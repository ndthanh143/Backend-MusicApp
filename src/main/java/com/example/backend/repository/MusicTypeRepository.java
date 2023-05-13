package com.example.backend.repository;

import com.example.backend.model.MusicType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicTypeRepository extends MongoRepository<MusicType, String> {
}
