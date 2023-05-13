package com.example.backend.service.impl;

import com.example.backend.dto.PlaylistDto;
import com.example.backend.exception.InvalidException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Playlist;
import com.example.backend.model.Song;
import com.example.backend.repository.PlaylistRepository;
import com.example.backend.service.PlaylistService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private PlaylistRepository repo;

    public PlaylistServiceImpl(PlaylistRepository repo) {
        this.repo = repo;
    }


    @Override
    public Playlist getPlaylistById(String id) {
        Optional<Playlist> playlist = repo.findById(id);
        return playlist.get();
    }

    @Override
    public List<Song> getListSongFromPlaylist(String id) {
        Playlist playlist = getPlaylistById(id);
        List<Song> listSong = playlist.getListSongs();
        return listSong;
    }

    @Override
    public Playlist create(PlaylistDto dto) {
        Playlist playlist;
        if(ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Vui lòng nhập tên playlist");
        }
        playlist = new Playlist();
        playlist.setName(dto.getName());
        playlist.setThumbnail(null);
        playlist.setListSongs(null);
        repo.save(playlist);
        return playlist;
    }

    @Override
    public Playlist addSongToPlaylist(String id, Song song) {
        Playlist playlist = getPlaylistById(id);
        if(playlist == null) {
            throw new NotFoundException(String.format("Không tìm thấy playlist với id %s", id));
        }
        List<Song> newListSong = playlist.getListSongs();
        if (newListSong == null) {
//            playlist.setThumbnail(song.getImageSongUrl());
        }
        newListSong.add(song);
        playlist.setListSongs(newListSong);
        repo.save(playlist);
        return playlist;
    }

    @Override
    public Playlist removeSongFromPlaylist(String id, Song song) {
        Playlist playlist = getPlaylistById(id);
        if(playlist == null) {
            throw new NotFoundException(String.format("Không tìm thấy playlist với id %s", id));
        }
        List<Song> newListSong = playlist.getListSongs();
        if(newListSong == null) {
            throw new InvalidException("Không có bài hát nào trong playlist");
        }
        newListSong.remove(song);
        playlist.setListSongs(newListSong);
        repo.save(playlist);
        return playlist;
    }

    @Override
    public Playlist delete(String id) {
        Playlist playlist = getPlaylistById(id);
        if(playlist == null) {
            throw new NotFoundException(String.format("Không tìm thấy playlist với id %s", id));
        }
        repo.delete(playlist);
        return playlist;
    }
}
