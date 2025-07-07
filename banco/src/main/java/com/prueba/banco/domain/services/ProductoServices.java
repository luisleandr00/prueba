package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ClienteRepository;
import com.prueba.banco.domain.ports.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ProductoServices {
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    public Producto crearCuentaAhorros(Long clienteId, boolean exentaGMF) {
        Cliente cliente = clienteRepository.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        CuentaAhorros cuenta = CuentaAhorros.builder()
                .tipoCuenta("AHORROS")
                .numeroCuenta(generarNumeroCuenta("53"))
                .estado("ACTIVA")
                .saldo(BigDecimal.ZERO)
                .exentaGMF(exentaGMF)
                .cliente(cliente)
                .build();

        return productoRepository.guardar(cuenta);
    }

    private String generarNumeroCuenta(String prefijo) {
        return prefijo + String.format("%08d", new Random().nextInt(100000000));
    }
}