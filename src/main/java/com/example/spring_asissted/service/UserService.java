package com.example.spring_asissted.service;

import com.example.spring_asissted.dto.UserRequestDTO;
import com.example.spring_asissted.entity.User;
import com.example.spring_asissted.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserRequestDTO request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new RuntimeException("Email already exists");
        });

        com.example.spring_asissted.entity.UserRole role = com.example.spring_asissted.entity.UserRole.USER;
        if (request.getRole() != null) {
            try {
                role = com.example.spring_asissted.entity.UserRole.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Keep default USER if invalid role provided
            }
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        return userRepository.save(user);
    }
}
