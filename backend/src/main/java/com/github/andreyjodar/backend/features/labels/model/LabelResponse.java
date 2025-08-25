package com.github.andreyjodar.backend.features.labels.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class LabelResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public static LabelResponse fromEntity(Label label) {
        LabelResponse dto = new LabelResponse();
        dto.setId(label.getId());
        dto.setName(label.getName());
        dto.setDescription(label.getDescription());
        dto.setCreatedAt(label.getCreatedAt());
        return dto;
    }
}
