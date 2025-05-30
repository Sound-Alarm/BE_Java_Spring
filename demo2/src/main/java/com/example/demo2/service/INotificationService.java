package com.example.demo2.service;

import com.example.demo2.model.ConveyorBelt;
import com.example.demo2.model.Custer;
import com.example.demo2.model.Notification;
import com.example.demo2.model.NotificationHistory;

import java.util.List;

public interface INotificationService {
    Notification createNotification(Notification notification);
    NotificationHistory createNotificationHistory(Notification notification);
    List<Notification> getAllThongBao();

    List<Notification> getThongBaoChuaDoc();

    void deleteThongBao(Notification notification);

    Notification getThongBaoByJobTypeId(Integer jobTypeId, Custer custer, ConveyorBelt conveyorBeltList);

    Notification updateThongBao(Notification existingNotification, Notification notification);

    void markAsRead(String id);
}