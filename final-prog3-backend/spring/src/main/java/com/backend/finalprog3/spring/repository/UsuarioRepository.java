package com.backend.finalprog3.spring.repository;

import com.backend.finalprog3.spring.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    //Busqueda por mail:

    Optional<Usuario> findByEmail(String email);


    //Existe?

    boolean existsByEmail(String email);




}
