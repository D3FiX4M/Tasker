package com.practice.Tasker.domain.repository;

import com.practice.Tasker.domain.entity.tasker.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByName(String name);
    Optional<Task> findByName(String name);
}
