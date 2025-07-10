package com.prueba.banco.infraestructure.controller;

import com.prueba.banco.application.dto.ProductoDTO;
import com.prueba.banco.application.mapper.ProductoMapper;
import com.prueba.banco.domain.ports.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper productoMapper;

    @PostMapping("/ahorros")
    public ResponseEntity<ProductoDTO> crearCuentaAhorros(
            @RequestParam Long clienteId,
            @RequestParam boolean exentaGMF
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoMapper.toDto(
                        productoService.crearCuentaAhorros(clienteId, exentaGMF)
                ));
    }

    @PostMapping("/corriente")
    public ResponseEntity<ProductoDTO> crearCuentaCorriente(
            @RequestParam Long clienteId,
            @RequestParam boolean exentaGMF
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productoMapper.toDto(
                        productoService.crearCuentaCorriente(clienteId, exentaGMF)
                ));
    }

    @PutMapping("/{numeroCuenta}/estado")
    public ResponseEntity<ProductoDTO> cambiarEstadoCuenta(
            @PathVariable String numeroCuenta,
            @RequestParam String nuevoEstado
    ) {
        return ResponseEntity.ok(
                productoMapper.toDto(
                        productoService.cambiarEstadoCuenta(numeroCuenta, nuevoEstado)
                ));
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<ProductoDTO> buscarPorNumeroCuenta(@PathVariable String numeroCuenta) {
        return productoService.buscarPorNumeroCuenta(numeroCuenta)
                .map(producto -> ResponseEntity.ok(productoMapper.toDto(producto)))
                .orElse(ResponseEntity.notFound().build());
    }
}