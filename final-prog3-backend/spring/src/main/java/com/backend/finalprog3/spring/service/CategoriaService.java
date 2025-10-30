package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.categoria.CategoriaDTO;
import com.backend.finalprog3.spring.dto.categoria.CreateCategoriaDTO;
import java.util.List;

public interface CategoriaService {

    CategoriaDTO crearCategoria(CreateCategoriaDTO createDTO);
    List<CategoriaDTO> findAllCategorias();
    CategoriaDTO findCategoriaById(Long id);
    CategoriaDTO modificarCategoria(Long id, CreateCategoriaDTO updateDTO);
    void eliminarCategoria(Long id);
    CategoriaDTO findByNombreIgnoreCase(String nombre);
}