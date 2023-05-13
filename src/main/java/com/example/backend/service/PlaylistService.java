package com.example.backend.service;

import com.example.backend.dto.PlaylistDto;
import com.example.backend.model.Playlist;
import com.example.backend.model.Song;

import java.util.List;

public interface PlaylistService {

    List<Playlist> findAll();

    Playlist getPlaylistById(String id);

    List<Song> getListSongFromPlaylist(String id);

    Playlist create(PlaylistDto dto);

    Playlist addSongToPlaylist(String id, Song song);

    Playlist removeSongFromPlaylist(String id, Song song);

    Playlist update(String id, PlaylistDto dto);

    Playlist delete(String id);
}
