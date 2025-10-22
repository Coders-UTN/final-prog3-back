package com.backend.finalprog3.spring.controller;


import com.backend.finalprog3.spring.dto.LoginRequestDTO;
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


    //Post:

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {

        Usuario usuarioRegistrado = authService.register(usuario);

        //Se oculta contrasena respuesta (seguridad)

        usuario.setPassword(null);

        return ResponseEntity.status(201).body(usuarioRegistrado);
    }

    //Logeo como usuario:

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody LoginRequestDTO loginRequest) {
       try {
           Usuario loginEnUsuario = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
           return ResponseEntity.ok(loginEnUsuario);
       }catch (RuntimeException e){
           return ResponseEntity.status(401).build();
       }
    }










}
