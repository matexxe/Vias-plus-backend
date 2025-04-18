package ViasPlus.Repositorio;

import ViasPlus.Modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByNombre(String nombre);
    Optional<Cliente> findByCorreo(String correo);
}

