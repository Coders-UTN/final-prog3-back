package com.backend.finalprog3.spring.mapper;

import com.backend.finalprog3.spring.dto.categoria.CategoriaDTO;
import com.backend.finalprog3.spring.dto.categoria.CreateCategoriaDTO;
import com.backend.finalprog3.spring.entity.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaDTO toDto(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getImagen(),
                categoria.getActivo()
        );
    }

    public Categoria toEntity(CreateCategoriaDTO categoriaDTO) {
        return Categoria.builder()
                .nombre(categoriaDTO.nombre())
                .descripcion(categoriaDTO.descripcion())
                .imagen(categoriaDTO.imagen())
                .build();
    }
}
