package com.example.taskmanager.service;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.enums.Category;

import java.util.List;

// This interface defines the contract for task-related business operations.
// Any implementation of this service is expected to handle task creation,
// retrieval, deletion, and completion toggling for a logged-in user.
public interface TaskService {

    // Creates a new task for the user identified by userEmail.
    // The request data is passed in as TaskDTO and the result is wrapped in ApiResponse.
    ApiResponse<TaskDTO> createTask(TaskDTO taskDTO, String userEmail);

    // Retrieves all tasks for a specific user and category.
    // Example: all completed tasks, active tasks, or a specific category filter.
    ApiResponse<List<TaskDTO>> getTaskByUserAndCategory(String userEmail, Category category);

    // Deletes a task by its unique id for the given user's email.
    // Returns a success/failure message inside ApiResponse.
    ApiResponse<String> deleteTask(Long id, String email);

    // Marks a task as completed or not completed based on its current state.
    // The task is identified by id and must belong to the authenticated user.
    ApiResponse<TaskDTO> toggleTaskCompletion(Long id, String email);
}
