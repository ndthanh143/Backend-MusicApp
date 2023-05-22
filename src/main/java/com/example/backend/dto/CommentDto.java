package com.example.backend.dto;

import com.example.backend.model.Song;
import com.example.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private String comment;
    private Song song;
    private User user;
}
