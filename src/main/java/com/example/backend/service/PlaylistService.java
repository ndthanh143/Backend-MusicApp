package com.example.backend.service;

import com.example.backend.dto.PlaylistDto;
import com.example.backend.model.Playlist;
import com.example.backend.model.Song;

import java.util.List;

public interface PlaylistService {
    Playlist getPlaylistById(String id);

    List<Song> getListSongFromPlaylist(String id);

    Playlist create(PlaylistDto dto);

    Playlist addSongToPlaylist(String id, Song song);

    Playlist removeSongFromPlaylist(String id, Song song);

    Playlist delete(String id);
}
