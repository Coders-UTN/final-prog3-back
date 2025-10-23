package com.backend.finalprog3.spring.controller;


import com.backend.finalprog3.spring.dto.AuthResponse;
import com.backend.finalprog3.spring.dto.LoginRequestDTO;
import com.backend.finalprog3.spring.dto.RegistroRequestDTO;
import com.backend.finalprog3.spring.entity.Usuario;
import com.backend.finalprog3.spring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegistroRequestDTO usuario) {

            AuthResponse authResponse = authService.register(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequestDTO loginRequest) {
           AuthResponse loginEnUsuario = authService.login(loginRequest);
           return ResponseEntity.ok(loginEnUsuario);

    }
}
