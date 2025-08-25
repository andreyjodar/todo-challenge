package com.github.andreyjodar.backend.features.users.model;

import java.time.LocalDateTime;
import java.util.Set;

import com.github.andreyjodar.backend.features.roles.model.RoleResponse;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Set<RoleResponse> roles;
    private LocalDateTime createdAt;

    public static UserResponse fromEntity(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setRoles(user.getRoles().stream()
            .map(RoleResponse::fromEntity).collect(java.util.stream.Collectors.toSet()));
        return dto;
    }
}
