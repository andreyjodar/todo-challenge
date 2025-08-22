package com.github.andreyjodar.backend.features.tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.features.tasks.model.Task;
import com.github.andreyjodar.backend.features.tasks.model.TaskRequest;
import com.github.andreyjodar.backend.features.tasks.model.TaskResponse;
import com.github.andreyjodar.backend.features.tasks.service.TaskService;
import com.github.andreyjodar.backend.features.users.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest taskRequest, @AuthenticationPrincipal User authUser) {
        Task task = taskService.fromDto(taskRequest);
        task.setAuthor(authUser);
        Task taskDb = taskService.create(task);
        return ResponseEntity.ok(taskService.fromEntity(taskDb));
    }
}
