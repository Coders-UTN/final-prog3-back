package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.usuario.RegistroRequestDTO;
import com.backend.finalprog3.spring.dto.usuario.UsuarioDTO;
import com.backend.finalprog3.spring.entity.Rol;
import com.backend.finalprog3.spring.entity.Usuario;
import com.backend.finalprog3.spring.mapper.UsuarioMapper;
import com.backend.finalprog3.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public UsuarioDTO crearUsuario(RegistroRequestDTO registroRequestDTO) {

        if (usuarioRepository.existsByEmail(registroRequestDTO.email())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El mail ingresado ya existe. Intente con otro");
        }

        Usuario usuarioNuevo = usuarioMapper.toEntity(registroRequestDTO);
        String hashPass = Sha256Util.hash(registroRequestDTO.password());

        usuarioNuevo.setContrasena(hashPass);
        usuarioNuevo.setRol(Rol.USUARIO);
        usuarioNuevo.setActivo(true);

        usuarioRepository.save(usuarioNuevo);

        return usuarioMapper.toDto(usuarioNuevo);
    }

    @Override
    public UsuarioDTO buscarUsuarioPorId(Long id) {
        Usuario usuarioEncontrado = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return usuarioMapper.toDto(usuarioEncontrado);
    }


}
