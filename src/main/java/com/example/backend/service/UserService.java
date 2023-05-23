package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User getUser(String id);
    User update(String id, UserDto DTO);

    User updateAvatar(String id, String avatar);

    User changePassword(String id, String oldPassword, String newPassword);
    User delete(String id);
}
