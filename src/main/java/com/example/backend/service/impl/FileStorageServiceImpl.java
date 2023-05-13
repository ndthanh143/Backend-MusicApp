package com.example.backend.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.backend.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        Map params = ObjectUtils.asMap(
                "resource_type", "auto",
                "folder", "audio"
        );
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(), params)
                .get("url")
                .toString();
    }

    @Override
    public String deleteFile(String id) throws IOException {
        cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return "File has been deleted";
    }
}
