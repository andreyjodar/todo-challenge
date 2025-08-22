package com.github.andreyjodar.backend.features.labels.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LabelResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
