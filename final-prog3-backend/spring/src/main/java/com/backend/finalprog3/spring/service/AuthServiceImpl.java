package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.AuthResponse;
import com.backend.finalprog3.spring.dto.LoginRequestDTO;
import com.backend.finalprog3.spring.dto.RegistroRequestDTO;
import com.backend.finalprog3.spring.dto.UsuarioDTO;
import com.backend.finalprog3.spring.entity.Usuario;
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
    public AuthResponse register(RegistroRequestDTO usuarioRegistro) {

        UsuarioDTO usuarioCreado = usuarioService.crearUsuario(usuarioRegistro);

        //Creacion de token de autenticacion
        String token = "token_simulado_aqui";

        return new AuthResponse(token, usuarioCreado);
    }

    @Override
    public AuthResponse login(LoginRequestDTO usuarioLogin) {

        Usuario usuario = usuarioRepository.findByEmail(usuarioLogin.email()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email invalido"));

        //Se hashea la clave del login para poder compararlas:
        String contrasenaHasheada = Sha256Util.hash(usuarioLogin.password());

        if(!usuario.getContrasena().equals(contrasenaHasheada)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        }
        String token =  "token_simulado_aqui";

        return new AuthResponse(token, usuarioMapper.toDto(usuario));
    }
}
