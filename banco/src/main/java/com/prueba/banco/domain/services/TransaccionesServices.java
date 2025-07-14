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
public class TransaccionesServices implements TransaccionesService {

    private final TransaccionRepository transaccionRepository;
    private final ProductoRepository productoRepository;
    private final TransaccionMapper transaccionMapper;

    public TransaccionesServices(TransaccionRepository transaccionRepository,
                                 ProductoRepository productoRepository,
                                 TransaccionMapper transaccionMapper) {
        this.transaccionRepository = transaccionRepository;
        this.productoRepository = productoRepository;
        this.transaccionMapper = transaccionMapper;
    }

    @Override
    @Transactional
    public TransaccionesDTO realizarTransferencia(String numeroOrigen, String numeroDestino, BigDecimal monto) {

        if (numeroOrigen.equals(numeroDestino)) {
            throw new IllegalArgumentException("No se puede transferir a la misma cuenta");
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }


        Producto origen = productoRepository.buscarPorNumeroCuenta(numeroOrigen)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no encontrada"));

        Producto destino = productoRepository.buscarPorNumeroCuenta(numeroDestino)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no encontrada"));


        origen.validarSaldo(monto.negate());
        destino.validarSaldo(monto);


        origen.setSaldo(origen.getSaldo().subtract(monto));
        destino.setSaldo(destino.getSaldo().add(monto));

        productoRepository.guardar(origen);
        productoRepository.guardar(destino);


        Transacciones transaccion = new Transacciones();
        transaccion.setTipo("TRANSFERENCIA");
        transaccion.setMonto(monto);
        transaccion.setProductoOrigenId(origen.getId());
        transaccion.setProductoDestinoId(destino.getId());
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado("EXITOSA");

        Transacciones transaccionGuardada = transaccionRepository.guardar(transaccion);
        return transaccionMapper.toDto(transaccionGuardada);
    }

    @Override
    @Transactional
    public TransaccionesDTO realizarRetiro(String numeroCuenta, BigDecimal monto) {

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        Producto cuenta = productoRepository.buscarPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));


        cuenta.validarSaldo(monto.negate());


        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        productoRepository.guardar(cuenta);


        Transacciones transaccion = new Transacciones();
        transaccion.setTipo("RETIRO");
        transaccion.setMonto(monto);
        transaccion.setProductoOrigenId(cuenta.getId());
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado("EXITOSA");

        Transacciones transaccionGuardada = transaccionRepository.guardar(transaccion);
        return transaccionMapper.toDto(transaccionGuardada);
    }

    @Override
    @Transactional
    public TransaccionesDTO realizarConsignacion(String numeroCuenta, BigDecimal monto) {

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }

        Producto cuenta = productoRepository.buscarPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));


        if ("CANCELADA".equalsIgnoreCase(cuenta.getEstado())) {
            throw new IllegalStateException("No se puede consignar a una cuenta cancelada");
        }


        cuenta.setSaldo(cuenta.getSaldo().add(monto));
        productoRepository.guardar(cuenta);


        Transacciones transaccion = new Transacciones();
        transaccion.setTipo("CONSIGNACION");
        transaccion.setMonto(monto);
        transaccion.setProductoDestinoId(cuenta.getId());
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setEstado("EXITOSA");

        Transacciones transaccionGuardada = transaccionRepository.guardar(transaccion);
        return transaccionMapper.toDto(transaccionGuardada);
    }
}