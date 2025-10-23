package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.AuthResponse;
import com.backend.finalprog3.spring.dto.LoginRequestDTO;
import com.backend.finalprog3.spring.dto.RegistroRequestDTO;

public interface AuthService {
    public AuthResponse register(RegistroRequestDTO usuario);
    public AuthResponse login(LoginRequestDTO usuarioLogin);


}
