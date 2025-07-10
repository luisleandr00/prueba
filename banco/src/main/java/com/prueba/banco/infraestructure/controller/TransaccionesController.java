package com.prueba.banco.infraestructure.controller;


import com.prueba.banco.application.dto.TransaccionesDTO;
import com.prueba.banco.application.mapper.TransaccionMapper;
import com.prueba.banco.domain.ports.TransaccionesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transacciones")
@RequiredArgsConstructor
public class TransaccionesController {

    private final TransaccionesService transaccionesService;
    private final TransaccionMapper transaccionMapper;

    @PostMapping("/transferencia")
    public ResponseEntity<TransaccionesDTO> realizarTransferencia(
            @RequestParam String numeroOrigen,
            @RequestParam String numeroDestino,
            @RequestParam BigDecimal monto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transaccionMapper.toDto(
                        transaccionesService.realizarTransferencia(numeroOrigen, numeroDestino, monto)
                ));
    }

    @PostMapping("/retiro")
    public ResponseEntity<TransaccionesDTO> realizarRetiro(
            @RequestParam String numeroCuenta,
            @RequestParam BigDecimal monto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transaccionMapper.toDto(
                        transaccionesService.realizarRetiro(numeroCuenta, monto)
                ));
    }

    @PostMapping("/consignacion")
    public ResponseEntity<TransaccionesDTO> realizarConsignacion(
            @RequestParam String numeroCuenta,
            @RequestParam BigDecimal monto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transaccionMapper.toDto(
                        transaccionesService.realizarConsignacion(numeroCuenta, monto)
                ));
    }
}