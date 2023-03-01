package com.practice.Tasker.domain.repository;

import com.practice.Tasker.domain.entity.tasker.GlobalTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GlobalTaskRepository extends JpaRepository<GlobalTask, Long> {
    boolean existsByName(String name);
    Optional<GlobalTask> findByName(String name);
}
