package com.example.demo2.repository;

import com.example.demo2.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ThongBaoRepository extends MongoRepository<Notification, String> {
    Optional<Notification> findByJobTypeId(Integer jobTypeId);

}