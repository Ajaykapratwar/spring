package com.example.taskmanager.dto;

import com.example.taskmanager.enums.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Data-transfer representation of a task exchanged through the API.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDTO {

    /** Task identifier when available. */
    private Long id;

    /** Short task title. */
    private String title;

    /** Optional task details. */
    private String description;

    /** Task classification. */
    private Category category;

    /** Completion state of the task. */
    private boolean isCompleted = false;

//    private UserDTO user;
}
