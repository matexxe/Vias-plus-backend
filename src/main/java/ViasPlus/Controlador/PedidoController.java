package ViasPlus.Controlador;

import ViasPlus.DTO.PedidoDTO;
import ViasPlus.Respuesta.PedidoResponse;
import ViasPlus.Servicio.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    /**
     * Registrar un nuevo pedido usando un DTO.
     */
    @PostMapping
    public ResponseEntity<?> registrarPedido(@RequestBody PedidoDTO pedidoDTO, UriComponentsBuilder uriComponentsBuilder) {
        return pedidoService.registrarPedido(pedidoDTO, uriComponentsBuilder);
    }


    /**
     * Obtener todos los pedidos.
     */
    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.listarPedidos());
    }

    /**
     * Obtener un pedido por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPedido(@PathVariable Long id) {
        return pedidoService.obtenerPedido(id);
    }

    /**
     * Actualizar un pedido usando un DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPedido(@PathVariable Long id, @RequestBody @Valid PedidoDTO pedidoDTO) {
        return pedidoService.actualizarPedido(id, pedidoDTO);
    }

    /**
     * Eliminar un pedido por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPedido(@PathVariable Long id) {
        return pedidoService.eliminarPedido(id);
    }
}
