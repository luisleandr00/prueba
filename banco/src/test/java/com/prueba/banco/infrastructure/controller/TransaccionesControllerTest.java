package com.prueba.banco.infrastructure.controller;

import com.prueba.banco.application.dto.TransaccionesDTO;
import com.prueba.banco.application.mapper.TransaccionMapper;
import com.prueba.banco.domain.model.Transacciones;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TransaccionesControllerTest {

    @Mock
    private TransaccionesService transaccionesService;

    @Mock
    private TransaccionMapper transaccionMapper;

    @InjectMocks
    private TransaccionesController transaccionesController;

    @Test
    void realizarTransferencia_DeberiaFuncionar() {
        // 1. Preparamos los objetos reales (sin mock)
        Transacciones transaccionReal = new Transacciones();
        transaccionReal.setId(1L);
        transaccionReal.setTipo("TRANSFERENCIA");

        TransaccionesDTO dtoReal = new TransaccionesDTO();
        dtoReal.setId(1L);
        dtoReal.setTipo("TRANSFERENCIA");

        // 2. Configuramos los mocks con doReturn (en lugar de when-thenReturn)
        doReturn(transaccionReal).when(transaccionesService)
                .realizarTransferencia(anyString(), anyString(), any(BigDecimal.class));

        doReturn(dtoReal).when(transaccionMapper).toDto(transaccionReal);

        // 3. Ejecutamos
        ResponseEntity<TransaccionesDTO> respuesta = transaccionesController
                .realizarTransferencia("5312345678", "5398765432", new BigDecimal("1000"));

        // 4. Verificamos
        assertEquals(HttpStatus.CREATED, respuesta.getStatusCode());
        assertEquals(1L, respuesta.getBody().getId());
    }
}