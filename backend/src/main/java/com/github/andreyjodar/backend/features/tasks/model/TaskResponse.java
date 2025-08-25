package com.github.andreyjodar.backend.features.tasks.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.github.andreyjodar.backend.features.labels.model.LabelResponse;
import com.github.andreyjodar.backend.features.users.model.UserResponse;

import lombok.Data;

@Data
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private String status;
    private UserResponse author;
    private Set<LabelResponse> labels;
    private LocalDate deadline;
    private LocalDateTime createdAt;

    public static TaskResponse fromEntity(Task task) {
        TaskResponse dto = new TaskResponse();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus().toString());
        dto.setAuthor(UserResponse.fromEntity(task.getAuthor()));
        dto.setLabels(task.getLabels().stream()
            .map(LabelResponse::fromEntity).collect(java.util.stream.Collectors.toSet()));
        dto.setDeadline(task.getDeadline());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }
}
