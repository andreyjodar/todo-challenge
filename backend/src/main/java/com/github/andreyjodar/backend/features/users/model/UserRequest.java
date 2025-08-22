package com.github.andreyjodar.backend.features.users.model;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class UserRequest {
    @NotBlank
    private String name;
    @NotBlank @Email
    private String email;
    @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$")
    private String password;
    @NotEmpty
    private Set<String> roles;
}
