package ViasPlus.Respuesta;

import ViasPlus.Estatus.EstatusPedido;
import java.time.LocalDate;

/**
 * Representa la respuesta de un pedido con los datos esenciales.
 */
public record PedidoResponse(
        Long id,
        String articulo,
        String negocio,
        String estatus,
        String fecha,
        ConductorResponse conductor,
        ClienteResponse cliente
) {}

