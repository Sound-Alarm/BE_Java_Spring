package com.example.demo2.service;

import com.example.demo2.model.ConveyorBelt;

import java.util.List;

public interface ConveyorBeltService {
    ConveyorBelt createConveyorBelt(ConveyorBelt conveyorBelt);
    List<ConveyorBelt> getAllConveyorBelt();

}