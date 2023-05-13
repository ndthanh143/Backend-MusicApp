package com.example.backend.service;

import com.example.backend.dto.UserDTO;
import com.example.backend.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User getTaiKhoan(String id);
    User create(UserDTO DTO);
    User update(String id,UserDTO DTO);
    User delete(String id);
}
