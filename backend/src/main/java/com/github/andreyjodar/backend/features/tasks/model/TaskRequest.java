package com.github.andreyjodar.backend.features.tasks.model;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequest {
    
    @NotBlank(message = "{validation.tasktitle.notblank}")
    @Size(max = 100, message = "{validation.tasktitle.maxsize}")
    private String title;

    @NotBlank(message = "{validation.taskdesc.notblank}")
    private String description;

    private String status;
    private Set<Long> labelsId;

    @NotNull(message = "{validation.taskdeadline.notnull}") 
    @Future(message = "{validation.taskdeadline.future}")
    private LocalDate deadline; 
}
