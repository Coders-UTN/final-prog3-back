package com.backend.finalprog3.spring.dto.usuario;

public record RegistroRequestDTO(
    String nombre,
    String apellido,
    String email,
    String celular,
    String password
) {}
