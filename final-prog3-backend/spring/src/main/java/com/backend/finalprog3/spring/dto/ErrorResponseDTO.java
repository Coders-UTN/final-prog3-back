package com.backend.finalprog3.spring.dto;

public record ErrorResponseDTO(
        String error,
        String mensaje,
        int status
) {
}
