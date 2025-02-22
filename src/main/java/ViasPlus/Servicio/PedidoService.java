package ViasPlus.Servicio;

import ViasPlus.DTO.PedidoDTO;
import ViasPlus.Estatus.EstatusPedido;
import ViasPlus.Modelo.Clientes;
import ViasPlus.Modelo.Conductor;
import ViasPlus.Modelo.Pedido;
import ViasPlus.Repositorio.ClienteRepository;
import ViasPlus.Repositorio.ConductorRepository;
import ViasPlus.Repositorio.PedidoRepository;
import ViasPlus.Respuesta.ClienteResponse;
import ViasPlus.Respuesta.ConductorResponse;
import ViasPlus.Respuesta.PedidoResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    /**
     * Registrar un nuevo pedido.
     */
    @Transactional
    public ResponseEntity<Pedido> registrarPedido(PedidoDTO pedidoDTO, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Clientes> clienteOpt = clienteRepository.findById(pedidoDTO.cliente_id());
        Optional<Conductor> conductorOpt = conductorRepository.findById(pedidoDTO.conductor_id());

        if (clienteOpt.isEmpty() || conductorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Cliente o Conductor no encontrado.");
        }

        Pedido nuevoPedido = new Pedido(
                clienteOpt.get(),
                conductorOpt.get(),
                pedidoDTO.direccion(),
                EstatusPedido.EN_PROCESO, // Estatus por defecto
                pedidoDTO.articulo(),
                pedidoDTO.negocio(),
                LocalDate.now()
        );

        pedidoRepository.save(nuevoPedido);

        URI location = uriComponentsBuilder.path("/pedidos/{id}")
                .buildAndExpand(nuevoPedido.getId()).toUri();

        return ResponseEntity.created(location).body(crearPedidoResponse(nuevoPedido));
    }

    /**
     * Listar todos los pedidos.
     */
    public List<PedidoResponse> listarPedidos() {
        return pedidoRepository.findAll().stream()
                .map(this::crearPedidoResponse)
                .toList();
    }

    /**
     * Obtener un pedido por ID.
     */
    public ResponseEntity<?> obtenerPedido(Long id) {
        return pedidoRepository.findById(id)
                .map(pedido -> ResponseEntity.ok(crearPedidoResponse(pedido)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Actualizar un pedido.
     */
    @Transactional
    public ResponseEntity<?> actualizarPedido(Long id, PedidoDTO pedidoDTO) {
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        Optional<Clientes> clienteOpt = clienteRepository.findById(pedidoDTO.cliente_id());
        Optional<Conductor> conductorOpt = conductorRepository.findById(pedidoDTO.conductor_id());

        if (pedidoOpt.isEmpty() || clienteOpt.isEmpty() || conductorOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Pedido, Cliente o Conductor no encontrado.");
        }

        Pedido pedido = pedidoOpt.get();
        pedido.setCliente(clienteOpt.get());
        pedido.setConductor(conductorOpt.get());
        pedido.setDireccion(pedidoDTO.direccion());
        pedido.setArticulo(pedidoDTO.articulo());
        pedido.setNegocio(pedidoDTO.negocio());

        pedidoRepository.save(pedido);

        return ResponseEntity.ok(crearPedidoResponse(pedido));
    }

    /**
     * Eliminar un pedido por ID.
     */
    @Transactional
    public ResponseEntity<?> eliminarPedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return ResponseEntity.ok("Pedido eliminado correctamente.");
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Método auxiliar para crear un PedidoResponse SIN calificación.
     */
    private PedidoResponse crearPedidoResponse(Pedido pedido) {
        // Crear ClienteResponse
        ClienteResponse clienteResponse = new ClienteResponse(
                pedido.getCliente().getId(),
                pedido.getCliente().getNombre(),
                pedido.getCliente().getCorreo()
        );

        // Crear ConductorResponse
        ConductorResponse conductorResponse = new ConductorResponse(
                pedido.getConductor().getId(),
                pedido.getConductor().getNombre(),
                pedido.getConductor().getLicencia()
        );

        // Crear y devolver PedidoResponse
        return new PedidoResponse(
                pedido.getId(),
                pedido.getArticulo(),
                pedido.getNegocio(),
                pedido.getEstatus().name(),
                pedido.getFecha().toString(),
                conductorResponse,
                clienteResponse
        );
    }
}