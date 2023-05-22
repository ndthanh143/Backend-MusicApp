package com.example.backend.controller;

import com.example.backend.dto.CommentDto;
import com.example.backend.model.Comment;
import com.example.backend.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAll() {
        return new ResponseEntity<>(commentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{songId}/all")
    public ResponseEntity<List<Comment>> getCommentOfSong(@PathVariable String songId) {
        return new ResponseEntity<>(commentService.getListCommentOfSong(songId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable String id) {
        return new ResponseEntity<>(commentService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody CommentDto dto) {
        return new ResponseEntity<>(commentService.create(dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable String id, @RequestParam("comment") String comment) {
        return new ResponseEntity<>(commentService.update(id, comment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> delete(@PathVariable String id) {
        return new ResponseEntity<>(commentService.delete(id), HttpStatus.OK);
    }
}
