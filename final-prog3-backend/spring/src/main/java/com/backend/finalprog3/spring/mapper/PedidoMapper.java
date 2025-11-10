package com.backend.finalprog3.spring.mapper;

import com.backend.finalprog3.spring.dto.pedido.DetallePedidoDTO;
import com.backend.finalprog3.spring.dto.pedido.PedidoDTO;
import com.backend.finalprog3.spring.entity.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoMapper {

@Autowired
private DetallePedidoMapper detallePedidoMapper;
    public PedidoDTO toDTO(Pedido pedido) {

        List<DetallePedidoDTO> detallePedidoDTO = pedido.getDetallePedido().stream()
                .map(detallePedidoMapper::toDto).collect(Collectors.toList());

        return new PedidoDTO(
                pedido.getId(),
                pedido.getFecha(),
                pedido.getEstado(),
                pedido.getTotal(),
                detallePedidoDTO,
                pedido.getUsuario().getId(),
                pedido.getUsuario().getApellido() + ", " + pedido.getUsuario().getNombre(),
                pedido.getDireccionEnvio()
        );
    }
}
