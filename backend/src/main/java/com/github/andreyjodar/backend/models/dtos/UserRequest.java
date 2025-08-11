package com.github.andreyjodar.backend.models.dtos;

import lombok.Data;

@Data
public class UserRequest {
    private String email;
    private String senha;
}
