package com.example.demo2.service.impl;

import com.example.demo2.model.Workshop;
import com.example.demo2.repository.WorkshopRepository;
import com.example.demo2.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkshopServiceImpl implements WorkshopService {
    @Autowired
    private WorkshopRepository workshopRepository;


    @Override
    public Workshop createWorkshop(Workshop workshop) {
        return workshopRepository.save(workshop);
    }

    @Override
    public Workshop getWorkshopByCode(String code) {
        return workshopRepository.findByCode(code)
                .orElse(null);
    }
}