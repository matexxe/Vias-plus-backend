package ViasPlus.Controlador;

import ViasPlus.Respuesta.ConductorResponse;
import ViasPlus.Servicio.ConductorService;
import ViasPlus.Modelo.Conductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/conductores")
public class ConductorController {


    @Autowired
    private ConductorService conductorService;


    // Obtener todos los conductores
    @GetMapping
    public List<ConductorResponse> listarConductores() {
        return conductorService.findAll().stream()
                .map(this::crearConductorResponse)
                .collect(Collectors.toList());
    }

    // Obtener un conductor por ID
    @GetMapping("/{id}")
    public ResponseEntity<ConductorResponse> obtenerConductorPorId(@PathVariable Long id) {
        return conductorService.findById(id)
                .map(conductor -> ResponseEntity.ok(crearConductorResponse(conductor)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo conductor
    @PostMapping
    public ResponseEntity<ConductorResponse> crearConductor(@RequestBody Conductor conductor, UriComponentsBuilder uriComponentsBuilder) {
        Conductor nuevoConductor = conductorService.save(conductor);

        URI location = uriComponentsBuilder.path("/conductores/{id}")
                .buildAndExpand(nuevoConductor.getId()).toUri();

        return ResponseEntity.created(location).body(new ConductorResponse(
                nuevoConductor.getId(),
                nuevoConductor.getNombre(),
                nuevoConductor.getLicencia()
        ));
    }


    // Actualizar un conductor
    @PutMapping("/{id}")
    public ResponseEntity<ConductorResponse> actualizarConductor(@PathVariable Long id, @RequestBody Conductor detallesConductor) {
        return conductorService.update(id, detallesConductor)
                .map(conductor -> ResponseEntity.ok(crearConductorResponse(conductor)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    //  Eliminar un conductor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConductor(@PathVariable Long id) {
        if (conductorService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Método privado para mapear Conductor a ConductorResponse
    private ConductorResponse crearConductorResponse(Conductor conductor) {
        return new ConductorResponse(
                conductor.getId(),
                conductor.getNombre(),
                conductor.getLicencia()
        );
    }
}
