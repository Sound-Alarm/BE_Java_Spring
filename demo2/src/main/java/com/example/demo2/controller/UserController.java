package com.example.demo2.controller;

import com.example.demo2.model.Role;
import com.example.demo2.model.User;
import com.example.demo2.service.impl.UserService;
import com.example.demo2.dto.LoginResponse;
import com.example.demo2.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import com.example.demo2.service.impl.JwtService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, HttpServletRequest httpRequest) {
        try {
            // Lấy access token từ header
            String authHeader = httpRequest.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
            }

            // Lấy mã code role từ token


            // Đăng ký user như cũ
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(request.getPassword());
            user.setEmail(request.getEmail());
            Role newUserRole = userService.getRoleByCode(request.getCode());
            user.setRole(newUserRole);
            user.setConveyorBelt(request.getConveyorBelt());
            User registeredUser = userService.register(user);
            return ResponseEntity.ok(registeredUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        LoginResponse loginResponse = userService.login(credentials.get("username"), credentials.get("password"));
        System.out.println(loginResponse);
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        LoginResponse loginResponse = userService.refreshToken(refreshToken);
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        }
        return ResponseEntity.badRequest().body("Invalid refresh token");
    }

    @PostMapping("/logout/{userName}")
    public ResponseEntity<?> logout(@PathVariable String userName) {
        userService.logout(userName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/online")
    public ResponseEntity<List<User>> getOnlineUsers() {
        return ResponseEntity.ok(userService.getAllOnlineUsers());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/role")
    public ResponseEntity<List<Role>> getAllRoleController() {
        return  ResponseEntity.ok(userService.getAllRoleService());
    }
    @GetMapping("/notifications")
    public ResponseEntity<?> getNotifications() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/conveyorBelt")
    public ResponseEntity<?> getConveyorBelt(@RequestParam(value = "userName") String userName) {
        System.out.println(userName);
        return ResponseEntity.ok(userService.findConveyorBeltOfUser(userName));
    }
}