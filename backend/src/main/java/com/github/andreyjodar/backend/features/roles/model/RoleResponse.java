package com.github.andreyjodar.backend.features.roles.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoleResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
