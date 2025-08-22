package com.github.andreyjodar.backend.features.users.model;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "{validation.username.notblank}")
    @Size(max = 100, message = "{validation.username.maxsize}")
    private String name;

    @NotBlank(message = "{validation.useremail.notblank}")
    @Email(message = "{validation.useremail.notvalid}")
    private String email;

    @NotBlank(message = "{validation.userpassw.notblank}")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
    message = "{validation.userpassw.notvalid}")
    private String password;
    
    @NotEmpty(message = "{validation.userroles.notempty}")
    private Set<String> roles;
}
