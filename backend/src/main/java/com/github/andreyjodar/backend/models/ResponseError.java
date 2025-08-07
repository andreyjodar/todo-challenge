package com.github.andreyjodar.backend.models;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ResponseError {
    private LocalDateTime datetime;
    private int code;
    private String error;
    private String message;
    private String path;
    private List<String> details;

    public ResponseError(int code, String error, String message, String path, List<String> details) {
        this.code = code;
        this.error = error;
        this.path = path;
        this.message = message;
        this.details = details;
        datetime = LocalDateTime.now();
    }
}
