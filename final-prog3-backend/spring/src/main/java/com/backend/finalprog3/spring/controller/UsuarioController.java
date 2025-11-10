package com.backend.finalprog3.spring.controller;

import com.backend.finalprog3.spring.dto.usuario.UsuarioDTO;
import com.backend.finalprog3.spring.entity.Usuario;
import com.backend.finalprog3.spring.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorId(@PathVariable Long id){
        UsuarioDTO usuarioEncontrado = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuarioEncontrado);
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> getUsuarioLogueado(HttpSession session){
        //guardado de id de usuario en session para seguridad al hacer la peticion posterior y no llamar al idusuario desde el frontend
        Long usuarioId = (Long) session.getAttribute("usuario_id");

        if (usuarioId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UsuarioDTO usuarioEncontrado = usuarioService.buscarUsuarioPorId(usuarioId);
        return ResponseEntity.ok(usuarioEncontrado);
    }
}
