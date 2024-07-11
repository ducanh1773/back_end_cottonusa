package com.cottonusa.backend.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String email;
    private String passWord;

    // Getters và Setters
}