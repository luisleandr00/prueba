package com.prueba.banco.domain.ports;

import com.prueba.banco.application.dto.TransaccionesDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public interface TransaccionesService {
    TransaccionesDTO realizarTransferencia(String numeroOrigen, String numeroDestino, BigDecimal monto);
    TransaccionesDTO realizarRetiro(String numeroCuenta, BigDecimal monto);
    TransaccionesDTO realizarConsignacion(String numeroCuenta, BigDecimal monto);
}
