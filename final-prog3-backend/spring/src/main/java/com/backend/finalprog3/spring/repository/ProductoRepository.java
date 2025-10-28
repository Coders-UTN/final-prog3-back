package com.backend.finalprog3.spring.repository;

import com.backend.finalprog3.spring.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Integer> {
}
