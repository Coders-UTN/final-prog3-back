package com.backend.finalprog3.spring.repository;

import com.backend.finalprog3.spring.entity.Estado;
import com.backend.finalprog3.spring.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT DISTINCT p FROM Pedido p JOIN FETCH p.usuario LEFT JOIN FETCH p.detallePedido dp LEFT JOIN FETCH dp.producto WHERE p.id = :id")
    Optional<Pedido> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM Pedido p JOIN FETCH p.usuario LEFT JOIN FETCH p.detallePedido dp LEFT JOIN FETCH dp.producto")
    List<Pedido> findAllPedidosWithDetails();

    @Query("SELECT DISTINCT p FROM Pedido p JOIN FETCH p.usuario LEFT JOIN FETCH p.detallePedido dp LEFT JOIN FETCH dp.producto WHERE p.usuario.id = :id")
    List<Pedido> findAllByUsuarioIdWithDetails(@Param("id") long id);

    @Query("SELECT DISTINCT p FROM Pedido p JOIN FETCH p.usuario LEFT JOIN FETCH p.detallePedido dp LEFT JOIN FETCH dp.producto WHERE p.estado = :estado")
    List<Pedido> findAllByEstadoWithDetails(@Param("estado") Estado estado);

    @Query("SELECT DISTINCT p FROM Pedido p JOIN FETCH p.usuario LEFT JOIN FETCH p.detallePedido dp LEFT JOIN FETCH dp.producto WHERE p.estado = :estado AND p.usuario.id = :usuarioId")
    List<Pedido> findAllByEstadoAndUsuarioIdWithDetails(@Param("estado") Estado estado, @Param("usuarioId") long usuarioId);
}