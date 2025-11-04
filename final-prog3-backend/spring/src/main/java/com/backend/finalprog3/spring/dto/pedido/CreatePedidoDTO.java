package com.backend.finalprog3.spring.dto.pedido;

import java.util.List;

public record CreatePedidoDTO(
        Long usuarioId,
        List<CreateDetallePedidoDTO> items

) {
}
