package com.example.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String uploadFile(MultipartFile multipartFile) throws IOException;

    String deleteFile(String id) throws IOException;
}
