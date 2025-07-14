package com.prueba.banco.infraestructure.mappers;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.infraestructure.persistence.ClienteEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClienteEntityMapper {

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(entity.getId());
        cliente.setTipoIdentificacion(entity.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(entity.getNumeroIdentificacion());
        cliente.setNombres(entity.getNombres());
        cliente.setApellido(entity.getApellido());
        cliente.setEmail(entity.getEmail());
        cliente.setFechaNacimiento(entity.getFechaNacimiento());

        // Manejo seguro de fechas
        LocalDateTime fechaCreacion = entity.getFechaCreacion() != null ?
                entity.getFechaCreacion() : LocalDateTime.now();
        LocalDateTime fechaModificacion = entity.getFechaModificacion() != null ?
                entity.getFechaModificacion() : LocalDateTime.now();

        cliente.setFechaCreacion(fechaCreacion);
        cliente.setFechaModificacion(fechaModificacion);

        return cliente;
    }

    public ClienteEntity toEntity(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setTipoIdentificacion(cliente.getTipoIdentificacion());
        entity.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        entity.setNombres(cliente.getNombres());
        entity.setApellido(cliente.getApellido());
        entity.setEmail(cliente.getEmail());
        entity.setFechaNacimiento(cliente.getFechaNacimiento());

        return entity;
    }
}