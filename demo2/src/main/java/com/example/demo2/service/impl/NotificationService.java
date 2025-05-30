package com.example.demo2.service.impl;

import com.example.demo2.model.ConveyorBelt;
import com.example.demo2.model.Custer;
import com.example.demo2.model.Notification;
import com.example.demo2.model.NotificationHistory;
import com.example.demo2.repository.ThongBaoRepository;
import com.example.demo2.service.INotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.mongodb.client.model.changestream.ChangeStreamDocument;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.bson.Document;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private ExecutorService executorService;

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();

        // Tạo Change Stream để theo dõi collection thong_bao
        executorService.submit(() -> {
            try {
                mongoTemplate.getCollection("notification")
                        .watch()
                        .forEach((ChangeStreamDocument<Document> changeStreamDocument) -> {
                            String operationType = changeStreamDocument.getOperationType().getValue();

                            // Bỏ qua nếu document bị xoá hoặc không có dữ liệu đầy đủ
                            if (changeStreamDocument.getFullDocument() == null) {
                                System.out.println("Document bị xoá hoặc không có dữ liệu đầy đủ, bỏ qua.");
                                return;
                            }

                            Notification notification = mongoTemplate.getConverter()
                                    .read(Notification.class, changeStreamDocument.getFullDocument());

                            if (notification != null) {
                                messagingTemplate.convertAndSend("/topic/thongbao", notification);
                            }
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @PreDestroy
    public void cleanup() {
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public Notification createNotification(Notification notification) {
        return mongoTemplate.save(notification);
    }
    @Override
    public NotificationHistory createNotificationHistory(Notification notification) {
        Query query = new Query(Criteria.where("nameJobType").is(notification.getNameJobType()).and("jobTypeId").is(notification.getJobTypeId()).and("custer").is(notification.getCuster()).and("conveyorBelt").is(notification.getConveyorBelt()));
        NotificationHistory notificationHistory = mongoTemplate.findOne(query,NotificationHistory.class);
        NotificationHistory newNotificationHistory = new NotificationHistory();
        BeanUtils.copyProperties(notification,newNotificationHistory);
        if (notificationHistory != null){
            return mongoTemplate.save(newNotificationHistory);
        }
       return null;
    }
    public List<Notification> getAllThongBao() {
        return mongoTemplate.findAll(Notification.class);
    }

    public List<Notification> getThongBaoChuaDoc() {
        Query query = new Query(Criteria.where("daDoc").is(false));
        return mongoTemplate.find(query, Notification.class);
    }

    public void deleteThongBao(Notification notification) {
        Query query = new Query(Criteria.where("_id").is(notification.getId()));
        Notification notificationFindById = mongoTemplate.findOne(query, Notification.class);

        String newNoiDung = notificationFindById.getContent();
        int indexTo = newNoiDung.indexOf("Tổ");

        if (indexTo != -1) {
            newNoiDung = newNoiDung.substring(0, indexTo).trim(); // Cắt từ đầu đến trước "Tổ"
        }
        // Bước 2: Xử lý danh sách indexTeam
        List<Integer> currentTeams = new ArrayList<>(notificationFindById.getIndexTeam()); // tránh sửa trực tiếp
        List<Integer> inputTeams = notification.getIndexTeam();

        for (Integer team : currentTeams.stream().toList()) { // copy tạm để tránh lỗi xóa khi duyệt
            if (inputTeams.contains(team)) {
                currentTeams.remove(team); // loại bỏ team trùng
            }
        }

        // Thêm các team còn lại từ input nếu chưa có
        for (Integer team : currentTeams) {
            newNoiDung += " Tổ " + team;
        }
        newNoiDung = newNoiDung + " Yêu cầu " + notificationFindById.getNameJobType();
        notificationFindById.setContent(newNoiDung);
        notificationFindById.setIndexTeam(currentTeams);
        if (notificationFindById.getIndexTeam().size() < 1) {
            mongoTemplate.remove(query, Notification.class);
            // Gửi thông báo realtime về việc xóa thông báo
            messagingTemplate.convertAndSend("/topic/thongbao/delete", Map.of(
                    "id", notificationFindById.getId(),
                    "type", "DELETE",
                    "message", "Thông báo đã bị xóa"));
        } else {
            mongoTemplate.save(notificationFindById);
        }
    }

    public Notification getThongBaoByJobTypeId(Integer jobTypeId, Custer custer, ConveyorBelt conveyorBeltList) {
        Query query = new Query(Criteria.where("jobTypeId").is(jobTypeId)
                .and("custer").is(custer)
                .and("ConveyorBelt").is(conveyorBeltList));
        Notification existingNotification = mongoTemplate.findOne(query, Notification.class);

        return existingNotification;
    }

    public Notification updateThongBao(Notification existingNotification, Notification notification) {
        // Tìm thông báo hiện tại

        if (existingNotification == null) {
            throw new RuntimeException("Không tìm thấy thông báo với id: " + existingNotification.getId());
        }

        String noiDung = notification.getContent();
        int indexTo = noiDung.indexOf("Tổ");
        if (indexTo != -1) {
            noiDung = noiDung.substring(0, indexTo); // Cắt từ đầu đến trước "Tổ"
        }
        String noiDungUpdate = noiDung.trim();
        System.out.println(noiDung.trim()); // Gán lại, cắt khoảng trắng nếu có

        existingNotification.setType(notification.getType());

        List<Integer> indexTeamExistingThongBao = existingNotification.getIndexTeam();
        List<Integer> indexTeamThongBao = notification.getIndexTeam();
        Set<Integer> mergedSet = new HashSet<>();
        mergedSet.addAll(indexTeamExistingThongBao);
        mergedSet.addAll(indexTeamThongBao);

        List<Integer> mergedList = new ArrayList<>(mergedSet);
        for (Integer value : mergedList) {
            System.out.println(value);
            noiDungUpdate = noiDungUpdate + " Tổ " + value;
        }
        noiDungUpdate = noiDungUpdate + " yêu cầu " + notification.getNameJobType();
        System.out.println(noiDungUpdate);
        existingNotification.setContent(noiDungUpdate);
        existingNotification.setIndexTeam(mergedList);
        existingNotification.setTotalTeam(notification.getTotalTeam());
        existingNotification.setAudioBase64(notification.getAudioBase64());
        existingNotification.setJobTypeId(notification.getJobTypeId());
        // Không cập nhật thoiGianTao và daDoc

        // Lưu thông báo đã cập nhật
        return mongoTemplate.save(existingNotification);
    }

    public void markAsRead(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update().set("daDoc", true);
        mongoTemplate.updateFirst(query, update, Notification.class);
    }


}