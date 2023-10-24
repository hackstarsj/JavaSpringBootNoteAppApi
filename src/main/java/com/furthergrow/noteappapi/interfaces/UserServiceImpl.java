package com.furthergrow.noteappapi.interfaces;


import com.furthergrow.noteappapi.entity.User;
import com.furthergrow.noteappapi.repository.UserRepository;
import com.furthergrow.noteappapi.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
    @Override
    public User createUser(User note) {
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(note);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = Optional.of(userRepository.findById(id).get());
        return optionalUser.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        User note1 = userRepository.findById(user.getId()).get();
        note1.setUsername(user.getUsername());
        note1.setPassword(user.getPassword());
        note1.setEmail(user.getEmail());
        note1.setUpdatedAt(LocalDateTime.now());
        User noteUpdated = userRepository.save(note1);
        return noteUpdated;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}