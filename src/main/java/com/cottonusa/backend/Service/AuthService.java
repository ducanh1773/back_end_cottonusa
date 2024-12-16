package com.cottonusa.backend.Service;

import com.cottonusa.backend.PasswordHashingExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utility.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordHashingExample passwordHashingExample;

    public String generateToken(String email) {
        return jwtUtil.generateToken(email);
    }

    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordHashingExample.matches(rawPassword, encodedPassword);
    }

    public String getEmailFromToken(String token) {
        return JwtUtil.getEmailFromToken(token);
    }
}
