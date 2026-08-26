package com.example.taskmanager.model;

import com.example.taskmanager.enums.Category;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Persistence entity representing a task owned by a user.
 */
@Entity
@Table(name = "tasks")
@Data
public class Task {

    /** Database-generated task identifier. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Short title describing the task. */
    private String title;

    /** Optional detailed task description. */
    private String description;

    /** Category used to classify the task. */
    @Enumerated(EnumType.STRING)
    private Category category;

    /** Whether the task has been completed. */
    private boolean isCompleted = false;

    /** User who owns this task. */
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "users-tasks")
    private User user;
}
