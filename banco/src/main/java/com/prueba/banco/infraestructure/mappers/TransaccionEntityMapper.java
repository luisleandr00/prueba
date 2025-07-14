package com.prueba.banco.infraestructure.mappers;

import com.prueba.banco.domain.model.Transacciones;
import com.prueba.banco.infraestructure.persistence.TransaccionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransaccionEntityMapper {
    public Transacciones toDomain(TransaccionEntity entity) {
        Transacciones transaccion = new Transacciones();
        transaccion.setId(entity.getId());
        transaccion.setTipo(entity.getTipo());
        transaccion.setMonto(entity.getMonto());
        transaccion.setProductoOrigenId(entity.getProductoOrigenId());
        transaccion.setProductoDestinoId(entity.getProductoDestinoId());
        transaccion.setFecha(entity.getFecha());
        transaccion.setEstado(entity.getEstado());
        return transaccion;
    }

    public TransaccionEntity toEntity(Transacciones transaccion) {
        TransaccionEntity entity = new TransaccionEntity();
        entity.setId(transaccion.getId());
        entity.setTipo(transaccion.getTipo());
        entity.setMonto(transaccion.getMonto());
        entity.setProductoOrigenId(transaccion.getProductoOrigenId());
        entity.setProductoDestinoId(transaccion.getProductoDestinoId());
        entity.setFecha(transaccion.getFecha());
        entity.setEstado(transaccion.getEstado());
        return entity;
    }
}