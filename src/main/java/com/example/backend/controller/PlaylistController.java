package com.example.backend.controller;

import com.example.backend.dto.PlaylistDto;
import com.example.backend.model.Playlist;
import com.example.backend.model.Song;
import com.example.backend.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Playlist>> getAll() {
        return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getById(@PathVariable String id) {
        return new ResponseEntity<>(playlistService.getPlaylistById(id), HttpStatus.OK);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<List<Song>> getSongsFromPlaylist(@PathVariable String id) {
        return new ResponseEntity<>(playlistService.getListSongFromPlaylist(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Playlist> create(@RequestBody PlaylistDto dto) {
        Playlist playlist = playlistService.create(dto);
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-song")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable String id, @RequestBody Song song) {
        return new ResponseEntity<>(playlistService.addSongToPlaylist(id, song), HttpStatus.OK);
    }

    @PutMapping("/{id}/remove-song")
    public ResponseEntity<Playlist> removeSongFromPlaylist(@PathVariable String id, @RequestBody Song song) {
        return new ResponseEntity<>(playlistService.removeSongFromPlaylist(id, song), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> update(@PathVariable String id, @RequestBody PlaylistDto dto) {
        return new ResponseEntity<>(playlistService.update(id,dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Playlist> delete(@PathVariable String id) {
        return new ResponseEntity<>(playlistService.delete(id),HttpStatus.OK);
    }
}
