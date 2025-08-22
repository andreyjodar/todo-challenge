package com.github.andreyjodar.backend.features.tasks.model;

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
    private LocalDateTime createdAt;
}
