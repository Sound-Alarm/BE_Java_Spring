package com.example.demo2.controller;

import com.example.demo2.model.Workshop;
import com.example.demo2.service.WorkshopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/workshops")
@CrossOrigin(origins = "*")
public class WorkshopController {
    @Autowired
    private WorkshopService workshopService;

    @PostMapping
    public ResponseEntity<Workshop> createWorkshop(@RequestBody Workshop workshop) {
        Workshop savedWorkshop = workshopService.createWorkshop(workshop);
        return ResponseEntity.ok(savedWorkshop);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<?> getWorkshopByCode(@PathVariable String code) {
        System.out.println(code);
        Workshop workshop = workshopService.getWorkshopByCode(code);
        if (workshop == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "status", 400,
                            "message", "Workshop with code '" + code + "' not found"
                    ));
        }
        return ResponseEntity.ok(workshop);
    }

}