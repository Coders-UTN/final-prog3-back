package com.backend.finalprog3.spring.repository;

import com.backend.finalprog3.spring.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    List<Categoria> findByActivoTrue();
    Optional<Categoria> findByIdAndActivoTrue(Long id);
    boolean existsByNombreIgnoreCase(String nombre);
    Optional<Categoria> findByNombreIgnoreCaseAndActivoTrue(String nombre);
}