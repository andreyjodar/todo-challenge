package com.github.andreyjodar.backend.features.tasks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.github.andreyjodar.backend.features.tasks.model.Task;
import com.github.andreyjodar.backend.features.users.model.User;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
    public Page<Task> findByAuthor(User author, Pageable pageable);
}
