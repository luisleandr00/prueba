package com.prueba.banco.domain.services;

import com.prueba.banco.application.dto.TransaccionesDTO;
import com.prueba.banco.application.mapper.TransaccionMapper;
import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.model.Transacciones;
import com.prueba.banco.domain.ports.ProductoRepository;
import com.prueba.banco.domain.ports.TransaccionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionesServicesTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private TransaccionMapper transaccionMapper;

    @InjectMocks
    private TransaccionesServices transaccionesServices;

    private Producto cuentaOrigen;
    private Producto cuentaDestino;
    private Transacciones transaccion;
    private TransaccionesDTO transaccionDTO;

    @BeforeEach
    void setUp() {
        cuentaOrigen = new CuentaAhorros();
        cuentaOrigen.setId(1L);
        cuentaOrigen.setNumeroCuenta("5312345678");
        cuentaOrigen.setSaldo(new BigDecimal("2000"));
        cuentaOrigen.setEstado("ACTIVA");

        cuentaDestino = new CuentaAhorros();
        cuentaDestino.setId(2L);
        cuentaDestino.setNumeroCuenta("5398765432");
        cuentaDestino.setSaldo(new BigDecimal("1000"));
        cuentaDestino.setEstado("ACTIVA");

        transaccion = new Transacciones();
        transaccion.setId(1L);
        transaccion.setTipo("TRANSFERENCIA");
        transaccion.setMonto(new BigDecimal("500"));
        transaccion.setProductoOrigenId(1L);
        transaccion.setProductoDestinoId(2L);
        transaccion.setEstado("EXITOSA");

        transaccionDTO = new TransaccionesDTO();
        transaccionDTO.setId(1L);
        transaccionDTO.setTipo("TRANSFERENCIA");
    }

    @Test
    void realizarTransferencia_DatosValidos_TransaccionExitosa() {
        when(productoRepository.buscarPorNumeroCuenta("5312345678")).thenReturn(Optional.of(cuentaOrigen));
        when(productoRepository.buscarPorNumeroCuenta("5398765432")).thenReturn(Optional.of(cuentaDestino));
        when(productoRepository.guardar(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transaccionRepository.guardar(any(Transacciones.class))).thenReturn(transaccion);
        when(transaccionMapper.toDto(transaccion)).thenReturn(transaccionDTO);

        TransaccionesDTO resultado = transaccionesServices.realizarTransferencia(
                "5312345678", "5398765432", new BigDecimal("500"));

        assertNotNull(resultado);
        assertEquals("TRANSFERENCIA", resultado.getTipo());
        verify(productoRepository, times(2)).guardar(any(Producto.class));
    }

    @Test
    void realizarTransferencia_MismoNumeroCuenta_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionesServices.realizarTransferencia(
                    "5312345678", "5312345678", new BigDecimal("500"));
        });
    }

    @Test
    void realizarTransferencia_MontoNegativo_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            transaccionesServices.realizarTransferencia(
                    "5312345678", "5398765432", new BigDecimal("-500"));
        });
    }

    @Test
    void realizarRetiro_DatosValidos_TransaccionExitosa() {
        when(productoRepository.buscarPorNumeroCuenta("5312345678")).thenReturn(Optional.of(cuentaOrigen));
        when(productoRepository.guardar(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transaccionRepository.guardar(any(Transacciones.class))).thenReturn(transaccion);
        when(transaccionMapper.toDto(transaccion)).thenReturn(transaccionDTO);

        TransaccionesDTO resultado = transaccionesServices.realizarRetiro(
                "5312345678", new BigDecimal("500"));

        assertNotNull(resultado);
        assertEquals("TRANSFERENCIA", resultado.getTipo());
    }

    @Test
    void realizarConsignacion_DatosValidos_TransaccionExitosa() {
        when(productoRepository.buscarPorNumeroCuenta("5398765432")).thenReturn(Optional.of(cuentaDestino));
        when(productoRepository.guardar(any(Producto.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transaccionRepository.guardar(any(Transacciones.class))).thenReturn(transaccion);
        when(transaccionMapper.toDto(transaccion)).thenReturn(transaccionDTO);

        TransaccionesDTO resultado = transaccionesServices.realizarConsignacion(
                "5398765432", new BigDecimal("500"));

        assertNotNull(resultado);
        assertEquals("TRANSFERENCIA", resultado.getTipo());
    }

    @Test
    void realizarConsignacion_CuentaCancelada_LanzaExcepcion() {
        cuentaDestino.setEstado("CANCELADA");
        when(productoRepository.buscarPorNumeroCuenta("5398765432")).thenReturn(Optional.of(cuentaDestino));

        assertThrows(IllegalStateException.class, () -> {
            transaccionesServices.realizarConsignacion("5398765432", new BigDecimal("500"));
        });
    }
}
