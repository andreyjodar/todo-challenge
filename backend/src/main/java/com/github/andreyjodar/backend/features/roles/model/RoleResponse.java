package com.github.andreyjodar.backend.features.roles.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoleResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;

    public static RoleResponse fromEntity(Role role) {
        RoleResponse dto = new RoleResponse();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setCreatedAt(role.getCreatedAt());
        return dto;
    }
}
