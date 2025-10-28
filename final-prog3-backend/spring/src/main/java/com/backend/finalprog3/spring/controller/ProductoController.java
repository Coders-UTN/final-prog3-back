package com.backend.finalprog3.spring.controller;

import com.backend.finalprog3.spring.dto.producto.CreateProductoDTO;
import com.backend.finalprog3.spring.dto.producto.ProductoDTO;
import com.backend.finalprog3.spring.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/productos")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody CreateProductoDTO createDTO) {
        ProductoDTO nuevoProducto = productoService.crearProducto(createDTO);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping("/productos")
    public List<ProductoDTO> findAllProductos() {
        return productoService.findAllProductos();
    }

    @GetMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> findProductoById(@PathVariable Long id) {
        ProductoDTO productoDTO = productoService.findProductoById(id);
        return ResponseEntity.ok(productoDTO);
    }

    @GetMapping("/productos/nombre/{name}")
    public ResponseEntity<ProductoDTO> findProductoByName(@PathVariable String name) {
        ProductoDTO productoDTO = productoService.findProductoByName(name);
        return ResponseEntity.ok(productoDTO);
    }

    @PutMapping("/productos/{id}")
    public ResponseEntity<ProductoDTO> modificarProducto(
            @PathVariable Long id,
            @RequestBody CreateProductoDTO updateDTO) {

        ProductoDTO productoActualizado = productoService.modificarProducto(id, updateDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/productos/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
