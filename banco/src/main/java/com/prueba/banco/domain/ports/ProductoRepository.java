package com.prueba.banco.domain.ports;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository {
    Producto guardar(Producto producto);

    Optional<Producto> buscarPorId(Long id);

    Optional<Producto> buscarPorNumeroCuenta(String numeroCuenta);

    List<Producto> listarPorCliente(Cliente cliente);

    void eliminar(Producto producto);
}