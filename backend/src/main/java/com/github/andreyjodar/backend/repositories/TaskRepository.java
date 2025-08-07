package com.github.andreyjodar.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
}
