package com.example.backend.service.impl;

import com.example.backend.dto.UserAdminUpdateDto;
import com.example.backend.dto.UserDto;
import com.example.backend.exception.InvalidException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.FileStorageService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private FileStorageService fileStorageService;

    public UserServiceImpl(UserRepository userRepository){this.userRepository=userRepository;}
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException(String.format("Không tìm thấy tài khoản có id: ",id)));
    }

    @Override
    public User update(String id, UserDto dto) {
        User existUser = getUser(id);
        if(existUser == null) {
            throw new NotFoundException("User not found");
        }
        if(!ObjectUtils.isEmpty(dto.getName())) {
            existUser.setName(dto.getName());
        }
        if(!ObjectUtils.isEmpty(dto.getPassword())) {
            existUser.setPassword(dto.getPassword());
        }
        userRepository.save(existUser);
        return existUser;
    }

    @Override
    public User adminUpdate(String id, UserAdminUpdateDto dto) {
        User existUser = getUser(id);
        if(existUser == null) {
            throw new NotFoundException("User not found");
        }
        if(!ObjectUtils.isEmpty(dto.getName())) {
            existUser.setName(dto.getName());
        }
        if(!ObjectUtils.isEmpty(dto.getEmail())) {
            existUser.setEmail(dto.getEmail());
        }
        if(!ObjectUtils.isEmpty(dto.getPhone())) {
            existUser.setPhone(dto.getPhone());
        }
        if(!ObjectUtils.isEmpty(dto.getRole())) {
            existUser.setRole(dto.getRole());
        }
        userRepository.save(existUser);
        return existUser;
    }

    @Override
    public User updateAvatar(String id, String avatar) throws IOException {
        User user = getUser(id);

        if(user == null) {
            throw new NotFoundException("User not found");
        }
        fileStorageService.deleteFile(user.getAvatar());
        user.setAvatar(avatar);
        userRepository.save(user);
        return user;
    }

    @Override
    public User changePassword(String id, String oldPassword, String newPassword) {
        User existUser = getUser(id);
        if(existUser == null) {
            throw new NotFoundException("User not found");
        }
        if(!existUser.getPassword().equals(oldPassword)) {
            throw new InvalidException("Password wrong!");
        }
        existUser.setPassword(newPassword);
        userRepository.save(existUser);
        return existUser;
    }

    @Override
    public User delete(String id) {
        User existUser = getUser(id);
        if(existUser == null) {
            throw new NotFoundException("User not found");
        }
        userRepository.delete(existUser);
        return existUser;
    }
}
