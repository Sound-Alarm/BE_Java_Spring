package com.example.demo2.service.impl;

import com.example.demo2.model.ConveyorBelt;
import com.example.demo2.model.Notification;
import com.example.demo2.model.Role;
import com.example.demo2.model.User;
import com.example.demo2.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("unchecked")
    public User register(User user) {
        // Kiểm tra username đã tồn tại chưa
        Query query = new Query(Criteria.where("username").is(user.getUsername()));
        if (mongoTemplate.exists(query, User.class)) {
            throw new RuntimeException("Tên tài khoản đã đăng ký");
        }
        // Mã hóa password trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mongoTemplate.save(user);
    }

    @SuppressWarnings("unchecked")
    public LoginResponse login(String username, String password) {
        Query query = new Query(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query, User.class);
        System.out.println(user);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Cập nhật trạng thái online
            Update update = new Update()
                    .set("isOnline", true)
                    .set("lastLogin", LocalDateTime.now());
            mongoTemplate.updateFirst(query, update, User.class);

            // Generate tokens
            String accessToken = jwtService.generateAccessToken(user.getUsername(), user.getRole().getCode(),
                    user.getRole().getName());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername(), user.getRole().getCode(),
                    user.getRole().getName());

            // Gửi thông báo realtime
            messagingTemplate.convertAndSend("/topic/users", getAllOnlineUsers());
            // Gửi thông báo user mới đăng nhập
            messagingTemplate.convertAndSend("/topic/notifications",
                    Map.of("type", "LOGIN", "username", user.getUsername()));

            return new LoginResponse(
                    user.getUsername(),
                    user.getEmail(),
                    accessToken,
                    refreshToken,
                    user.getRole().getName(), user.getRole().getCode());

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public LoginResponse refreshToken(String refreshToken) {
        if (jwtService.validateToken(refreshToken)) {
            String username = jwtService.extractUsername(refreshToken);
            Query query = new Query(Criteria.where("username").is(username));
            User user = mongoTemplate.findOne(query, User.class);

            if (user != null) {
                String newAccessToken = jwtService.generateAccessToken(user.getUsername(), user.getRole().getCode(),
                        user.getRole().getName());
                return new LoginResponse(
                        user.getUsername(),
                        user.getEmail(),
                        newAccessToken,
                        user.getRole().getName());
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void logout(String userName) {
        Query query = new Query(Criteria.where("userName").is(userName));
        LocalDateTime now = LocalDateTime.now();
        Update update = new Update()
                .set("isOnline", false)
                .set("lastLogout", now);
        mongoTemplate.updateFirst(query, update, User.class);

        // Gửi thông báo realtime
        messagingTemplate.convertAndSend("/topic/users", getAllOnlineUsers());
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllOnlineUsers() {
        Query query = new Query(Criteria.where("isOnline").is(true));
        List<User> onlineUsers = mongoTemplate.find(query, User.class);

        // Thêm cả users đã đăng xuất
        Query offlineQuery = new Query(Criteria.where("isOnline").is(false));
        List<User> offlineUsers = mongoTemplate.find(offlineQuery, User.class);

        // Cập nhật thời gian đăng xuất cho mỗi user
        offlineUsers.forEach(user -> {
            if (user.getLastLogout() != null) {
                user.setTimeSinceLogout(user.calculateTimeSinceLogout(user.getLastLogout()));
            }
        });

        onlineUsers.addAll(offlineUsers);
        return onlineUsers;
    }

    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    public Role getRoleByCode(String code) {
        Query query = new Query(Criteria.where("code").is(code));
        Role role = mongoTemplate.findOne(query, Role.class);
        return role;
    }

    public User findByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class);
    }
    public List<Role> getAllRoleService() {
        Query query = new Query(Criteria.where("name").ne("ADMIN"));
        return mongoTemplate.find(query, Role.class);
    }
    public User findById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query, User.class);
    }
    public ConveyorBelt findConveyorBeltOfUser(String username){
        Query query = new Query(Criteria.where("username").is(username));
        User user =  mongoTemplate.findOne(query, User.class);
        System.out.println("user");
        System.out.println(user);
        return user.getConveyorBelt();
    }
}