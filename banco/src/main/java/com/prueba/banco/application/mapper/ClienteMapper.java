package com.prueba.banco.application.mapper;

import com.prueba.banco.application.dto.ClienteDTO;
import com.prueba.banco.domain.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
        public ClienteDTO toDto(Cliente cliente) {
            return ClienteDTO.builder()
                    .id(cliente.getId())
                    .tipoIdentificacion(cliente.getTipoIdentificacion())
                    .numeroIdentificacion(cliente.getNumeroIdentificacion())
                    .nombres(cliente.getNombres())
                    .apellido(cliente.getApellido())
                    .email(cliente.getEmail())
                    .fechaNacimiento(cliente.getFechaNacimiento())
                    .build();
        }

        public Cliente toDomain(ClienteDTO clienteDTO) {
            return Cliente.builder()
                    .id(clienteDTO.getId())
                    .tipoIdentificacion(clienteDTO.getTipoIdentificacion())
                    .numeroIdentificacion(clienteDTO.getNumeroIdentificacion())
                    .nombres(clienteDTO.getNombres())
                    .apellido(clienteDTO.getApellido())
                    .email(clienteDTO.getEmail())
                    .fechaNacimiento(clienteDTO.getFechaNacimiento())
                    .build();
        }
    }