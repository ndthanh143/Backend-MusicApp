package com.example.backend.service;

import com.example.backend.dto.UserDto;
import com.example.backend.dto.UserLoginDto;
import com.example.backend.model.User;

import javax.naming.AuthenticationException;

public interface AuthService {
    User signUp(UserDto dto);

    User login(String email, String password) throws AuthenticationException;

    boolean verifyOTP(String email, String otp);
}
