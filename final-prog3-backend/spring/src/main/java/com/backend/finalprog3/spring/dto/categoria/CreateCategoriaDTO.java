package com.backend.finalprog3.spring.dto.categoria;

public record CreateCategoriaDTO(
        String nombre,
        String descripcion,
        String imagen
) {
}
