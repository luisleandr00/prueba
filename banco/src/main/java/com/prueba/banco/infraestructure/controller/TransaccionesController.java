package com.prueba.banco.infraestructure.controller;

import com.prueba.banco.application.dto.TransaccionesDTO;
import com.prueba.banco.domain.ports.TransaccionesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionesController {

    private final TransaccionesService transaccionesService;


    public TransaccionesController(TransaccionesService transaccionesService) {
        this.transaccionesService = transaccionesService;
    }

    @PostMapping("/transferencia")
    public ResponseEntity<TransaccionesDTO> realizarTransferencia(
            @RequestParam String numeroOrigen,
            @RequestParam String numeroDestino,
            @RequestParam BigDecimal monto) {


        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TransaccionesDTO transaccion = transaccionesService.realizarTransferencia(
                    numeroOrigen,
                    numeroDestino,
                    monto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/retiro")
    public ResponseEntity<TransaccionesDTO> realizarRetiro(
            @RequestParam String numeroCuenta,
            @RequestParam BigDecimal monto) {


        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TransaccionesDTO transaccion = transaccionesService.realizarRetiro(
                    numeroCuenta,
                    monto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/consignacion")
    public ResponseEntity<TransaccionesDTO> realizarConsignacion(
            @RequestParam String numeroCuenta,
            @RequestParam BigDecimal monto) {


        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TransaccionesDTO transaccion = transaccionesService.realizarConsignacion(
                    numeroCuenta,
                    monto);
            return ResponseEntity.status(HttpStatus.CREATED).body(transaccion);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}