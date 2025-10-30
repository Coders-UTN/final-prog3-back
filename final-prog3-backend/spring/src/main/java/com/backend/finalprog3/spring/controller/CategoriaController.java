package com.backend.finalprog3.spring.controller;

import com.backend.finalprog3.spring.dto.categoria.CreateCategoriaDTO;
import com.backend.finalprog3.spring.dto.categoria.CategoriaDTO;
import com.backend.finalprog3.spring.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "http://localhost:5173")
public class CategoriaController {

    @Autowired private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CreateCategoriaDTO createDTO) {
        CategoriaDTO nuevaCategoria = categoriaService.crearCategoria(createDTO);
        return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CategoriaDTO> findAllCategorias() {
        return categoriaService.findAllCategorias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findCategoriaById(@PathVariable Long id) {
        CategoriaDTO categoriaDTO = categoriaService.findCategoriaById(id);
        return ResponseEntity.ok(categoriaDTO);
    }

    @GetMapping("/nombre/{name}")
    public ResponseEntity<CategoriaDTO> findCategoriaByName(@PathVariable String name) {
        CategoriaDTO categoriaDTO = categoriaService.findByNombreIgnoreCase(name);
        return ResponseEntity.ok(categoriaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> modificarCategoria(
            @PathVariable Long id,
            @RequestBody CreateCategoriaDTO updateDTO) {

        CategoriaDTO categoriaActualizada = categoriaService.modificarCategoria(id, updateDTO);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }
}