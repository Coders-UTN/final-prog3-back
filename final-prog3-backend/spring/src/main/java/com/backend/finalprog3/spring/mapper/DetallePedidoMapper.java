package com.backend.finalprog3.spring.mapper;

import com.backend.finalprog3.spring.dto.pedido.CreateDetallePedidoDTO;
import com.backend.finalprog3.spring.dto.pedido.DetallePedidoDTO;
import com.backend.finalprog3.spring.entity.DetallePedido;
import org.springframework.stereotype.Component;

@Component
public class DetallePedidoMapper {
    public DetallePedidoDTO toDto(DetallePedido detallePedido){
        return new DetallePedidoDTO(
                detallePedido.getId(),
                detallePedido.getCantidad(),
                detallePedido.getProducto().getId(),
                detallePedido.getProducto().getNombre(),
                detallePedido.getPrecioUnitario(),
                detallePedido.getSubtotal()
        );
    }
}
