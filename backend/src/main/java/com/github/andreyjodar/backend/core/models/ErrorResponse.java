package com.github.andreyjodar.backend.core.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    private LocalDateTime datetime;
    private int code;
    private String error;
    private String message;
    private String path;
    private List<String> details;

    public ErrorResponse(int code, String error, String message, String path, List<String> details) {
        this.code = code;
        this.error = error;
        this.path = path;
        this.message = message;
        this.details = details;
        datetime = LocalDateTime.now();
    }
}
