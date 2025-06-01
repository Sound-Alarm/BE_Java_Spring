package com.example.demo2.controller;

import com.example.demo2.model.ConveyorBelt;
import com.example.demo2.service.ConveyorBeltService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conveyor-belts")
@CrossOrigin(origins = "*")
public class ConveyorBeltController {
    @Autowired
    private  ConveyorBeltService conveyorBeltService;


    @PostMapping
    public ResponseEntity<ConveyorBelt> createConveyorBelt(@RequestBody ConveyorBelt conveyorBelt) {
        ConveyorBelt savedConveyorBelt = conveyorBeltService.createConveyorBelt(conveyorBelt);
        return ResponseEntity.ok(savedConveyorBelt);
    }
    @GetMapping
    public ResponseEntity<List<ConveyorBelt>> getAllConveyorBelt() {
        List<ConveyorBelt> savedConveyorBelt = conveyorBeltService.getAllConveyorBelt();
        return ResponseEntity.ok(savedConveyorBelt);
    }
}