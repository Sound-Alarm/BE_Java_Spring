package com.example.demo2.controller;

import com.example.demo2.model.Custer;
import com.example.demo2.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "*")
public class TeamController {

    @Autowired
    private ITeamService teamService;

    @PostMapping
    public ResponseEntity<Custer> createTeam(@RequestBody Custer cluster) {
        Custer savedCluster = teamService.createTeam(cluster);
        return ResponseEntity.ok(savedCluster);
    }
}