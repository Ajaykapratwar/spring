package com.example.taskmanager.service;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.RegLoginRequest;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Defines registration and login operations for application users.
 */
public interface AuthService {

    /**
     * Registers a new user account.
     *
     * @param regRequest registration details supplied by the client
     * @return a response containing the registration result
     */
    ApiResponse<?> register(RegLoginRequest regRequest);

    /**
     * Authenticates a user and establishes a session for the request.
     *
     * @param loginRequest login credentials supplied by the client
     * @param request current HTTP request used to create the session
     * @return a response containing the login result
     */
    ApiResponse<?> login(RegLoginRequest loginRequest, HttpServletRequest request);
}
