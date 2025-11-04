package com.backend.finalprog3.spring.dto.pedido;


public record CreateDetallePedidoDTO(
        Long productoId,
        int cantidad
) {
}
