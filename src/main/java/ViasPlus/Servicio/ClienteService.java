package ViasPlus.Servicio;

import ViasPlus.Respuesta.ClienteResponse;
import ViasPlus.Modelo.Clientes;
import ViasPlus.DTO.DataClientes;
import ViasPlus.Repositorio.ClienteRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public ResponseEntity<?> registrarCliente(DataClientes dataClientes, UriComponentsBuilder uriComponentsBuilder) {
        try {
            if (clienteRepository.findByNombre(dataClientes.nombre()).isPresent()) {
                return ResponseEntity.badRequest().body("El cliente con ese nombre ya existe.");
            }

            if (clienteRepository.findByCorreo(dataClientes.correo()).isPresent()) {
                return ResponseEntity.badRequest().body("El correo ya está en uso.");
            }


            // Crear nuevo cliente
            Clientes cliente = new Clientes(dataClientes);
            cliente.setNombre(dataClientes.nombre());
            cliente.setCorreo(dataClientes.correo());
            cliente.setDireccion(dataClientes.direccion());
            cliente.setTelefono(dataClientes.telefono());

            // Guardar cliente en la base de datos
            clienteRepository.save(cliente);

            // Crear respuesta
            ClienteResponse clienteResponse = new ClienteResponse(cliente.getId(), cliente.getNombre(), cliente.getCorreo());

            // Construir URL del nuevo recurso
            URI url = uriComponentsBuilder.path("/clientes/{id}").buildAndExpand(cliente.getId()).toUri();
            return ResponseEntity.created(url).body(clienteResponse);
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.badRequest().body("Error de validación: " + ex.getMessage());
        }
    }

    public List<Clientes> listarClientes() {
        return clienteRepository.findAll();
    }

    public ResponseEntity<?> obtenerCliente(Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok(new ClienteResponse(cliente.getId(), cliente.getNombre(), cliente.getCorreo())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity<?> actualizarCliente(Long id, DataClientes dataClientes) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNombre(dataClientes.nombre());
            cliente.setCorreo(dataClientes.correo());
            cliente.setDireccion(dataClientes.direccion());
            cliente.setTelefono(dataClientes.telefono());
            clienteRepository.save(cliente);
            return ResponseEntity.ok(new ClienteResponse(cliente.getId(), cliente.getNombre(), cliente.getCorreo()));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity<?> eliminarCliente(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok("Cliente eliminado correctamente.");
        }
        return ResponseEntity.notFound().build();
    }
}
