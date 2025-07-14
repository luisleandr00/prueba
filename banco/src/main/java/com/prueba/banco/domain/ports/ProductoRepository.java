package com.prueba.banco.domain.ports;

import com.prueba.banco.domain.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    Producto guardar(Producto producto);
    Optional<Producto> buscarPorId(Long id);
    Optional<Producto> buscarPorNumeroCuenta(String numeroCuenta);
    List<Producto> listarPorClienteId(Long clienteId); // Cambiado a recibir ID
    void eliminar(Producto producto);
}