package com.backend.finalprog3.spring.controller;

import com.backend.finalprog3.spring.dto.categoria.CategoriaDTO;
import com.backend.finalprog3.spring.dto.categoria.CreateCategoriaDTO;
import com.backend.finalprog3.spring.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAll(){

        List<CategoriaDTO> listaCategorias = categoriaService.listarCategorias();
        return ResponseEntity.ok(listaCategorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> getById(@PathVariable Long id){ // <-- CORREGIDO

        CategoriaDTO categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CreateCategoriaDTO categoria){

        CategoriaDTO nuevaCategoria = categoriaService.crearCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDTO> getByNombre(@PathVariable String nombre){

        CategoriaDTO categoriaEncontrada = categoriaService.findByNombreIgnoreCase(nombre);
        return ResponseEntity.ok(categoriaEncontrada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> modificarCategoria(@PathVariable Long id, @RequestBody CreateCategoriaDTO categoria){

        CategoriaDTO categoriaEditada = categoriaService.modificarCategoria(id, categoria);
        return ResponseEntity.ok(categoriaEditada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id){
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.ok("Categoria eliminada correctamente");
    }
}

