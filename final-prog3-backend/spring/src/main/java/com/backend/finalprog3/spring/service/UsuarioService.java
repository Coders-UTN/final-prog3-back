package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.usuario.RegistroRequestDTO;
import com.backend.finalprog3.spring.dto.usuario.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO crearUsuario(RegistroRequestDTO  registroRequestDTO);
}
