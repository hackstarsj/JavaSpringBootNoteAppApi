package com.furthergrow.noteappapi.services;

import com.furthergrow.noteappapi.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);
    String extractUserEmail(String token);
    String extractUserNameExact(String token);

    String generateToken(User user, UserDetails userDetails);
    String generateRefreshToken(User user);

    boolean isTokenValid(String token, UserDetails userDetails);
}