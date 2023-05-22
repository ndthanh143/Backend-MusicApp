package com.example.backend.service;

import com.example.backend.dto.CommentDto;
import com.example.backend.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAll();
    Comment getById(String id);
    List<Comment> getListCommentOfSong(String songId);
    Comment create(CommentDto dto);
    Comment update(String id, String comment);
    Comment delete(String id);
}
