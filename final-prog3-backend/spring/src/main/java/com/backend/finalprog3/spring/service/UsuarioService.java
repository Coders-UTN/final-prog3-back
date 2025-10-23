package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.RegistroRequestDTO;
import com.backend.finalprog3.spring.dto.UsuarioDTO;

public interface UsuarioService {
    UsuarioDTO crearUsuario(RegistroRequestDTO  registroRequestDTO);
}
