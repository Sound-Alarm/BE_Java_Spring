package com.example.demo2.service.impl;

import com.example.demo2.model.*;
import com.example.demo2.service.IEmergencyNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;

import java.util.Date;
import java.util.List;

@Service
public class EmergencyNoticeServiceSevice implements IEmergencyNoticeService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public static String getTodayWeekdayFormatted() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        String dayName = dayOfWeek.toString().substring(0, 1).toUpperCase()
                + dayOfWeek.toString().substring(1).toLowerCase();
        return dayName; // Ví dụ: Monday, Tuesday, ...
    }

    @Override
    public EmergencyNotice createEmergencyNotice(EmergencyNotice emergencyNotice) {
        Query query = new Query(Criteria.where("content").is(emergencyNotice.getContent()));
        EmergencyNotice emergencyNoticeCheck = mongoTemplate.findOne(query, EmergencyNotice.class);
        if (emergencyNoticeCheck != null) {
            return null;
        }
        emergencyNotice.setCreateAt(new Date());
        return mongoTemplate.save(emergencyNotice);
    }

    @Override
    public List<TypeNotification> getAllTypeNotification() {
        return mongoTemplate.findAll(TypeNotification.class);
    }

    @Override
    public List<EmergencyNotice> getAllEmergencyNotice() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        String currentDay = dayOfWeek.toString().substring(0, 1).toUpperCase()
                + dayOfWeek.toString().substring(1).toLowerCase();
        System.out.println("Thứ ngày hôm nay: " + currentDay);

        Query query = new Query(Criteria.where("listOfReleaseDates").in(currentDay));
        return mongoTemplate.find(query, EmergencyNotice.class);
    }

}
