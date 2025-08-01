package com.prueba.banco.domain.ports;

import com.prueba.banco.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente crearCliente(Cliente cliente);
    Optional<Cliente> actualizarCliente(Long id, Cliente clienteActualizado);
    void eliminarCliente(Long id);
    Optional<Cliente> buscarPorId(Long id);
    List<Cliente> listarTodos();
}
