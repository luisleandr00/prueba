package com.prueba.banco.infrastructure.controller;

import com.prueba.banco.application.dto.TransaccionesDTO;
import com.prueba.banco.domain.ports.TransaccionesService;
import com.prueba.banco.infraestructure.controller.TransaccionesController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionesControllerTest {

    @Mock
    private TransaccionesService transaccionesService;

    @InjectMocks
    private TransaccionesController transaccionesController;

    @Test
    void realizarTransferencia_DatosValidos_RetornaCreated() {
        TransaccionesDTO dto = new TransaccionesDTO();
        dto.setId(1L);
        dto.setTipo("TRANSFERENCIA");

        when(transaccionesService.realizarTransferencia(anyString(), anyString(), any(BigDecimal.class)))
                .thenReturn(dto);

        ResponseEntity<TransaccionesDTO> response = transaccionesController
                .realizarTransferencia("5312345678", "5398765432", new BigDecimal("1000"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TRANSFERENCIA", response.getBody().getTipo());
    }

    @Test
    void realizarTransferencia_MontoInvalido_RetornaBadRequest() {
        ResponseEntity<TransaccionesDTO> response = transaccionesController
                .realizarTransferencia("5312345678", "5398765432", new BigDecimal("-100"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void realizarTransferencia_MismoNumeroCuenta_RetornaBadRequest() {
        when(transaccionesService.realizarTransferencia(anyString(), anyString(), any(BigDecimal.class)))
                .thenThrow(new IllegalArgumentException("No se puede transferir a la misma cuenta"));

        ResponseEntity<TransaccionesDTO> response = transaccionesController
                .realizarTransferencia("5312345678", "5312345678", new BigDecimal("1000"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void realizarRetiro_DatosValidos_RetornaCreated() {
        TransaccionesDTO dto = new TransaccionesDTO();
        dto.setId(2L);
        dto.setTipo("RETIRO");

        when(transaccionesService.realizarRetiro(anyString(), any(BigDecimal.class)))
                .thenReturn(dto);

        ResponseEntity<TransaccionesDTO> response = transaccionesController
                .realizarRetiro("5312345678", new BigDecimal("500"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("RETIRO", response.getBody().getTipo());
    }

    @Test
    void realizarConsignacion_DatosValidos_RetornaCreated() {
        TransaccionesDTO dto = new TransaccionesDTO();
        dto.setId(3L);
        dto.setTipo("CONSIGNACION");

        when(transaccionesService.realizarConsignacion(anyString(), any(BigDecimal.class)))
                .thenReturn(dto);

        ResponseEntity<TransaccionesDTO> response = transaccionesController
                .realizarConsignacion("5312345678", new BigDecimal("1500"));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("CONSIGNACION", response.getBody().getTipo());
    }
}