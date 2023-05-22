package com.example.backend.repository;

import com.example.backend.model.Playlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PlaylistRepository extends MongoRepository<Playlist, String> {
    @Query(value = "{'User.id': ?0}")
    List<Playlist> getPlaylistOfUser(String id);
}
