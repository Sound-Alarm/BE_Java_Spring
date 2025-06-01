package com.example.demo2.service.impl;

import com.example.demo2.model.ConveyorBelt;
import com.example.demo2.repository.ConveyorBeltRepository;
import com.example.demo2.service.ConveyorBeltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConveyorBeltServiceImpl implements ConveyorBeltService {
    @Autowired
    private  ConveyorBeltRepository conveyorBeltRepository;


    @Override
    public ConveyorBelt createConveyorBelt(ConveyorBelt conveyorBelt) {
        return conveyorBeltRepository.save(conveyorBelt);
    }

    @Override
    public List<ConveyorBelt> getAllConveyorBelt() {
        return conveyorBeltRepository.findAll();
    }
}