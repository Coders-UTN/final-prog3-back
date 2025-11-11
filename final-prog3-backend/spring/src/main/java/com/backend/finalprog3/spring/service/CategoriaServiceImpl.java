package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.categoria.CategoriaDTO;
import com.backend.finalprog3.spring.dto.categoria.CreateCategoriaDTO;
import com.backend.finalprog3.spring.entity.Categoria;
import com.backend.finalprog3.spring.exceptions.categoria.CategoriaAlreadyExistsException;
import com.backend.finalprog3.spring.exceptions.categoria.CategoriaNotFoundException;
import com.backend.finalprog3.spring.mapper.CategoriaMapper;
import com.backend.finalprog3.spring.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriaMapper categoriaMapper;

    @Override
    @Transactional
    public CategoriaDTO crearCategoria(CreateCategoriaDTO categoria) {

        Optional<Categoria> opcionalCategoria = categoriaRepository.findCategoriaByNombreIgnoreCase(categoria.nombre());

        if (opcionalCategoria.isPresent()) {
            Categoria categoriaEncontrada = opcionalCategoria.get();

            if (categoriaEncontrada.isActivo()) {
                throw new CategoriaAlreadyExistsException("El nombre de la categoria ya existe");
            } else {
                categoriaEncontrada.setDescripcion(categoria.descripcion());
                categoriaEncontrada.setImagen(categoria.imagen());
                categoriaEncontrada.setActivo(true);

                Categoria categoriaGuardada = categoriaRepository.save(categoriaEncontrada);
                return categoriaMapper.toDto(categoriaGuardada);
            }
        }

        Categoria nuevaCategoria = categoriaMapper.toEntity(categoria);
        categoriaRepository.save(nuevaCategoria);
        return categoriaMapper.toDto(nuevaCategoria);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO findCategoriaById(Long id) {
        Categoria categoria = categoriaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new CategoriaNotFoundException("No se encontro una categoria con el id especificado"));

        return categoriaMapper.toDto(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> findAllCategorias() {
        return categoriaRepository.findByActivoTrue().stream()
                .map(categoriaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaDTO modificarCategoria(Long id, CreateCategoriaDTO categoriaDTO) {
        // 1. Buscar la original
        Categoria categoria = categoriaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new CategoriaNotFoundException("No se encontró una categoría con el id especificado"));

        // 2. Buscar por nombre
        Optional<Categoria> existente = categoriaRepository.findByNombreIgnoreCase(categoriaDTO.nombre());

        if (existente.isPresent() && !existente.get().getId().equals(id)) {
            Categoria duplicada = existente.get();

            if (duplicada.isActivo()) {
                throw new CategoriaAlreadyExistsException("El nombre de la categoría ya existe");
            }

            // Reactivar inactiva
            duplicada.setDescripcion(categoriaDTO.descripcion());
            duplicada.setImagen(categoriaDTO.imagen());
            duplicada.setActivo(true);
            categoriaRepository.save(duplicada);
            return categoriaMapper.toDto(duplicada);
        }

        // 3. Si no hay conflictos, modificar la original
        categoria.setNombre(categoriaDTO.nombre());
        categoria.setDescripcion(categoriaDTO.descripcion());
        categoria.setImagen(categoriaDTO.imagen());

        categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new CategoriaNotFoundException("No se encontro una categoria con el id especificado"));

        categoria.setActivo(false);

        if (categoria.getProducto() != null) {
            categoria.getProducto().forEach(p -> p.setActivo(false));
        }

        categoriaRepository.save(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO findByNombreIgnoreCase(String nombre) {
        Categoria categoria = categoriaRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new CategoriaNotFoundException("No se encontro la categoria"));

        return categoriaMapper.toDto(categoria);
    }
}