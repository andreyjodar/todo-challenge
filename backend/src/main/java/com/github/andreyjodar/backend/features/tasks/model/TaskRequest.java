package com.github.andreyjodar.backend.features.tasks.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {
    @NotBlank @Size(max = 100)
    private String title;
    @NotBlank
    private String description;
    private String status;
    private Set<Long> labelsId;
    @NotNull
    private LocalDate deadline; 
}
