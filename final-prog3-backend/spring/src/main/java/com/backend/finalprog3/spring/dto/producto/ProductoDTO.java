package com.backend.finalprog3.spring.dto.producto;

public record ProductoDTO(
        Long id,
        String nombre,
        double precio,
        Long categoriaid,
        int stock,
        String descripcion,
        String imagen
) {
}