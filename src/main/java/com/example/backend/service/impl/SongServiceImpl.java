package com.example.backend.service.impl;

import com.cloudinary.Cloudinary;
import com.example.backend.dto.SongDto;
import com.example.backend.exception.InvalidException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Song;
import com.example.backend.repository.SongRepository;
import com.example.backend.service.FileStorageService;
import com.example.backend.service.SongService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {
    private SongRepository repo;
    private final FileStorageService fileUpload;


    public SongServiceImpl(SongRepository repo, FileStorageService fileUpload) {
        this.repo = repo;
        this.fileUpload = fileUpload;
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
    public List<Song> findByMusicType(String id) {
        List<Song> findResult= repo.findSongByMusicType(id);
        return findResult;
    }

    @Override
    public List<Song> findByName(String name) {
        List<Song> findResult = repo.findSongByName(name);
        return findResult;
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
        Song song = getSongById(id);
        if(song == null) {
            throw new NotFoundException(String.format("Không tìm thấy bài hát có id: %s", id));
        }
        if(!ObjectUtils.isEmpty(dto.getName())) {
            song.setName(dto.getName());
        }
        if(!ObjectUtils.isEmpty(dto.getType())) {
            song.setType(dto.getType());
        }
        if(!ObjectUtils.isEmpty(dto.getLyrics())) {
            song.setLyrics(dto.getLyrics());
        }
        if(!ObjectUtils.isEmpty(dto.getArtist())) {
            song.setArtist(dto.getArtist());
        }
        repo.save(song);
        return song;
    }

    @Override
    public Song delete(String id) throws IOException {
        Song song = getSongById(id);
        if(song == null) {
            throw new NotFoundException(String.format("Không tìm thấy bài hát có id: %s", id));
        }
        String songUrl = song.getSongUrl();
        String imageSongUrl = song.getImageSongUrl();
        String songId = songUrl.substring(songUrl.lastIndexOf("/") + 1, songUrl.lastIndexOf("."));
        String imageSongId = imageSongUrl.substring(imageSongUrl.lastIndexOf("/") + 1, imageSongUrl.lastIndexOf("."));
        fileUpload.deleteFile(songId);
        fileUpload.deleteFile(imageSongId);
        repo.deleteById(id);
        return song;
    }
}
