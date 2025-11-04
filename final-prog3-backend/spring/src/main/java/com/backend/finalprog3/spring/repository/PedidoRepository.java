package com.backend.finalprog3.spring.repository;

import com.backend.finalprog3.spring.dto.pedido.PedidoDTO;
import com.backend.finalprog3.spring.entity.Estado;
import com.backend.finalprog3.spring.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByUsuarioId(long id);
    List<Pedido> findAllByEstado(Estado estado);
    List<Pedido> findAllByEstadoAndUsuarioId(Estado estado, long usuarioId);
}
