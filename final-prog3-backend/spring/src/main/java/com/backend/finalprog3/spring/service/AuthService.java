package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.usuario.LoginRequestDTO;
import com.backend.finalprog3.spring.dto.usuario.RegistroRequestDTO;
import com.backend.finalprog3.spring.dto.usuario.UsuarioDTO;

public interface AuthService {
    UsuarioDTO register(RegistroRequestDTO usuario);
    UsuarioDTO login(LoginRequestDTO usuarioLogin);
}
