package com.backend.finalprog3.spring.mapper;

import com.backend.finalprog3.spring.dto.producto.CreateProductoDTO;
import com.backend.finalprog3.spring.dto.producto.ProductoDTO;
import com.backend.finalprog3.spring.entity.Categoria;
import com.backend.finalprog3.spring.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoDTO toDTO(Producto producto) {
        Categoria categoria = producto.getCategoria();

        Long categoriaId = (categoria != null) ? categoria.getId() : null;
        String nombreCategoria = (categoria != null) ? categoria.getNombre() : null;

        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                categoriaId,
                nombreCategoria,
                producto.getStock(),
                producto.getDescripcion(),
                producto.getImagen()
        );
    }

    public Producto toEntity(CreateProductoDTO dto) {
        Producto nuevoProducto = Producto.builder()
                .nombre(dto.nombre())
                .precio(dto.precio())
                .stock(dto.stock())
                .descripcion(dto.descripcion())
                .imagen(dto.imagen())
                .build();
        return nuevoProducto;
    }
}