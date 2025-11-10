package com.backend.finalprog3.spring.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "categoria",
            fetch =FetchType.LAZY,
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Producto> producto = new ArrayList<>();

    private String descripcion;
    private String imagen;

    @Builder.Default
    private boolean activo = true;

    public void agregarProducto(Producto producto){
        this.producto.add(producto);
        producto.setCategoria(this);
    }

    public void eliminarProducto(Producto producto){
        this.producto.remove(producto);
        producto.setCategoria(null);
    }
}