package com.example.backend.repository;

import com.example.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    @Query(value = "{'email':  ?0}")
    Optional<User> getUser(String email);

    @Query(value = "{'email': ?0}", exists = true)
    boolean kiemtraEmail(String email);

    @Query(value = "{'phone': ?0}", exists = true)
    boolean existsByUsername(String phone);
}
