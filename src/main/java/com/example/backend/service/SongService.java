package com.example.backend.service;

import com.example.backend.dto.SongDto;
import com.example.backend.model.Song;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SongService {
    Song getSongById(String id);
    List<Song> findAll();

    Song create(SongDto dto, String songUrl, String imageSongUrl) throws IOException;
    Song update(String id, SongDto dto);
    Song delete(String id);
}
