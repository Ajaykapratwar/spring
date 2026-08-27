package com.example.taskmanager.controller;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.RegLoginRequest;
import com.example.taskmanager.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This controller handles all authentication-related API requests.
// It exposes endpoints under /api/auth for creating a new user account and logging in an existing user.
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    // AuthService contains the real business logic for registration and login.
    private final AuthService authService;

    // POST /api/auth/register
    // This endpoint receives a registration request body, validates it,
    // and delegates the actual user creation to the service layer.
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(
            @Valid @RequestBody RegLoginRequest regRequest
    ) {
        return ResponseEntity.ok(authService.register(regRequest));
    }

    // POST /api/auth/login
    // This endpoint validates the login request, checks the user's credentials,
    // and uses the HTTP request object to maintain session-based authentication when needed.
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @Valid @RequestBody RegLoginRequest regRequest,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(authService.login(regRequest, request));
    }

}
