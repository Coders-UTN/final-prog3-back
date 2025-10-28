package com.backend.finalprog3.spring.dto.usuario;

public record CreateUsuarioDTO(
        String nombre,
        String apelido,
        String email,
        String contrasena
) {
}
