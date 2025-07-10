package com.prueba.banco.application.mapper;

import com.prueba.banco.application.dto.TransaccionesDTO;
import com.prueba.banco.domain.model.Transacciones;
import org.springframework.stereotype.Component;

@Component
public class TransaccionMapper {
    public TransaccionesDTO toDto(Transacciones transaccion) {
        return TransaccionesDTO.builder()
                .id(transaccion.getId())
                .tipo(transaccion.getTipo())
                .monto(transaccion.getMonto())
                .productoOrigenId(transaccion.getProductoOrigen() != null ? transaccion.getProductoOrigen().getId() : null)
                .productoDestinoId(transaccion.getProductoDestino() != null ? transaccion.getProductoDestino().getId() : null)
                .fecha(transaccion.getFecha())
                .estado(transaccion.getEstado())
                .build();
    }

    public Transacciones toDomain(TransaccionesDTO transaccionDTO) {
        return Transacciones.builder()
                .id(transaccionDTO.getId())
                .tipo(transaccionDTO.getTipo())
                .monto(transaccionDTO.getMonto())
                .fecha(transaccionDTO.getFecha())
                .estado(transaccionDTO.getEstado())
                .build();
    }
}
