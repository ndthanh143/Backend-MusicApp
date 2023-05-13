package com.example.backend.service.impl;

import com.example.backend.dto.SongDto;
import com.example.backend.exception.InvalidException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Song;
import com.example.backend.repository.SongRepository;
import com.example.backend.service.SongService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {
    private SongRepository repo;

    public SongServiceImpl(SongRepository repo) {
        this.repo = repo;
    }

    @Override
    public Song getSongById(String id) {
        Optional<Song> song = repo.findById(id);
        return song.get();
    }

    @Override
    public List<Song> findAll() {
        return repo.findAll();
    }


    @Override
    public Song create(SongDto dto, String songUrl, String imageSongUrl) throws IOException {
        Song song;
        if(ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Vui lòng nhập tên bài hát");
        }
        if(ObjectUtils.isEmpty(dto.getType())) {
            throw new InvalidException("Vui lòng nhập thể loại nhạc");
        }
        if(ObjectUtils.isEmpty(dto.getLyrics())) {
            throw new InvalidException("Vui lòng nhập lyrics");
        }
        if(ObjectUtils.isEmpty(dto.getArtist())) {
            throw new InvalidException("Vui lòng nhập tên nghệ sĩ");
        }
        if(songUrl == null) {
            throw new InvalidException("Vui lòng cung cấp source bài hát");
        }
        if(imageSongUrl == null) {
            throw new InvalidException("Vui lòng cung cấp hình bài hát");
        }
        song = new Song();
        song.setName(dto.getName());
        song.setType(dto.getType());
        song.setArtist(dto.getArtist());
        song.setSongUrl(songUrl);
        song.setImageSongUrl(imageSongUrl);
        song.setLyrics(dto.getLyrics());
        repo.save(song);
        return song;
    }

    @Override
    public Song update(String id, SongDto dto) {
        return null;
    }

    @Override
    public Song delete(String id) {
        Song song = getSongById(id);
        if(song == null) {
            throw new NotFoundException(String.format("Không tìm thấy bài hát có id: %s", id));
        }
        repo.deleteById(id);
        return song;
    }
}
