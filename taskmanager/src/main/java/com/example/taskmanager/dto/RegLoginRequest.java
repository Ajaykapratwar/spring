package com.example.taskmanager.dto;

import com.example.taskmanager.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data-transfer object containing registration or login credentials.
 */
@Data
public class RegLoginRequest {

    /** Email used for registration or login. */
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email;

    /** Plain password supplied for registration or login. */
    @NotBlank(message = "Password is required")
    private String password;

    /** Requested role during registration, when provided. */
    private Role role;
}
