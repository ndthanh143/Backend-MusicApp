package com.example.backend.dto;

import com.example.backend.model.MusicType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SongDto {
    private String name;

    private String artist;

    private String lyrics;

    private MusicType type;

}
