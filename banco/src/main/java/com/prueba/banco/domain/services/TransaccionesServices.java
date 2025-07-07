package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.model.Transacciones;
import com.prueba.banco.domain.ports.ProductoRepository;
import com.prueba.banco.domain.ports.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransaccionesServices {
    private final TransaccionRepository transaccionRepository;
    private final ProductoRepository productoRepository;

    public Transacciones realizarTransferencia(String numeroOrigen, String numeroDestino, BigDecimal monto) {
        Producto origen = productoRepository.buscarPorNumeroCuenta(numeroOrigen)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta origen no existe"));
        Producto destino = productoRepository.buscarPorNumeroCuenta(numeroDestino)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta destino no existe"));


        BigDecimal montoNegativo = monto.multiply(new BigDecimal("-1"));
        origen.validarSaldo(montoNegativo);
        destino.validarSaldo(monto);

        origen.setSaldo(origen.getSaldo().subtract(monto));
        destino.setSaldo(destino.getSaldo().add(monto));

        productoRepository.guardar(origen);
        productoRepository.guardar(destino);

        Transacciones transacciones = Transacciones.builder()
                .tipo("TRANSFERENCIA")
                .monto(monto)
                .productoOrigen(origen)
                .productoDestino(destino)
                .fecha(LocalDateTime.now())
                .estado("EXITOSA")
                .build();

        return transaccionRepository.guardar(transacciones);
    }

    public Transacciones realizarRetiro(String numeroCuenta, BigDecimal monto) {
        Producto cuenta = productoRepository.buscarPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));


        cuenta.validarSaldo(monto.negate());


        cuenta.setSaldo(cuenta.getSaldo().subtract(monto));

        productoRepository.guardar(cuenta);


        return transaccionRepository.guardar(
                Transacciones.builder()
                        .tipo("RETIRO")
                        .monto(monto)
                        .productoOrigen(cuenta)
                        .fecha(LocalDateTime.now())
                        .estado("EXITOSA")
                        .build()
        );
    }
    public Transacciones realizarConsignacion(String numeroCuenta, BigDecimal monto) {
        Producto cuenta = productoRepository.buscarPorNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

        cuenta.validarSaldo(monto); // Valida estado de la cuenta
        cuenta.setSaldo(cuenta.getSaldo().add(monto));

        productoRepository.guardar(cuenta);

        return transaccionRepository.guardar(
                Transacciones.builder()
                        .tipo("CONSIGNACION")
                        .monto(monto)
                        .productoDestino(cuenta)
                        .fecha(LocalDateTime.now())
                        .estado("EXITOSA")
                        .build()
        );
    }
}