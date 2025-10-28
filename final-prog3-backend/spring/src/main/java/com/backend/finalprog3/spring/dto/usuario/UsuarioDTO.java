package com.backend.finalprog3.spring.dto.usuario;

import com.backend.finalprog3.spring.entity.Rol;

public record UsuarioDTO(
        Long id,
        String nombre,
        String apellido,
        String email,
        String celular,
        Rol rol,
        boolean activo) {
}
