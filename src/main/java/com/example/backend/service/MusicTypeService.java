package com.example.backend.service;

import com.example.backend.dto.MusicTypeDto;
import com.example.backend.model.MusicType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MusicTypeService {

    List<MusicType> findAll();
    MusicType getById(String id);

    MusicType create(MusicTypeDto dto, String thumbnailUrl) throws IOException;

    MusicType update(String id, MusicTypeDto dto, MultipartFile thumbnail);

    MusicType delete(String id) throws IOException;
}
