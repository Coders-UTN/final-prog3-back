package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.entity.Rol;
import com.backend.finalprog3.spring.entity.Usuario;
import com.backend.finalprog3.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario register(Usuario usuario) {

        //Chequea si el mail existe:

        if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()){
            throw new RuntimeException("El mail ingresado ya existe. Intente con otro");
        }

        //Si no se asigna ningun rol que por defecto sea un rol = usuario;
        if(usuario.getRol() == null){
            usuario.setRol(Rol.USUARIO);
        }

        //Hasheo de clave:

        usuario.setPassword(Sha256Util.hash(usuario.getPassword()));


        //Guardamos y retornamos:

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario login(String email, String password) {

        //Busca usuarios por mail:

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Usuario o email invalido"));

        //Se hashea la clave del login para poder compararlas:

        String contrasenaHasheada = Sha256Util.hash(password);

        if(!usuario.getPassword().equals(contrasenaHasheada)){
            throw new RuntimeException("Contraseña o email incorrecto");
        }

        //Para no devolver la clave

        usuario.setPassword(null);

        return usuario;
    }
}
