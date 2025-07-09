package com.prueba.banco.domain.ports;

import com.prueba.banco.domain.model.Transacciones;
import java.math.BigDecimal;

public interface TransaccionesService {
    Transacciones realizarTransferencia(String numeroOrigen, String numeroDestino, BigDecimal monto);
    Transacciones realizarRetiro(String numeroCuenta, BigDecimal monto);
    Transacciones realizarConsignacion(String numeroCuenta, BigDecimal monto);
}
