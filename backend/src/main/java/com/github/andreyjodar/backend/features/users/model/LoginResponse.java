package com.github.andreyjodar.backend.features.users.model;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private Long expiresIn;
    private UserResponse userResponse;

    public LoginResponse(String token, UserResponse userResponse) {
        this.token = token;
        this.userResponse = userResponse;
    }
}
