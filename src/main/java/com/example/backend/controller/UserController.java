package com.example.backend.controller;

import com.example.backend.dto.UserDto;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users= userService.findAll();
        if (users.size() >0) {
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No users available", HttpStatus.valueOf(404));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @RequestBody UserDto dto) {
        return new ResponseEntity<>(userService.update(id, dto), HttpStatus.OK);
    }
    @PutMapping("/update-password/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable String id, @RequestPart("oldPassword") String oldPassword, @RequestPart("newPassword") String newPassword) {
        return new ResponseEntity<>(userService.changePassword(id, oldPassword, newPassword), HttpStatus.OK);
    }

}
