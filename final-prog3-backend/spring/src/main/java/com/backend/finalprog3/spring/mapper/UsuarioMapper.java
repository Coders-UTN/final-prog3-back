package com.backend.finalprog3.spring.mapper;

import com.backend.finalprog3.spring.dto.RegistroRequestDTO;
import com.backend.finalprog3.spring.dto.UsuarioDTO;
import com.backend.finalprog3.spring.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {
    public UsuarioDTO toDto(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getCelular(),
                usuario.getRol(),
                usuario.getActivo());
    }
    public Usuario toEntity(RegistroRequestDTO usuarioDTO) {
        return Usuario.builder()
                .nombre(usuarioDTO.nombre())
                .apellido(usuarioDTO.apellido())
                .email(usuarioDTO.email())
                .celular(usuarioDTO.celular())
                .contrasena(usuarioDTO.password())
                .build();
    }

}
