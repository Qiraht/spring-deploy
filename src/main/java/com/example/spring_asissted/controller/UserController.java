package com.example.spring_asissted.controller;

import com.example.spring_asissted.dto.ApiResponse;
import com.example.spring_asissted.dto.AuthRequestDTO;
import com.example.spring_asissted.dto.AuthResponseDTO;
import com.example.spring_asissted.dto.UserRequestDTO;
import com.example.spring_asissted.service.AuthService;
import com.example.spring_asissted.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    /**
     * Register a new user. Returns empty success response.
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@Valid @RequestBody UserRequestDTO request) {
        userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.<Object>builder()
                .success(true)
                .message(null)
                .data(null)
                .build());
    }

    /**
     * Login user. Returns token in data field.
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody AuthRequestDTO request) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success(null, response));
    }

    /**
     * Logout placeholder. In JWT, clients should discard the token.
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        return ResponseEntity.ok(ApiResponse.success("Logout successful. Please discard your token.", null));
    }
}
