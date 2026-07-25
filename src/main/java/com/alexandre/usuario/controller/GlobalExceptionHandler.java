package com.alexandre.usuario.controller;


import com.alexandre.usuario.infrastructure.exception.ConflictException;
import com.alexandre.usuario.infrastructure.exception.ResourceNotFoundException;
import com.alexandre.usuario.infrastructure.exception.UnauthorizedException;
import com.alexandre.usuario.infrastructure.exception.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                            HttpServletRequest request){
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(UnauthorizedException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(com.alexandre.usuario.infrastructure.exception.IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(com.alexandre.usuario.infrastructure.exception.IllegalArgumentException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(java.lang.IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(java.lang.IllegalArgumentException ex, HttpServletRequest request){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(HttpStatus status, String mensagem, HttpServletRequest request) {
        return ResponseEntity.status(status).body(buildError(status.value(),
                mensagem,
                request.getRequestURI(),
                status.getReasonPhrase()
        ));
    }


    private ErrorResponseDTO buildError(int status, String mensagem, String path, String error) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(mensagem)
                .error(error)
                .status(status)
                .path(path)
                .build();
    }
}
