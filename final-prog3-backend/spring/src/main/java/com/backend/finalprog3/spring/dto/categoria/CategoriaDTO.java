package com.backend.finalprog3.spring.dto.categoria;

public record CategoriaDTO(
        Long id,
        String nombre,
        String descripcion,
        String imagen,
        boolean activo
) {
}
