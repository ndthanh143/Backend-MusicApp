package com.example.backend.controller;

import com.example.backend.dto.UserDTO;
import com.example.backend.model.User;
import com.example.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:8080")
@RestController
@RequestMapping("/api/v1/tai-khoan")
public class TaiKhoanController {
    private final UserService taiKhoanService;

    public TaiKhoanController(UserService taiKhoanService) {
        this.taiKhoanService = taiKhoanService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getTaiKhoan(@PathVariable String id) {
        return new ResponseEntity<>(taiKhoanService.getTaiKhoan(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO DTO) {
        return new ResponseEntity<>(taiKhoanService.create(DTO), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable String id, @Validated @RequestBody UserDTO DTO) {
        return new ResponseEntity<>(taiKhoanService.update(id, DTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable String id) {
        return new ResponseEntity<>(taiKhoanService.delete(id), HttpStatus.OK);
    }
}


