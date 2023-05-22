package com.example.backend.repository;

import com.example.backend.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    @Query(value = "{'Song.id': ?0}")
    List<Comment> getListCommentOfSong(String id);
}
