package com.github.andreyjodar.backend.features.users.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotBlank(message = "{validation.useremail.notblank}") 
    @Email(message = "{validation.useremail.notvalid}")
    private String email;

    @NotBlank(message = "{validation.userpassw.notblank}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
    message = "{validation.userpassw.notvalid}")
    private String password;
}
