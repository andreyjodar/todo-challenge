package com.github.andreyjodar.backend.features.tasks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.andreyjodar.backend.core.exceptions.ForbiddenException;
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

    @Autowired
    private MessageSource messageSource;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TaskResponse> create(
            @Valid @RequestBody TaskRequest taskRequest, 
            @AuthenticationPrincipal User principal) {

        Task task = taskService.fromDto(taskRequest);
        task.setAuthor(principal);
        Task taskDb = taskService.create(task);
        return ResponseEntity.ok(taskService.fromEntity(taskDb));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Page<TaskResponse>> findAll(
            Pageable pageable, 
            @AuthenticationPrincipal User principal) {

        Page<Task> tasks;
        if(isAdmin(principal)) {
            tasks = taskService.findAll(pageable);
        } else {
            tasks = taskService.findByAuthor(principal, pageable);
        }
        Page<TaskResponse> tasksResponse = tasks.map(taskService::fromEntity);
        return ResponseEntity.ok(tasksResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TaskResponse> update(
            @PathVariable("id") Long id, 
            @Valid @RequestBody TaskRequest taskRequest,
            @AuthenticationPrincipal User principal) {

        Task task = taskService.fromDto(taskRequest);
        Task taskDb = taskService.findById(id);
        if(!isAdmin(principal) && !taskDb.getAuthor().getId().equals(principal.getId())) {
            throw new ForbiddenException(messageSource.getMessage("exception.userrole.unauthorized",
                new Object[] { principal.getAuthorities() }, LocaleContextHolder.getLocale()));
        }
        task.setId(id);
        return ResponseEntity.ok(taskService.fromEntity(task));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id, 
            @AuthenticationPrincipal User principal) {
        
        Task task = taskService.findById(id);
        if(!isAdmin(principal) && !task.getAuthor().getId().equals(principal.getId())) {
            throw new ForbiddenException(messageSource.getMessage("exception.userrole.unauthorized",
                new Object[] { principal.getAuthorities() }, LocaleContextHolder.getLocale()));
        }
        taskService.delete(id);
        return ResponseEntity.ok("Task with id:" + id + " deleted successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<TaskResponse> findById(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User principal) {
        
        Task task = taskService.findById(id);
        if(!isAdmin(principal) && !task.getAuthor().getId().equals(principal.getId())) {
            throw new ForbiddenException(messageSource.getMessage("exception.userrole.unauthorized",
                new Object[] { principal.getAuthorities() }, LocaleContextHolder.getLocale()));
        }
        return ResponseEntity.ok(taskService.fromEntity(task));
    }

    private boolean isAdmin(User principal) {
        return principal.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }
}
