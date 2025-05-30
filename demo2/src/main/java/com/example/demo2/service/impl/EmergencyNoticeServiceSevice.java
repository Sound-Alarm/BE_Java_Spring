package com.example.demo2.service.impl;

import com.example.demo2.model.*;
import com.example.demo2.service.IEmergencyNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmergencyNoticeServiceSevice implements IEmergencyNoticeService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public EmergencyNotice createEmergencyNotice(EmergencyNotice emergencyNotice) {
        Query query = new Query(Criteria.where("content").is(emergencyNotice.getContent()));
        EmergencyNotice emergencyNoticeCheck = mongoTemplate.findOne(query,EmergencyNotice.class);
        if (emergencyNoticeCheck != null){
            return null;
        }
        return mongoTemplate.save(emergencyNotice);
    }

    @Override
    public List<TypeNotification> getAllTypeNotification() {
        return mongoTemplate.findAll(TypeNotification.class);
    }

    @Override
    public List<EmergencyNotice> getAllEmergencyNotice() {
        return mongoTemplate.findAll(EmergencyNotice.class);
    }
}
