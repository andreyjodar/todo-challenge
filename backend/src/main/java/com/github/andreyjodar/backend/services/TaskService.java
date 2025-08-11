package com.github.andreyjodar.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.exceptions.NotFoundException;
import com.github.andreyjodar.backend.models.entities.Task;
import com.github.andreyjodar.backend.repositories.TaskRepository;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    MessageSource messageSource;

    public Task create(Task task) {
        Task taskInsert = taskRepository.save(task);
        return taskInsert;
    }

    public Task update(Task task) {
        Task taskDb = findById(task.getId());
        taskDb.setTitle(task.getTitle());
        taskDb.setDescription(task.getDescription());
        taskDb.setPriority(task.getPriority());
        taskDb.setStatus(task.getStatus());
        taskDb.setDeadline(task.getDeadline());
        return taskRepository.save(taskDb);
    }

    public void delete(Long id) {
        Task taskDb = findById(id);
        taskRepository.delete(taskDb);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("task.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
}
