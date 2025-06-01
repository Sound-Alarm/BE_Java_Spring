package com.example.demo2.controller;

import com.example.demo2.model.EmergencyNotice;
import com.example.demo2.model.Notification;
import com.example.demo2.model.TypeNotification;
import com.example.demo2.service.IEmergencyNoticeService;
import com.example.demo2.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/thongbao")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IEmergencyNoticeService emergencyNoticeService;

    @PostMapping
    public Notification createThongBao(@RequestBody Notification notification) {
        System.out.println(notification);
        System.out.println("ConveyorBelt");
        System.out.println(notification.getConveyorBelt());

        Notification checkJobTypeId = notificationService.getThongBaoByJobTypeId(notification.getJobTypeId(),
                notification.getCuster(), notification.getConveyorBelt());
//        notificationService.createNotificationHistory(notification);
        if (checkJobTypeId == null) {
            return notificationService.createNotification(notification);
        }
        return notificationService.updateNotification(checkJobTypeId, notification);
    }

    @GetMapping
    public List<Notification> getAllNotificationController() {
        return notificationService.getAllNotification();
    }

    @GetMapping("/chuadoc")
    public List<Notification> getAllNotificationNotReadController() {
        return notificationService.getAllNotificationNotRead();
    }

    @PostMapping("/doc")
    public void markAsRead(@RequestBody Notification notification) {
        System.out.println(notification);
        Notification deleteNotification =notificationService.deleteNotification(notification);
        notificationService.createNotificationHistory(deleteNotification);

    }

    @GetMapping("/emergency_notice")
    public List<EmergencyNotice> getAllEmergencyNoticeController() {
        return emergencyNoticeService.getAllEmergencyNotice();
    }

    @GetMapping("/type_notification")
    public List<TypeNotification> getAllTypeNotificationController() {
        return emergencyNoticeService.getAllTypeNotification();
    }

    @PostMapping("/create_emergency_notice")
    public EmergencyNotice createEmergencyNoticeController(@RequestBody EmergencyNotice emergencyNotice) {
        System.out.println(emergencyNotice);
        return emergencyNoticeService.createEmergencyNotice(emergencyNotice);
    }
}