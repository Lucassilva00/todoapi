package com.lucas.todoapi.repository;

import com.lucas.todoapi.model.Task;
import com.lucas.todoapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByTituloAndUser(String titulo, User user);

    List<Task> findByUserId(Long userId);
}
