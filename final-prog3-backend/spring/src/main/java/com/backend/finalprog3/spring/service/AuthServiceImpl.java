package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.usuario.LoginRequestDTO;
import com.backend.finalprog3.spring.dto.usuario.RegistroRequestDTO;
import com.backend.finalprog3.spring.dto.usuario.UsuarioDTO;
import com.backend.finalprog3.spring.entity.Usuario;
import com.backend.finalprog3.spring.exceptions.usuario.EmailAlreadyExistsException;
import com.backend.finalprog3.spring.exceptions.usuario.IncorrectPasswordException;
import com.backend.finalprog3.spring.mapper.UsuarioMapper;
import com.backend.finalprog3.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UsuarioDTO register(RegistroRequestDTO usuarioRegistro) {

        return usuarioService.crearUsuario(usuarioRegistro);
    }

    @Override
    public UsuarioDTO login(LoginRequestDTO usuarioLogin) {

        Usuario usuario = usuarioRepository.findByEmail(usuarioLogin.email()).orElseThrow(() ->
                new EmailAlreadyExistsException("El email " + usuarioLogin.email() + " no se encuentra registrado."));

        //Se hashea la clave del login para poder compararlas:
        String contrasenaHasheada = Sha256Util.hash(usuarioLogin.password());

        if(!usuario.getContrasena().equals(contrasenaHasheada)){
            throw new IncorrectPasswordException("Contraseña incorrecta");
        }

        return usuarioMapper.toDto(usuario);
    }
}
