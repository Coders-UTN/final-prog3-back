package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.categoria.CategoriaDTO;
import com.backend.finalprog3.spring.dto.categoria.CreateCategoriaDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaDTO crearCategoria(CreateCategoriaDTO categoria);
    CategoriaDTO findById(Long id);
    List<CategoriaDTO> listarCategorias();
    CategoriaDTO modificarCategoria(Long id, CreateCategoriaDTO categoria);
    void eliminarCategoria(Long id);
    CategoriaDTO findByNombreIgnoreCase(String nombre);
}
