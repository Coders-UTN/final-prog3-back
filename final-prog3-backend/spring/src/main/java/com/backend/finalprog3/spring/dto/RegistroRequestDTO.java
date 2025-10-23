package com.backend.finalprog3.spring.dto;

public record RegistroRequestDTO(
    String nombre,
    String apellido,
    String email,
    String celular,
    String password
) {}
