package com.backend.finalprog3.spring.dto.pedido;

import com.backend.finalprog3.spring.entity.Estado;

import java.time.LocalDate;
import java.util.List;

public record PedidoDTO(
        Long id,
        LocalDate fecha,
        Estado estado,
        double total,
        List<DetallePedidoDTO> detallePedidoDTO,
        Long usuarioId,
        String usuarioNombre,
        String direccionEnvio
) {
}
