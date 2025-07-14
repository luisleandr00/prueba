package com.prueba.banco.domain.ports;

import com.prueba.banco.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente guardar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    List<Cliente> listarTodos();
    void eliminar(Cliente cliente);
    Optional<Cliente> buscarPorIdentificacion(String tipo, String numero);
    boolean existePorId(Long id);
}