package com.backend.finalprog3.spring.mapper;

import com.backend.finalprog3.spring.dto.producto.ProductoDTO;
import com.backend.finalprog3.spring.entity.Categoria;
import com.backend.finalprog3.spring.entity.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public ProductoDTO toDTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getCategoria() != null ? producto.getCategoria().getNombre() : null
        );
    }

    public Producto toEntity(ProductoDTO productoDTO) {

        Categoria categoria =  new Categoria(); //implementar busqueda de categoriaid por nombre para generarla

        Producto nuevoProducto = Producto.builder()
                .nombre(productoDTO.nombre())
                .precio(productoDTO.precio())
                .build();

        categoria.agregarProducto(nuevoProducto);
        return nuevoProducto;
    }
}
