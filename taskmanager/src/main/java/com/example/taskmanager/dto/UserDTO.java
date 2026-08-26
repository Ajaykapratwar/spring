package com.example.taskmanager.dto;

import com.example.taskmanager.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Data-transfer representation of a user returned by the API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDTO {

    /** User identifier when available. */
    private Long id;

    /** User's email address. */
    private String email;

    /** Password field retained for DTO compatibility; normally omitted from responses. */
    private String password;

    /** User's authorization role. */
    private Role role;

    /** Tasks associated with the user when requested. */
    private List<TaskDTO> tasks;
}
