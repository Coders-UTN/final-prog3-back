package com.backend.finalprog3.spring.controller;


import com.backend.finalprog3.spring.dto.usuario.LoginRequestDTO;
import com.backend.finalprog3.spring.dto.usuario.RegistroRequestDTO;
import com.backend.finalprog3.spring.dto.usuario.UsuarioDTO;
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
    public ResponseEntity<UsuarioDTO> register(@RequestBody RegistroRequestDTO usuario) {

        UsuarioDTO usuarioRegistrado = authService.register(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginRequestDTO loginRequest) {

           UsuarioDTO loginEnUsuario = authService.login(loginRequest);
           return ResponseEntity.ok(loginEnUsuario);
    }
}
