package ViasPlus.Controlador;

import ViasPlus.DTO.DataClientes;
import ViasPlus.Servicio.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/register")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Registrar un cliente
    @PostMapping
    public ResponseEntity<?> registrarCliente(@RequestBody @Valid DataClientes dataClientes, UriComponentsBuilder uriComponentsBuilder) {
        return clienteService.registrarCliente(dataClientes, uriComponentsBuilder);
    }

    // Listar todos los clientes
    @GetMapping
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Long id) {
        return clienteService.obtenerCliente(id);
    }

    // Actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id, @RequestBody @Valid DataClientes dataClientes) {
        return clienteService.actualizarCliente(id, dataClientes);
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        return clienteService.eliminarCliente(id);
    }
}