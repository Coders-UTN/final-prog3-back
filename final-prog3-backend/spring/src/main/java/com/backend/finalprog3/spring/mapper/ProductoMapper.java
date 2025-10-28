package com.backend.finalprog3.spring.mapper;

import com.backend.finalprog3.spring.dto.producto.CreateProductoDTO;
import com.backend.finalprog3.spring.dto.producto.ProductoDTO;
import com.backend.finalprog3.spring.entity.Categoria;
import com.backend.finalprog3.spring.entity.Producto;
import com.backend.finalprog3.spring.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        String nombreCategoria = (producto.getCategoria() != null)
                ? producto.getCategoria().getNombre()
                : null;

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                nombreCategoria
        );
    }

    public Producto toEntity(CreateProductoDTO dto) {
        Producto nuevoProducto = Producto.builder()
                .nombre(dto.nombre())
                .precio(dto.precio())
                .build();
        return nuevoProducto;
    }
}

