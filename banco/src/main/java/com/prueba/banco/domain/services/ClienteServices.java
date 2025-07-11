package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ClienteRepository;
import com.prueba.banco.domain.ports.ClienteService;
import com.prueba.banco.domain.ports.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteServices implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public ClienteServices(ClienteRepository clienteRepository, ProductoRepository productoRepository) {
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        validarCliente(cliente);
        cliente.setFechaCreacion(LocalDateTime.now());
        return clienteRepository.guardar(cliente);
    }

    @Override
    public Optional<Cliente> actualizarCliente(Long id, Cliente clienteActualizado) {
        validarCliente(clienteActualizado);

        return clienteRepository.buscarPorId(id).map(cliente -> {
            actualizarCamposCliente(cliente, clienteActualizado);
            return clienteRepository.guardar(cliente);
        });
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.buscarPorId(id).ifPresent(cliente -> {
            validarEliminacionCliente(cliente);
            clienteRepository.eliminar(cliente);
        });
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id);
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.listarTodos();
    }

    private void validarCliente(Cliente cliente) {
        if (!cliente.esMayorDeEdad()) {
            throw new IllegalArgumentException("El cliente debe ser mayor de edad");
        }

        if (cliente.getEmail() != null && !cliente.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new IllegalArgumentException("Formato de email inválido");
        }

        if (cliente.getNombres() == null || cliente.getNombres().length() < 2) {
            throw new IllegalArgumentException("El nombre debe tener al menos 2 caracteres");
        }

        if (cliente.getApellido() == null || cliente.getApellido().length() < 2) {
            throw new IllegalArgumentException("El apellido debe tener al menos 2 caracteres");
        }
    }

    private void actualizarCamposCliente(Cliente actual, Cliente actualizado) {
        actual.setTipoIdentificacion(actualizado.getTipoIdentificacion());
        actual.setNumeroIdentificacion(actualizado.getNumeroIdentificacion());
        actual.setNombres(actualizado.getNombres());
        actual.setApellido(actualizado.getApellido());
        actual.setEmail(actualizado.getEmail());
        actual.setFechaNacimiento(actualizado.getFechaNacimiento());
    }

    private void validarEliminacionCliente(Cliente cliente) {
        List<Producto> productos = productoRepository.listarPorCliente(cliente);
        if (!productos.isEmpty()) {
            throw new IllegalStateException("No se puede eliminar un cliente con productos vinculados");
        }
    }
}