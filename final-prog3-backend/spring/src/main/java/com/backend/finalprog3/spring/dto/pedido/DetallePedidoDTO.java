package com.backend.finalprog3.spring.dto.pedido;

public record DetallePedidoDTO(
        Long id,
        int cantidad,
        Long productoId,
        String nombreProducto,
        double precioUnitario,
        double subtotal) {
}
