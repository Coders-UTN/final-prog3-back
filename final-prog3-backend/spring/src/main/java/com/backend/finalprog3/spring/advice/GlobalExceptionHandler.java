package com.backend.finalprog3.spring.advice;

import com.backend.finalprog3.spring.dto.ErrorResponseDTO;
import com.backend.finalprog3.spring.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponseDTO> handleApiExceptions(ApiException ex) {

        // 1. Crea el DTO de error
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getClass().getSimpleName(), // ej: "CategoriaNotFoundException"
                ex.getMessage(),
                ex.getStatus().value() // Obtiene el HttpStatus (404, 400, etc.)
        );

        // 2. Devuelve el ResponseEntity con el DTO y el HttpStatus correcto
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    // --- MANEJADOR 2: PARA ERRORES INESPERADOS (500) ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericExceptions(Exception ex) {


        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "ErrorInternoDelServidor",
                "Ha ocurrido un error inesperado.",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}