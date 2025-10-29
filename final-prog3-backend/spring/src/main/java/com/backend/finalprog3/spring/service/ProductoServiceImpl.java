package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.producto.CreateProductoDTO;
import com.backend.finalprog3.spring.dto.producto.ProductoDTO;
import com.backend.finalprog3.spring.entity.Categoria;
import com.backend.finalprog3.spring.entity.Producto;
import com.backend.finalprog3.spring.mapper.ProductoMapper;
import com.backend.finalprog3.spring.repository.CategoriaRepository;
import com.backend.finalprog3.spring.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired private ProductoRepository productoRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private ProductoMapper productoMapper;

    @Override
    @Transactional
    public ProductoDTO crearProducto(CreateProductoDTO createDTO) {
        Producto producto = productoMapper.toEntity(createDTO);

        Categoria categoria = categoriaRepository.findById(createDTO.categoriaid())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + createDTO.categoriaid()));

        producto.setCategoria(categoria);

        Producto productoGuardado = productoRepository.save(producto);
        return productoMapper.toDTO(productoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> findAllProductos() {
        List<Producto> productos = productoRepository.findByActivoTrue();
        return productos.stream().map(productoMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findProductoById(Long id) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado o inactivo con id: " + id));
        return productoMapper.toDTO(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO findProductoByName(String name) {
        Producto producto = productoRepository.findByNombreAndActivoTrue(name)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado o inactivo con nombre: " + name));
        return productoMapper.toDTO(producto);
    }

    @Override
    @Transactional
    public ProductoDTO modificarProducto(Long id, CreateProductoDTO updateDTO) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado o inactivo con id: " + id));

        producto.setNombre(updateDTO.nombre());
        producto.setPrecio(updateDTO.precio());
        producto.setStock(updateDTO.stock());

        if (updateDTO.categoriaid() != null && !updateDTO.categoriaid().equals(producto.getCategoria().getId())) {
            Categoria nuevaCategoria = categoriaRepository.findById(updateDTO.categoriaid())
                    .orElseThrow(() -> new RuntimeException("Nueva Categoría no encontrada con id: " + updateDTO.categoriaid()));
            producto.setCategoria(nuevaCategoria);
        }

        Producto productoActualizado = productoRepository.save(producto);
        return productoMapper.toDTO(productoActualizado);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado o inactivo con id: " + id));

        producto.setActivo(false);
        productoRepository.save(producto);
    }
}