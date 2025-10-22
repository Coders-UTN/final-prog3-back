package com.backend.finalprog3.spring.dto;


import lombok.Data;


//Este DTO permite recibir las credenciales


@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
