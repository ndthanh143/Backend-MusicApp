package com.example.backend.service;

import com.example.backend.dto.UserAdminUpdateDto;
import com.example.backend.dto.UserDto;
import com.example.backend.model.User;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User getUser(String id);
    User update(String id, UserDto DTO);

    User adminUpdate(String id, UserAdminUpdateDto DTO);

    User updateAvatar(String id, String avatar) throws IOException;

    User changePassword(String id, String oldPassword, String newPassword);
    User delete(String id);
}
