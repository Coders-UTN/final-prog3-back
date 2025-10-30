package com.backend.finalprog3.spring.dto.producto;

public record ProductoDTO(
        Long id,
        String nombre,
        double precio,
        String categoria,
        int stock,
        String descripcion,
        String imagen
) {
}