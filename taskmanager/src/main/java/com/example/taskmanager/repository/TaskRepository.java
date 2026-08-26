package com.example.taskmanager.repository;

import com.example.taskmanager.enums.Category;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Provides database operations and user-based queries for tasks.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Retrieves all tasks owned by a user.
     *
     * @param user task owner
     * @return tasks belonging to the user
     */
    List<Task> findByUser(User user);

    /**
     * Retrieves a user's tasks in a specific category.
     *
     * @param user task owner
     * @param category category to filter by
     * @return matching tasks
     */
    List<Task> findByUserAndCategory(User user, Category category);
}
