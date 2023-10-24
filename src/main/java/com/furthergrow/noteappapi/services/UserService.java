package com.furthergrow.noteappapi.services;

import com.furthergrow.noteappapi.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    User createUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(User note);

    void deleteUser(Long id);
}