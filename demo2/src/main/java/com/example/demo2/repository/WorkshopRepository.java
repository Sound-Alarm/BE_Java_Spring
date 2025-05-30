package com.example.demo2.repository;

import com.example.demo2.model.Workshop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WorkshopRepository extends MongoRepository<Workshop, String> {
    Optional<Workshop> findByCode(String code);
}