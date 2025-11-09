package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.producto.CreateProductoDTO;
import com.backend.finalprog3.spring.dto.producto.ProductoDTO;

import java.util.List;

public interface ProductoService {

    ProductoDTO crearProducto(CreateProductoDTO createProductoDTO);
    List<ProductoDTO> findAllProductos();
    List<ProductoDTO> findAllByCategoriaId(Long id);
    ProductoDTO findProductoById(Long id);
    ProductoDTO findProductoByName(String name);
    ProductoDTO modificarProducto(Long id, CreateProductoDTO updateDTO);

    void eliminarProducto(Long id);


}
