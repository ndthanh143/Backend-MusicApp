package com.example.backend.repository;

import com.example.backend.model.Song;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SongRepository extends MongoRepository<Song, String> {
    @Query(value = "{name: {$regex:  ?0, $options: 'i'}}")
    List<Song> findSongByName(String keyword);

    @Query("{'Type.id': {$eq: ?0}}")
    List<Song> findSongByMusicType(String id);
}
