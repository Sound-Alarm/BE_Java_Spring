package com.example.demo2.service.impl;

import com.example.demo2.model.Custer;
import com.example.demo2.repository.TeamRepository;
import com.example.demo2.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements ITeamService {

    @Autowired
    private TeamRepository teamRepository;

    public Custer createTeam(Custer cluster) {
        return teamRepository.save(cluster);
    }
}