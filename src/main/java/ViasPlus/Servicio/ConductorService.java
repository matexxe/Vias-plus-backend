package ViasPlus.Servicio;

import ViasPlus.Modelo.Conductor;
import ViasPlus.Repositorio.ConductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConductorService {

    @Autowired
    private ConductorRepository conductorRepository;

    // Obtener todos los conductores
    public List<Conductor> findAll() {
        return conductorRepository.findAll();
    }

    // Obtener un conductor por ID
    public Optional<Conductor> findById(Long id) {
        return conductorRepository.findById(id);
    }

    // Crear un nuevo conductor
    public Conductor save(Conductor conductor) {
        return conductorRepository.save(conductor);
    }

    // Actualizar un conductor
    public Optional<Conductor> update(Long id, Conductor detallesConductor) {
        return conductorRepository.findById(id).map(conductor -> {
            conductor.setNombre(detallesConductor.getNombre());
            conductor.setLicencia(detallesConductor.getLicencia());
            conductor.setEntregasTotales(detallesConductor.getEntregasTotales());
            conductor.setCalificacion(detallesConductor.getCalificacion());
            return conductorRepository.save(conductor);
        });
    }

    // Eliminar un conductor
    public boolean delete(Long id) {
        if (conductorRepository.existsById(id)) {
            conductorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

