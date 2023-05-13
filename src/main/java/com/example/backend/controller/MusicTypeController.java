package com.example.backend.controller;

import com.example.backend.dto.MusicTypeDto;
import com.example.backend.model.MusicType;
import com.example.backend.service.FileStorageService;
import com.example.backend.service.MusicTypeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/music-type")
public class MusicTypeController {
    private final MusicTypeService musicTypeService;
    private final FileStorageService fileStorageService;

    public MusicTypeController(MusicTypeService musicTypeService, FileStorageService fileStorageService) {
        this.musicTypeService = musicTypeService;
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicType> getMusicType(@PathVariable String id) {
        return new ResponseEntity<>(musicTypeService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MusicType>> getAllMusicType() {
        return new ResponseEntity<>(musicTypeService.findAll(), HttpStatus.OK);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<MusicType> create(@RequestPart("music-type") MusicTypeDto dto,
                                            @RequestPart("thumbnail") MultipartFile thumbnail) throws IOException {
        String thumbnailUrl = fileStorageService.uploadFile(thumbnail);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(musicTypeService.create(dto, thumbnailUrl), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicType> update(@PathVariable String id, @RequestBody MusicTypeDto dto, @RequestPart MultipartFile thumbnail) {
        return new ResponseEntity<>(musicTypeService.update(id, dto, thumbnail), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MusicType> delete(@PathVariable String id) throws IOException {
        return new ResponseEntity<>(musicTypeService.delete(id), HttpStatus.OK);
    }
}
