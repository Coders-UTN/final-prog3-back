package com.backend.finalprog3.spring.service;

import com.backend.finalprog3.spring.dto.pedido.CreateDetallePedidoDTO;
import com.backend.finalprog3.spring.dto.pedido.CreatePedidoDTO;
import com.backend.finalprog3.spring.dto.pedido.PedidoDTO;
import com.backend.finalprog3.spring.entity.*;
import com.backend.finalprog3.spring.mapper.PedidoMapper;
import com.backend.finalprog3.spring.repository.PedidoRepository;
import com.backend.finalprog3.spring.repository.ProductoRepository;
import com.backend.finalprog3.spring.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PedidoMapper pedidoMapper;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public PedidoDTO crearPedido(CreatePedidoDTO createPedidoDTO) {

        //buscarmos usuario en base de datos
        Usuario usuarioEncontrado = usuarioRepository.findById(createPedidoDTO.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro usuario con el id proporcionado"));

        //creamos el nuevo pedido
        Pedido nuevoPedido = Pedido.builder()
                .usuario(usuarioEncontrado)
                .fecha(LocalDate.now())
                .estado(Estado.PENDIENTE)
                .build();

        double total = 0.0;
        //iteramos sober los itempedido del DTO de creacion
        for (CreateDetallePedidoDTO itemPedido : createPedidoDTO.items()){

            //si el producto no existe o no hay stock disponible lanzamos error
            Producto productoEncontrado = productoRepository.findById(itemPedido.productoId())
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            int stockDisponible = productoEncontrado.getStock();
            if (stockDisponible < itemPedido.cantidad()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente para realizar el pedido");
            }
            //asignamos precio unitario, subtotal y sumamos al total general
            double precioUnitario = productoEncontrado.getPrecio();
            double subtotal = precioUnitario * itemPedido.cantidad();
            total += subtotal;

            DetallePedido nuevoItem = DetallePedido.builder()
                    .producto(productoEncontrado)
                    .precioUnitario(precioUnitario)
                    .cantidad(itemPedido.cantidad())
                    .subtotal(subtotal)
                    .build();

            nuevoPedido.agregarDetalle(nuevoItem);
            productoEncontrado.setStock(stockDisponible - itemPedido.cantidad());
            //actualizamos producto en base de datos
            productoRepository.save(productoEncontrado);
        }
        //asignamos total, guardamos persistencia y devolvemos el DTO
        nuevoPedido.setTotal(total);
        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);
        return pedidoMapper.toDTO(pedidoGuardado);
    }

    @Override
    public List<PedidoDTO> findAllPedidos(){
        List<Pedido> listaPedidos = pedidoRepository.findAll();

        return listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO findById(long id) {
        Pedido pedidoEncontrado = pedidoRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        return pedidoMapper.toDTO(pedidoEncontrado);
    }

    @Override
    public List<PedidoDTO> findAllByUsuarioId(long usuarioId) {
        List<Pedido> listaPedidos = pedidoRepository.findAllByUsuarioId(usuarioId);

        return listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> findAllByEstado(String estado) {
        Estado estadoEnum = convertirStringAEstado(estado);
        List<Pedido> listaPedidos = pedidoRepository.findAllByEstado(estadoEnum);

        return listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PedidoDTO> findAllByEstadoAndUsuarioId(String estado, long usuarioId) {
        Estado estadoEnum = convertirStringAEstado(estado);
        List<Pedido> listaPedidos = pedidoRepository.findAllByEstadoAndUsuarioId(estadoEnum, usuarioId);
        return listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO modificarEstado(Long id, String estado) {
        Estado estadoEnum = convertirStringAEstado(estado);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        pedido.setEstado(estadoEnum);
        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    @Override
    public void cancelarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        if (pedido.getEstado() != Estado.PENDIENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puede cancelarse un pedido finalizado");
        }
        //devolver stock de productos que no van a utilizarse
        for (DetallePedido detalle : pedido.getDetallePedido()) {
            Producto producto = detalle.getProducto();
            int cantidadDevuelta = detalle.getCantidad();
            producto.setStock(producto.getStock() + cantidadDevuelta);
            productoRepository.save(producto);
        }

        //setear pedido a cancelado y persistirlo
        pedido.setEstado(Estado.CANCELADO);
        pedidoRepository.save(pedido);
    }

    private Estado convertirStringAEstado(String status) {
        try {
            return Estado.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado no válido: " + status);
        }
    }
}
