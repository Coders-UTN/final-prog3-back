package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.producto.CreateProductoDTO;
import com.backend.finalprog3.spring.dto.producto.ProductoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements  ProductoService {
    @Override
    public void crearProducto(CreateProductoDTO producto) {

    }

    @Override
    public List<ProductoDTO> findAllProductos() {
        return List.of();
    }

    @Override
    public ProductoDTO findProductoById(Integer id) {
        return null;
    }

    @Override
    public ProductoDTO findProductoByName(String name) {
        return null;
    }

    @Override
    public ProductoDTO modificarProducto(ProductoDTO productoDTO) {
        return null;
    }

    @Override
    public void eliminarProducto(Integer id) {

    }
}
