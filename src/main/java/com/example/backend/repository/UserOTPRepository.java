package com.example.backend.repository;

import com.example.backend.model.User;
import com.example.backend.model.UserOTP;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserOTPRepository extends MongoRepository<UserOTP, String> {
    @Query(value = "{'email':  ?0}")
    Optional<UserOTP> findByEmail(String email);
}
