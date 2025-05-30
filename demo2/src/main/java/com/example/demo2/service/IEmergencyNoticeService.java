package com.example.demo2.service;

import com.example.demo2.model.EmergencyNotice;
import com.example.demo2.model.Notification;
import com.example.demo2.model.TypeNotification;

import java.util.List;

public interface IEmergencyNoticeService {
    EmergencyNotice createEmergencyNotice(EmergencyNotice emergencyNotice);
    List<TypeNotification> getAllTypeNotification();
    List<EmergencyNotice> getAllEmergencyNotice();

}
