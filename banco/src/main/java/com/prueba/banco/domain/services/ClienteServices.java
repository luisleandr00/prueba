package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.ports.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClienteServices {
        private final ClienteRepository clienteRepository;

    public Cliente crearCliente(Cliente cliente) {
        if (!cliente.esMayorDeEdad()) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad");
        }
        cliente.setFechaCreacion(LocalDateTime.now());
        return clienteRepository.guardar(cliente);
    }
    // Otros métodos
}
