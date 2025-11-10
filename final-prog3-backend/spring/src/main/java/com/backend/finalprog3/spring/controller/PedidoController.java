package com.backend.finalprog3.spring.controller;

import com.backend.finalprog3.spring.dto.pedido.CreatePedidoDTO;
import com.backend.finalprog3.spring.dto.pedido.PedidoDTO;
import com.backend.finalprog3.spring.dto.pedido.UpdateEstadoDTO;
import com.backend.finalprog3.spring.entity.Estado;
import com.backend.finalprog3.spring.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:5173")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody CreatePedidoDTO createPedidoDTO){
        return  ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crearPedido(createPedidoDTO));
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> buscarPedidos(
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) String estado
    ) {

        List<PedidoDTO> pedidos;
        if (usuarioId != null && estado != null) {
            pedidos = pedidoService.findAllByEstadoAndUsuarioId(estado, usuarioId);
        } else if (usuarioId != null) {
            pedidos = pedidoService.findAllByUsuarioId(usuarioId);
        } else if (estado != null) {
            pedidos = pedidoService.findAllByEstado(estado);
        } else {
            pedidos = pedidoService.findAllPedidos();
        }

        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<PedidoDTO> modificarEstado(@PathVariable Long id, @RequestBody UpdateEstadoDTO updateEstadoDTO) {
        return ResponseEntity.ok(pedidoService.modificarEstado(id, updateEstadoDTO.estado()));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<PedidoDTO> cancelarPedido(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.cancelarPedido(id));
    }
}