package com.github.andreyjodar.backend.core.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.github.andreyjodar.backend.core.models.ErrorResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> naoEncontrado(NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Não Encontrado",
                ex.getMessage(),
                request.getDescription(false), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> negocio(BusinessException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de Negócio",
                ex.getMessage(),
                request.getDescription(false), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validacao(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> erros = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Erro de Validação",
                "Campos Inválidos",
                request.getDescription(false), erros);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // método global
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> global(Exception ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro Interno",
                ex.getMessage(),
                request.getDescription(false), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}