package com.prueba.banco.application.mapper;

import com.prueba.banco.application.dto.TransaccionesDTO;
import com.prueba.banco.domain.model.Transacciones;
import org.springframework.stereotype.Component;

@Component
public class TransaccionMapper {

    public TransaccionesDTO toDto(Transacciones transaccion) {
        if (transaccion == null) {
            return null;
        }

        TransaccionesDTO dto = new TransaccionesDTO();
        dto.setId(transaccion.getId());
        dto.setTipo(transaccion.getTipo());
        dto.setMonto(transaccion.getMonto());
        dto.setFecha(transaccion.getFecha());
        dto.setEstado(transaccion.getEstado());

        if (transaccion.getProductoOrigen() != null) {
            dto.setProductoOrigenId(transaccion.getProductoOrigen().getId());
        }

        if (transaccion.getProductoDestino() != null) {
            dto.setProductoDestinoId(transaccion.getProductoDestino().getId());
        }

        return dto;
    }

    public Transacciones toDomain(TransaccionesDTO transaccionDTO) {
        if (transaccionDTO == null) {
            return null;
        }

        Transacciones transaccion = new Transacciones();
        transaccion.setId(transaccionDTO.getId());
        transaccion.setTipo(transaccionDTO.getTipo());
        transaccion.setMonto(transaccionDTO.getMonto());
        transaccion.setFecha(transaccionDTO.getFecha());
        transaccion.setEstado(transaccionDTO.getEstado());

        return transaccion;
    }
}