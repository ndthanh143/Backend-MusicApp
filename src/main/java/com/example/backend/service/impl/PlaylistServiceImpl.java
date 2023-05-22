package com.example.backend.service.impl;

import com.example.backend.dto.PlaylistDto;
import com.example.backend.exception.InvalidException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.Playlist;
import com.example.backend.model.Song;
import com.example.backend.model.User;
import com.example.backend.repository.PlaylistRepository;
import com.example.backend.service.PlaylistService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private PlaylistRepository repo;

    public PlaylistServiceImpl(PlaylistRepository repo) {
        this.repo = repo;
    }


    @Override
    public List<Playlist> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Playlist> getPlaylistOfUser(String id) {
        List<Playlist> listPlaylist = repo.getPlaylistOfUser(id);
        return listPlaylist;
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
    public Playlist create(PlaylistDto dto, User user) {
        Playlist playlist;
        if(ObjectUtils.isEmpty(dto.getName())) {
            throw new InvalidException("Vui lòng nhập tên playlist");
        }
        playlist = new Playlist();
        playlist.setName(dto.getName());
        playlist.setThumbnail(null);
        playlist.setListSongs(null);
        playlist.setUser(user);
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
            newListSong = new ArrayList<>();
            playlist.setThumbnail(song.getImageSongUrl());
        } else {
            List<Song> list = playlist.getListSongs();
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).getId().equals(song.getId())) {
                    return null;
                }
            }
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
        List<Song> listSong = playlist.getListSongs();
        if(listSong.isEmpty()) {
            throw new InvalidException("Không có bài hát nào trong playlist");
        }
        for(int i = 0; i < listSong.size(); i++) {
            if(listSong.get(i).getId().equals(song.getId())) {
                listSong.remove(i);
            }
        }
        if(listSong.isEmpty()) {
            playlist.setThumbnail(null);
        }
        playlist.setListSongs(listSong);
        repo.save(playlist);
        return playlist;
    }

    @Override
    public Playlist update(String id, PlaylistDto dto) {
        Playlist playlist = getPlaylistById(id);
        if(playlist == null) {
            throw new NotFoundException(String.format("Không tìm thấy playlist với id %s", id));
        }
        if(!ObjectUtils.isEmpty(dto.getName())) {
            playlist.setName(dto.getName());
        }
        if(!ObjectUtils.isEmpty(dto.getUser())) {
            playlist.setUser(dto.getUser());
        }
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
