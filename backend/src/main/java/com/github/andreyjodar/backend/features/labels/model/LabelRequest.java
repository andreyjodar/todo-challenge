package com.github.andreyjodar.backend.features.labels.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LabelRequest {
    @NotBlank(message = "{validation.labelname.notblank}") 
    @Size(max = 30, message = "{validation.labelname.maxsize}")
    private String name;
    @NotBlank(message = "{validation.labeldesc.notblank}")
    @Size(max = 150, message = "{validation.labeldesc.maxsize}")
    private String description;
}