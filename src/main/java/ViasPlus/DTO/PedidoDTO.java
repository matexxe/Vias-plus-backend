package ViasPlus.DTO;

import ViasPlus.Estatus.EstatusPedido;

public record PedidoDTO(
        Long cliente_id,
        Long conductor_id,
        String direccion,
        EstatusPedido estatus,
        String articulo,
        String negocio
) {}
