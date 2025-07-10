package com.prueba.banco.domain.ports;

import com.prueba.banco.domain.model.Producto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductoService {
    Producto crearCuentaAhorros(Long clienteId, boolean exentaGMF);
    Producto crearCuentaCorriente(Long clienteId, boolean exentaGMF);
    Producto cambiarEstadoCuenta(String numeroCuenta, String nuevoEstado);
    Optional<Producto> buscarPorNumeroCuenta(String numeroCuenta);
}