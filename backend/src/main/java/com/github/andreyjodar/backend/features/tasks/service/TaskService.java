package com.github.andreyjodar.backend.features.tasks.service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.andreyjodar.backend.core.exceptions.NotFoundException;
import com.github.andreyjodar.backend.features.labels.model.Label;
import com.github.andreyjodar.backend.features.labels.service.LabelService;
import com.github.andreyjodar.backend.features.tasks.model.Task;
import com.github.andreyjodar.backend.features.tasks.model.TaskRequest;
import com.github.andreyjodar.backend.features.tasks.model.TaskStatus;
import com.github.andreyjodar.backend.features.tasks.repository.TaskRepository;
import com.github.andreyjodar.backend.features.users.model.User;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelService labelService;

    @Autowired
    private MessageSource messageSource;

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Task update(Task task) {
        Task taskDb = findById(task.getId());
        taskDb.setStatus(task.getStatus());
        if(task.getStatus() == TaskStatus.DONE) {
            taskDb.setEndDate(LocalDateTime.now());
        }
        taskDb.setTitle(task.getTitle());
        taskDb.setDescription(task.getDescription());
        taskDb.setDeadline(task.getDeadline());
        return taskRepository.save(taskDb);
    }

    public void delete(Long id) {
        Task taskDb = findById(id);
        taskRepository.delete(taskDb);
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.task.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    public Page<Task> findByAuthor(User user, Pageable pageable) {
        return taskRepository.findByAuthor(user, pageable);
    }

    public Page<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    public Task fromDto(TaskRequest taskRequest) {
        Task task = new Task();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setDeadline(taskRequest.getDeadline());
        if(taskRequest.getStatus() != null) {
            task.setStatus(TaskStatus.valueOf(taskRequest.getStatus().toUpperCase()));
        }

        Set<Label> labels = taskRequest.getLabelsId().stream()
            .map(labelId -> labelService.findById(labelId)).collect(Collectors.toSet());
        
        task.setLabels(labels);
        return task;
    }
}
