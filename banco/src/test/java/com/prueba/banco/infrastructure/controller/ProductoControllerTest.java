package com.prueba.banco.infrastructure.controller;

import com.prueba.banco.application.dto.ProductoDTO;
import com.prueba.banco.application.mapper.ProductoMapper;
import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ProductoService;
import com.prueba.banco.infraestructure.controller.ProductoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoController productoController;

    private Producto producto;
    private ProductoDTO productoDTO;

    @BeforeEach
    void setUp() {
        producto = new CuentaAhorros();
        producto.setId(1L);
        producto.setTipoCuenta("AHORROS");
        producto.setNumeroCuenta("5312345678");
        producto.setSaldo(BigDecimal.ZERO);
        producto.setFechaCreacion(LocalDateTime.now());

        productoDTO = new ProductoDTO();
        productoDTO.setId(1L);
        productoDTO.setTipoCuenta("AHORROS");
        productoDTO.setNumeroCuenta("5312345678");
        productoDTO.setSaldo(BigDecimal.ZERO);
        productoDTO.setFechaCreacion(LocalDateTime.now());
    }

    @Test
    void crearCuentaAhorros_DatosValidos_RetornaCreated() {
        when(productoService.crearCuentaAhorros(1L, false)).thenReturn(producto);
        when(productoMapper.toDto(producto)).thenReturn(productoDTO);

        ResponseEntity<ProductoDTO> response = productoController.crearCuentaAhorros(1L, false);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("AHORROS", response.getBody().getTipoCuenta());
    }

    @Test
    void buscarPorNumeroCuenta_CuentaExiste_RetornaOk() {
        when(productoService.buscarPorNumeroCuenta("5312345678")).thenReturn(Optional.of(producto));
        when(productoMapper.toDto(producto)).thenReturn(productoDTO);

        ResponseEntity<ProductoDTO> response = productoController.buscarPorNumeroCuenta("5312345678");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void buscarPorNumeroCuenta_CuentaNoExiste_RetornaNotFound() {
        when(productoService.buscarPorNumeroCuenta("9999999999")).thenReturn(Optional.empty());

        ResponseEntity<ProductoDTO> response = productoController.buscarPorNumeroCuenta("9999999999");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void cambiarEstadoCuenta_DatosValidos_RetornaOk() {
        when(productoService.cambiarEstadoCuenta("5312345678", "INACTIVA")).thenReturn(producto);
        when(productoMapper.toDto(producto)).thenReturn(productoDTO);

        ResponseEntity<ProductoDTO> response = productoController.cambiarEstadoCuenta("5312345678", "INACTIVA");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}