package com.backend.finalprog3.spring.dto;
import lombok.Data;

@Data
public class RegistroRequestDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String celular;
    private String password;
}
