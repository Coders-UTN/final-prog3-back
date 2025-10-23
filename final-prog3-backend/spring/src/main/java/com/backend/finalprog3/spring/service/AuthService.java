package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.AuthResponse;
import com.backend.finalprog3.spring.dto.LoginRequestDTO;
import com.backend.finalprog3.spring.dto.RegistroRequestDTO;

public interface AuthService {
    AuthResponse register(RegistroRequestDTO usuario);
    AuthResponse login(LoginRequestDTO usuarioLogin);


}
