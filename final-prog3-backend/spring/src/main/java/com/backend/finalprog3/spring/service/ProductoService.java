package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.producto.CreateProductoDTO;
import com.backend.finalprog3.spring.dto.producto.ProductoDTO;

import java.util.List;

public interface ProductoService {
    void crearProducto(CreateProductoDTO producto);
    List<ProductoDTO> findAllProductos();
    ProductoDTO  findProductoById(Integer id);
    ProductoDTO findProductoByName(String name);
    ProductoDTO modificarProducto(ProductoDTO productoDTO);
    void eliminarProducto(Integer id);
}
