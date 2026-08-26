package com.example.taskmanager.model;

import com.example.taskmanager.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Persistence entity representing a registered application user.
 */
@Entity
@Table(name = "users")
@Data
public class User {

    /** Database-generated user identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Unique email used as the user's login name. */
    @Column(unique = true, nullable = false)
    private String email;

    /** BCrypt-hashed password excluded from JSON responses. */
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    /** Authorization role assigned to the user. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /** Tasks owned by this user. */
    @JsonManagedReference(value = "users-tasks")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();
}
