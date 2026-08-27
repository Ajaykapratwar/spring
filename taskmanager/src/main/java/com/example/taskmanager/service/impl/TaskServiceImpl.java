package com.example.taskmanager.service.impl;

import com.example.taskmanager.dto.ApiResponse;
import com.example.taskmanager.dto.TaskDTO;
import com.example.taskmanager.enums.Category;
import com.example.taskmanager.exception.BadRequestException;
import com.example.taskmanager.exception.NotFoundException;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.repository.UserRepository;
import com.example.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;

    @Override
    public ApiResponse<TaskDTO> createTask(TaskDTO taskDTO, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not exist"));

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCategory(taskDTO.getCategory() != null ? taskDTO.getCategory() : Category.PERSONAL);

        task.setUser(user);

        Task savedTasks = taskRepository.save(task);
        TaskDTO savedTaskDTO = mapTaskToTaskDTO(savedTasks);

        return new ApiResponse<>(201, "Task created successfully", savedTaskDTO);
    }

    @Override
    public ApiResponse<List<TaskDTO>> getTaskByUserAndCategory(String userEmail, Category category) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not exist"));

        List<Task> tasks;

        if (category != null) {
            tasks = taskRepository.findByUserAndCategory(user, category);
        } else {
            tasks = taskRepository.findByUser(user);
        }

        List<TaskDTO> taskDTOList = tasks.stream()
                .map(this::mapTaskToTaskDTO)
                .toList();

        String message = (category != null) ? "User Tasks category: " + category : " All tasks retrieved for you.";
        return new ApiResponse<>(201, message, taskDTOList);
    }

    @Override
    public ApiResponse<String> deleteTask(Long id, String email) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Task Not Found exception"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new BadRequestException("You are not authorized to delete this task.");
        }

        taskRepository.delete(task);

        return new ApiResponse<>(204, "Task Deleted Successfully", null);
    }

    @Override
    public ApiResponse<TaskDTO> toggleTaskCompletion(Long id, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not exist"));

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Task Not Found exception"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new BadRequestException("You are not authorized to delete this task.");
        }

        task.setCompleted(!task.isCompleted());

        Task updatedTask = taskRepository.save(task);

        TaskDTO savedTaskDTO = mapTaskToTaskDTO(updatedTask);

        String status = updatedTask.isCompleted() ? "Completed" : "Pending";

        return new ApiResponse<>(200, "Task marked as " + status, savedTaskDTO);
    }

    private TaskDTO mapTaskToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCategory(task.getCategory());
        taskDTO.setCompleted(task.isCompleted());

        return taskDTO;
    }
}
