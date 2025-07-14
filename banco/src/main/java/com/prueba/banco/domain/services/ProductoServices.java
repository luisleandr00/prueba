package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.model.CuentaCorriente;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ClienteRepository;
import com.prueba.banco.domain.ports.ProductoRepository;
import com.prueba.banco.domain.ports.ProductoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductoServices implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    public ProductoServices(ProductoRepository productoRepository, ClienteRepository clienteRepository) {
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Producto crearCuentaAhorros(Long clienteId, boolean exentaGMF) {

        if (!clienteRepository.existePorId(clienteId)) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        CuentaAhorros cuenta = new CuentaAhorros();
        cuenta.setTipoCuenta("AHORROS");
        cuenta.setNumeroCuenta(generarNumeroCuenta("53"));
        cuenta.setEstado("ACTIVA");
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setExentaGMF(exentaGMF);
        cuenta.setClienteId(clienteId);

        return productoRepository.guardar(cuenta);
    }

    @Override
    public Producto crearCuentaCorriente(Long clienteId, boolean exentaGMF) {

        if (!clienteRepository.existePorId(clienteId)) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        CuentaCorriente cuenta = new CuentaCorriente();
        cuenta.setTipoCuenta("CORRIENTE");
        cuenta.setNumeroCuenta(generarNumeroCuenta("33"));
        cuenta.setEstado("ACTIVA");
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setExentaGMF(exentaGMF);
        cuenta.setClienteId(clienteId);

        return productoRepository.guardar(cuenta);
    }


    @Override
    public Producto cambiarEstadoCuenta(String numeroCuenta, String nuevoEstado) {
        Producto cuenta = productoRepository.buscarPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        if ("CANCELADA".equalsIgnoreCase(nuevoEstado) && cuenta.getSaldo().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalStateException("No se puede cancelar una cuenta con saldo diferente a cero");
        }

        cuenta.setEstado(nuevoEstado.toUpperCase());
        return productoRepository.guardar(cuenta);
    }

    @Override
    public Optional<Producto> buscarPorNumeroCuenta(String numeroCuenta) {
        return productoRepository.buscarPorNumeroCuenta(numeroCuenta);
    }

    private String generarNumeroCuenta(String prefijo) {
        String numeroGenerado;
        do {
            numeroGenerado = prefijo + String.format("%08d", new Random().nextInt(100000000));
        } while (productoRepository.buscarPorNumeroCuenta(numeroGenerado).isPresent());

        return numeroGenerado;
    }
}