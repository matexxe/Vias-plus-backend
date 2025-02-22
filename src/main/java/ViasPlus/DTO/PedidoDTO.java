package ViasPlus.DTO;


public record PedidoDTO(
        Long cliente_id,
        Long conductor_id,
        String direccion,
        String articulo,
        String negocio
) {}

