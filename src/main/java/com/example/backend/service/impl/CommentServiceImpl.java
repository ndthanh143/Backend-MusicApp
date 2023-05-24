package com.example.backend.service.impl;

import com.example.backend.dto.CommentDto;
import com.example.backend.exception.InvalidException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Comment;
import com.example.backend.repository.CommentRepository;
import com.example.backend.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repo;

    public CommentServiceImpl(CommentRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Comment> getAll() {
        return repo.findAll();
    }

    @Override
    public Comment getById(String id) {
        return repo.findById(id).get();
    }

    @Override
    public List<Comment> getListCommentOfSong(String songId) {
        return repo.getListCommentOfSong(songId);
    }

    @Override
    public Comment create(CommentDto dto) {
        if(ObjectUtils.isEmpty(dto.getComment().trim())) {
            throw new InvalidException("Vui lòng nhập comment");
        }
        if(ObjectUtils.isEmpty(dto.getSong())) {
            throw new InvalidException("Vui lòng cung cấp bài hát");
        }
        if(ObjectUtils.isEmpty(dto.getUser())) {
            throw new InvalidException("Vui lòng cung cấp user");
        }
        Comment comment = new Comment();
        comment.setComment(dto.getComment());
        comment.setSong(dto.getSong());
        comment.setUser(dto.getUser());
        comment.setCreatedAt(new Date());
        comment.setUpdatedAt(null);
        return repo.save(comment);
    }

    @Override
    public Comment update(String id, String comment) {
        Comment existComment = getById(id);
        if(existComment == null) {
            throw new NotFoundException("Không tìm thấy comment có id main: "+id);
        }
        existComment.setComment(comment);
        existComment.setUpdatedAt(new Date());
        return repo.save(existComment);
    }

    @Override
    public Comment delete(String id) {
        Comment existComment = getById(id);
        if(existComment == null) {
            throw new NotFoundException("Không tìm thấy comment có id âdasdad: "+id);
        }
        repo.delete(existComment);
        return existComment;
    }
}
