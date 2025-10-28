package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.entity.Usuario;

import java.util.Optional;

public interface AuthService {
    Usuario register(Usuario usuario);
    Usuario login(String email, String password);


}
