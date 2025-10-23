package com.backend.finalprog3.spring.dto;

import com.backend.finalprog3.spring.entity.Usuario;

public record AuthResponse(String token, UsuarioDTO usuarioDTO ) {
}
