package com.example.backend.dto;

import com.example.backend.utils.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminUpdateDto {
    private String name;
    private String phone;
    private String email;
    private String password;
    private ERole role;
}
