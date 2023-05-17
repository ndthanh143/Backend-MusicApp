package com.example.backend.model;

import com.example.backend.utils.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String id;

    private String name;

    private String phone;

    private String email;

    private String password;

    private ERole role;

    private boolean trangThai = true;


}
