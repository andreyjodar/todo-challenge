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
}
