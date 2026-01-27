package com.example.spring_asissted.service;

import com.example.spring_asissted.config.JwtUtil;
import com.example.spring_asissted.dto.AuthRequestDTO;
import com.example.spring_asissted.dto.AuthResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthResponseDTO login(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Fetch user from repository to get name and role
        com.example.spring_asissted.entity.User user = ((com.example.spring_asissted.service.UserDetailsServiceImpl) userDetailsService)
                .getUserByEmail(request.getEmail());

        return AuthResponseDTO.builder()
                .token(jwt)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
