package com.backend.finalprog3.spring.repository;

import com.backend.finalprog3.spring.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByActivoTrue();
    Optional<Producto> findByIdAndActivoTrue(Long id);
    Optional<Producto> findByNombreAndActivoTrue(String nombre);
}
