package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.pedido.CreatePedidoDTO;
import com.backend.finalprog3.spring.dto.pedido.PedidoDTO;
import com.backend.finalprog3.spring.entity.Estado;

import java.util.List;

public interface PedidoService {
    PedidoDTO crearPedido(CreatePedidoDTO createPedidoDTO);
    List<PedidoDTO> findAllPedidos();
    PedidoDTO findById(long id);
    List<PedidoDTO> findAllByUsuarioId(long usuarioId);
    List<PedidoDTO> findAllByEstado(String estado);
    List<PedidoDTO> findAllByEstadoAndUsuarioId(String estado, long usuarioId);
    PedidoDTO modificarEstado(Long id, String estado);
    PedidoDTO cancelarPedido(Long id);
}
