package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ClienteRepository;
import com.prueba.banco.domain.ports.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServices {
        private final ClienteRepository clienteRepository;
        private final ProductoRepository  productoRepository;

    public Cliente crearCliente(Cliente cliente) {
        if (!cliente.esMayorDeEdad()) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad");
        }
        cliente.setFechaCreacion(LocalDateTime.now());
        return clienteRepository.guardar(cliente);
    }
    public Optional<Cliente> actualizarCliente(Long id, Cliente clienteActualizado) {
        return clienteRepository.buscarPorId(id).map(cliente -> {
            if (!clienteActualizado.esMayorDeEdad()) {
                throw new IllegalArgumentException("El cliente debe ser mayor de edad");
            }

            // Validar email
            if (clienteActualizado.getEmail() != null && !clienteActualizado.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new IllegalArgumentException("Formato de email inválido");
            }

            // Validar nombres y apellidos
            if (clienteActualizado.getNombres() != null && clienteActualizado.getNombres().length() < 2) {
                throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
            }
            if (clienteActualizado.getApellido() != null && clienteActualizado.getApellido().length() < 2) {
                throw new IllegalArgumentException("El apellido debe tener al menos 2 caracteres");
            }

            cliente.setTipoIdentificacion(clienteActualizado.getTipoIdentificacion());
            cliente.setNumeroIdentificacion(clienteActualizado.getNumeroIdentificacion());
            cliente.setNombres(clienteActualizado.getNombres());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setEmail(clienteActualizado.getEmail());
            cliente.setFechaNacimiento(clienteActualizado.getFechaNacimiento());

            return clienteRepository.guardar(cliente);
        });
    }

    public void eliminarCliente(Long id) {
        clienteRepository.buscarPorId(id).ifPresent(cliente -> {
            List<Producto> productos = productoRepository.listarPorCliente(cliente);
            if (!productos.isEmpty()) {
                throw new IllegalStateException("No se puede eliminar un cliente con productos vinculados");
            }
            clienteRepository.eliminar(cliente);
        });
    }
}
