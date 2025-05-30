package com.example.demo2.repository;

import com.example.demo2.model.Custer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends MongoRepository<Custer, String> {
}