package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.categoria.CategoriaDTO;
import com.backend.finalprog3.spring.dto.categoria.CreateCategoriaDTO;
import com.backend.finalprog3.spring.entity.Categoria;
import com.backend.finalprog3.spring.mapper.CategoriaMapper;
import com.backend.finalprog3.spring.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriaMapper categoriaMapper;

    @Override
    public CategoriaDTO crearCategoria(CreateCategoriaDTO categoria) {

        if (categoriaRepository.existsByNombreIgnoreCase(categoria.nombre())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"El nombre de la categoria ya existe");
        }

        Categoria nuevaCategoria = categoriaMapper.toEntity(categoria);
        categoriaRepository.save(nuevaCategoria);
        return categoriaMapper.toDto(nuevaCategoria);
    }

    @Override
    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No se encontro una categoria con el id especificado"));

        return categoriaMapper.toDto(categoria);
    }

    @Override
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaDTO modificarCategoria(Long id, CreateCategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "No se encontro una categoria con el id especificado"));

        categoria.setNombre(categoriaDTO.nombre());
        categoriaRepository.save(categoria);
        return categoriaMapper.toDto(categoria);

    }

    @Override
    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public CategoriaDTO findByNombreIgnoreCase(String nombre) {
        Categoria categoria = categoriaRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro la categoria"));

        return categoriaMapper.toDto(categoria);
    }
}
