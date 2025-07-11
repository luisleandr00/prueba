package com.prueba.banco.infraestructure.controller;

import com.prueba.banco.application.dto.ProductoDTO;
import com.prueba.banco.application.mapper.ProductoMapper;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper productoMapper;

    public ProductoController(ProductoService productoService, ProductoMapper productoMapper) {
        this.productoService = productoService;
        this.productoMapper = productoMapper;
    }

    @PostMapping("/ahorros")
    public ResponseEntity<ProductoDTO> crearCuentaAhorros(
            @RequestParam Long clienteId,
            @RequestParam boolean exentaGMF) {
        try {
            Producto producto = productoService.crearCuentaAhorros(clienteId, exentaGMF);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productoMapper.toDto(producto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/corriente")
    public ResponseEntity<ProductoDTO> crearCuentaCorriente(
            @RequestParam Long clienteId,
            @RequestParam boolean exentaGMF) {
        try {
            Producto producto = productoService.crearCuentaCorriente(clienteId, exentaGMF);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productoMapper.toDto(producto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{numeroCuenta}/estado")
    public ResponseEntity<ProductoDTO> cambiarEstadoCuenta(
            @PathVariable String numeroCuenta,
            @RequestParam String nuevoEstado) {
        try {
            Producto producto = productoService.cambiarEstadoCuenta(numeroCuenta, nuevoEstado);
            return ResponseEntity.ok(productoMapper.toDto(producto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<ProductoDTO> buscarPorNumeroCuenta(@PathVariable String numeroCuenta) {
        try {
            return productoService.buscarPorNumeroCuenta(numeroCuenta)
                    .map(producto -> ResponseEntity.ok(productoMapper.toDto(producto)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}