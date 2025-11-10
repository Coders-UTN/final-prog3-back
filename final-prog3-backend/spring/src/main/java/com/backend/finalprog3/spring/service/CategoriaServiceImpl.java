package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.categoria.CategoriaDTO;
import com.backend.finalprog3.spring.dto.categoria.CreateCategoriaDTO;
import com.backend.finalprog3.spring.entity.Categoria;
import com.backend.finalprog3.spring.mapper.CategoriaMapper;
import com.backend.finalprog3.spring.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de la categoria ya existe");
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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No se encontro una categoria con el id especificado"));

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
        Categoria categoria = categoriaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No se encontro una categoria con el id especificado"));

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
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No se encontro una categoria con el id especificado"));

        categoria.setActivo(false);

        if (categoria.getProducto() != null) {
            categoria.getProducto().forEach(p -> p.setActivo(false));
        }

        categoriaRepository.save(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO findByNombreIgnoreCase(String nombre) {
        Categoria categoria = categoriaRepository.findByNombreIgnoreCaseAndActivoTrue(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro la categoria"));

        return categoriaMapper.toDto(categoria);
    }
}