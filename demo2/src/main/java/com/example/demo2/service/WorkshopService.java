package com.example.demo2.service;

import com.example.demo2.model.Workshop;

public interface WorkshopService {
    Workshop createWorkshop(Workshop workshop);
    Workshop getWorkshopByCode(String id);

}