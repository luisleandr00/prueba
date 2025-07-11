package com.prueba.banco.domain.services;

import com.prueba.banco.application.dto.TransaccionesDTO;
import com.prueba.banco.application.mapper.TransaccionMapper;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.model.Transacciones;
import com.prueba.banco.domain.ports.ProductoRepository;
import com.prueba.banco.domain.ports.TransaccionRepository;
import com.prueba.banco.domain.ports.TransaccionesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransaccionesServicesImpl implements TransaccionesService {

    private final TransaccionRepository transaccionRepository;
    private final ProductoRepository productoRepository;
    private final TransaccionMapper transaccionMapper;

    public TransaccionesServicesImpl(TransaccionRepository transaccionRepository,
                                    ProductoRepository productoRepository,
                                    TransaccionMapper transaccionMapper) {
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
        this.transaccionMapper = transaccionMapper;
    }

    @Override
    @Transactional
    public TransaccionesDTO realizarTransferencia(String numeroOrigen, String numeroDestino, BigDecimal monto) {
        // Validaciones
        if (numeroOrigen.equals(numeroDestino)) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        // Buscar cuentas
        Producto origen = productoRepository.buscarPorNumeroCuenta(numeroOrigen)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));

        Producto destino = productoRepository.buscarPorNumeroCuenta(numeroDestino)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));

        // Validar saldos
        origen.validarSaldo(monto.negate());
        destino.validarSaldo(monto);

        // Actualizar saldos
        origen.setSaldo(origen.getSaldo().subtract(monto));
        destino.setSaldo(destino.getSaldo().add(monto));

        productoRepository.guardar(origen);
        productoRepository.guardar(destino);

        // Crear transacción sin Builder
        Transacciones transaccion = new Transacciones();
        transaccion.setTipo("TRANSFERENCIA");
        transaccion.setMonto(monto);
        transaccion.setProductoOrigen(origen);
        transaccion.setProductoDestino(destino);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado("EXITOSA");

        Transacciones transaccionGuardada = transaccionRepository.guardar(transaccion);
        return transaccionMapper.toDto(transaccionGuardada);
    }

    @Override
    @Transactional
    public TransaccionesDTO realizarRetiro(String numeroCuenta, BigDecimal monto) {
        // Validaciones
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        Producto cuenta = productoRepository.buscarPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        // Validar saldo
        cuenta.validarSaldo(monto.negate());

        // Actualizar saldo
        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        productoRepository.guardar(cuenta);

        // Crear transacción sin Builder
        Transacciones transaccion = new Transacciones();
        transaccion.setTipo("RETIRO");
        transaccion.setMonto(monto);
        transaccion.setProductoOrigen(cuenta);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado("EXITOSA");

        Transacciones transaccionGuardada = transaccionRepository.guardar(transaccion);
        return transaccionMapper.toDto(transaccionGuardada);
    }

    @Override
    @Transactional
    public TransaccionesDTO realizarConsignacion(String numeroCuenta, BigDecimal monto) {
        // Validaciones
        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        Producto cuenta = productoRepository.buscarPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        // Validar cuenta
        if ("CANCELADA".equalsIgnoreCase(cuenta.getEstado())) {
            throw new IllegalStateException("No se puede consignar a una cuenta cancelada");
        }

        // Actualizar saldo
        cuenta.setSaldo(cuenta.getSaldo().add(monto));
        productoRepository.guardar(cuenta);

        // Crear transacción sin Builder
        Transacciones transaccion = new Transacciones();
        transaccion.setTipo("CONSIGNACION");
        transaccion.setMonto(monto);
        transaccion.setProductoDestino(cuenta);
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado("EXITOSA");

        Transacciones transaccionGuardada = transaccionRepository.guardar(transaccion);
        return transaccionMapper.toDto(transaccionGuardada);
    }
}