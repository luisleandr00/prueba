package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.model.CuentaCorriente;
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

        CuentaAhorros cuenta = new CuentaAhorros(); // Usa constructor o builder
        cuenta.setTipoCuenta("AHORROS");
        cuenta.setNumeroCuenta(generarNumeroCuenta("53"));
        cuenta.setEstado("ACTIVA");
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setExentaGMF(exentaGMF);
        cuenta.setCliente(cliente);

        return productoRepository.guardar(cuenta);
    }

    public Producto crearCuentaCorriente(Long clienteId, boolean exentaGMF) {
        Cliente cliente = clienteRepository.buscarPorId(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        CuentaCorriente cuenta = new CuentaCorriente();
        cuenta.setTipoCuenta("AHORROS");
        cuenta.setNumeroCuenta(generarNumeroCuenta("53"));
        cuenta.setEstado("ACTIVA");
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setExentaGMF(exentaGMF);
        cuenta.setCliente(cliente);

        return productoRepository.guardar(cuenta);
    }

    private String generarNumeroCuenta(String prefijo) {
        return prefijo + String.format("%08d", new Random().nextInt(100000000));
    }



    public Producto cambiarEstadoCuenta(String numeroCuenta, String nuevoEstado) {
        Producto cuenta = productoRepository.buscarPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(nuevoEstado) && cuenta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException("No se puede cancelar una cuenta con saldo diferente a cero");
        }

        cuenta.setEstado(nuevoEstado.toUpperCase());
        return productoRepository.guardar(cuenta);
    }
}