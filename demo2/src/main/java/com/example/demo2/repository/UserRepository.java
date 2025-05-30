package com.example.demo2.repository;

import com.example.demo2.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);

}
