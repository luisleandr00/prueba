package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.model.CuentaCorriente;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ClienteRepository;
import com.prueba.banco.domain.ports.ProductoRepository;
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
class ProductoServicesTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ProductoServices productoServices;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombres("Juan");
        cliente.setApellido("Perez");

    }

    @Test
    void crearCuentaAhorros_ClienteExiste_CuentaCreadaCorrectamente() {

        when(clienteRepository.existePorId(1L)).thenReturn(true);

        when(productoRepository.guardar(any(Producto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado = productoServices.crearCuentaAhorros(1L, true);

        assertNotNull(resultado);
        assertTrue(resultado instanceof CuentaAhorros);
        assertEquals("ACTIVA", resultado.getEstado());
        assertEquals(BigDecimal.ZERO, resultado.getSaldo());
        assertTrue(resultado.getNumeroCuenta().startsWith("53"));
        verify(productoRepository, times(1)).guardar(any(Producto.class));
    }



    @Test
    void crearCuentaCorriente_ClienteExiste_CuentaCreadaCorrectamente() {

        when(clienteRepository.existePorId(1L)).thenReturn(true);

        when(productoRepository.guardar(any(Producto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Producto resultado = productoServices.crearCuentaCorriente(1L, false);

        assertNotNull(resultado);
        assertTrue(resultado instanceof CuentaCorriente);
        assertEquals("ACTIVA", resultado.getEstado());
        assertEquals(BigDecimal.ZERO, resultado.getSaldo());
        assertTrue(resultado.getNumeroCuenta().startsWith("33"));
        verify(productoRepository, times(1)).guardar(any(Producto.class));
    }


    @Test
    void cambiarEstadoCuenta_CuentaConSaldoCero_CambiaACancelada() {
        Producto cuenta = new CuentaAhorros();
        cuenta.setNumeroCuenta("5312345678");
        cuenta.setSaldo(BigDecimal.ZERO);
        cuenta.setEstado("ACTIVA");

        when(productoRepository.buscarPorNumeroCuenta("5312345678")).thenReturn(Optional.of(cuenta));
        when(productoRepository.guardar(any(Producto.class))).thenReturn(cuenta);

        Producto resultado = productoServices.cambiarEstadoCuenta("5312345678", "CANCELADA");

        assertEquals("CANCELADA", resultado.getEstado());
    }

    @Test
    void cambiarEstadoCuenta_CuentaConSaldoNoCero_LanzaExcepcion() {
        Producto cuenta = new CuentaAhorros();
        cuenta.setNumeroCuenta("5312345678");
        cuenta.setSaldo(new BigDecimal("1000"));
        cuenta.setEstado("ACTIVA");

        when(productoRepository.buscarPorNumeroCuenta("5312345678")).thenReturn(Optional.of(cuenta));

        assertThrows(IllegalStateException.class, () -> {
            productoServices.cambiarEstadoCuenta("5312345678", "CANCELADA");
        });
    }
}