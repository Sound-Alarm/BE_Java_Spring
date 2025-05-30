package com.example.demo2.repository;

import com.example.demo2.model.ConveyorBelt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConveyorBeltRepository extends MongoRepository<ConveyorBelt, String> {
}