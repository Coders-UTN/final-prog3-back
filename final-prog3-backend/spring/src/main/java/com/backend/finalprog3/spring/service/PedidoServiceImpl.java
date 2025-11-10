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
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public PedidoDTO crearPedido(CreatePedidoDTO createPedidoDTO) {

        Usuario usuarioEncontrado = usuarioRepository.findById(createPedidoDTO.usuarioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro usuario con el id proporcionado"));

        Pedido nuevoPedido = Pedido.builder()
                .usuario(usuarioEncontrado)
                .fecha(LocalDate.now())
                .estado(Estado.PENDIENTE)
                .direccionEnvio(createPedidoDTO.direccionEnvio())
                .build();

        double total = 0.0;
        for (CreateDetallePedidoDTO itemPedido : createPedidoDTO.items()){

            Producto productoEncontrado = productoRepository.findByIdAndActivoTrue(itemPedido.productoId())
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

            int stockDisponible = productoEncontrado.getStock();
            int cantidadPedida = itemPedido.cantidad();

            if (stockDisponible < cantidadPedida) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente para realizar el pedido");
            }

            double precioUnitario = productoEncontrado.getPrecio();
            double subtotal = precioUnitario * cantidadPedida;
            total += subtotal;

            DetallePedido nuevoItem = DetallePedido.builder()
                    .producto(productoEncontrado)
                    .precioUnitario(precioUnitario)
                    .cantidad(cantidadPedida)
                    .subtotal(subtotal)
                    .build();

            nuevoPedido.agregarDetalle(nuevoItem);

            int nuevoStock = stockDisponible - cantidadPedida;
            productoEncontrado.setStock(nuevoStock);

            productoRepository.save(productoEncontrado);
        }

        nuevoPedido.setTotal(total);
        Pedido pedidoGuardado = pedidoRepository.save(nuevoPedido);
        return pedidoMapper.toDTO(pedidoGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllPedidos(){
        List<Pedido> listaPedidos = pedidoRepository.findAllPedidosWithDetails();

        return listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PedidoDTO findById(long id) {
        Pedido pedidoEncontrado = pedidoRepository.findByIdWithDetails(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));
        return pedidoMapper.toDTO(pedidoEncontrado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllByUsuarioId(long usuarioId) {
        List<Pedido> listaPedidos = pedidoRepository.findAllByUsuarioIdWithDetails(usuarioId);

        return listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllByEstado(String estado) {
        Estado estadoEnum = convertirStringAEstado(estado);
        List<Pedido> listaPedidos = pedidoRepository.findAllByEstadoWithDetails(estadoEnum);

        return listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> findAllByEstadoAndUsuarioId(String estado, long usuarioId) {
        Estado estadoEnum = convertirStringAEstado(estado);
        List<Pedido> listaPedidos = pedidoRepository.findAllByEstadoAndUsuarioIdWithDetails(estadoEnum, usuarioId);
        return listaPedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoDTO modificarEstado(Long id, String estado) {
        Estado estadoEnum = convertirStringAEstado(estado);

        Pedido pedido = pedidoRepository.findByIdWithDetails(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        Estado estadoActual = pedido.getEstado();

        if (estadoActual == Estado.TERMINADO || estadoActual == Estado.CANCELADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Este pedido ya se encuentra en un estado final y no puede ser modificado.");
        }

        pedido.setEstado(estadoEnum);

        if (estadoEnum == Estado.TERMINADO) {
            for (DetallePedido detalle : pedido.getDetallePedido()) {
                Producto producto = detalle.getProducto();
                if (producto.getStock() == 0) {
                    producto.setActivo(false);
                    productoRepository.save(producto);
                }
            }
        }

        return pedidoMapper.toDTO(pedidoRepository.save(pedido));
    }

    @Override
    @Transactional
    public PedidoDTO cancelarPedido(Long id){
        Pedido pedido = pedidoRepository.findByIdWithDetails(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido no encontrado"));

        if (pedido.getEstado() == Estado.CANCELADO || pedido.getEstado() == Estado.TERMINADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No puede cancelarse un pedido finalizado");

        }

        for (DetallePedido detalle : pedido.getDetallePedido()) {
            Producto producto = detalle.getProducto();
            int cantidadDevuelta = detalle.getCantidad();
            int nuevoStock = producto.getStock() + cantidadDevuelta;

            producto.setStock(nuevoStock);

            if (nuevoStock > 0) {
                producto.setActivo(true);
            }

            productoRepository.save(producto);
        }

        pedido.setEstado(Estado.CANCELADO);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(pedidoGuardado);
    }

    private Estado convertirStringAEstado(String status) {
        try {
            return Estado.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Estado no válido: " + status);
        }
    }
}