package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ClienteRepository;
import com.prueba.banco.domain.ports.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServicesTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ClienteServices clienteServices;

    private Cliente clienteValido;
    private Cliente clienteMenorEdad;

    @BeforeEach
    void setUp() {
        clienteValido = new Cliente();
        clienteValido.setId(1L);
        clienteValido.setNombres("Juan");
        clienteValido.setApellido("Perez");
        clienteValido.setFechaNacimiento(LocalDate.now().minusYears(20));
        clienteValido.setFechaCreacion(LocalDateTime.now());

        clienteMenorEdad = new Cliente();
        clienteMenorEdad.setId(2L);
        clienteMenorEdad.setNombres("Ana");
        clienteMenorEdad.setApellido("Gomez");
        clienteMenorEdad.setFechaNacimiento(LocalDate.now().minusYears(17));
    }

    @Test
    void crearCliente_ClienteValido_RetornaCliente() {
        when(clienteRepository.guardar(any(Cliente.class))).thenReturn(clienteValido);

        Cliente resultado = clienteServices.crearCliente(clienteValido);

        assertNotNull(resultado);
        assertEquals(clienteValido.getId(), resultado.getId());
        verify(clienteRepository, times(1)).guardar(clienteValido);
    }

    @Test
    void crearCliente_ClienteMenorEdad_LanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            clienteServices.crearCliente(clienteMenorEdad);
        });
    }

    @Test
    void actualizarCliente_ClienteExistente_ActualizaCorrectamente() {
        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(clienteValido));
        when(clienteRepository.guardar(any(Cliente.class))).thenReturn(clienteValido);

        Optional<Cliente> resultado = clienteServices.actualizarCliente(1L, clienteValido);

        assertTrue(resultado.isPresent());
        verify(clienteRepository, times(1)).guardar(clienteValido);
    }

    @Test
    void actualizarCliente_ClienteNoExiste_RetornaVacio() {
        when(clienteRepository.buscarPorId(99L)).thenReturn(Optional.empty());

        Optional<Cliente> resultado = clienteServices.actualizarCliente(99L, clienteValido);

        assertFalse(resultado.isPresent());
    }

    @Test
    void eliminarCliente_SinProductos_EliminaCorrectamente() {
        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(clienteValido));
        when(productoRepository.listarPorCliente(clienteValido)).thenReturn(Collections.emptyList());

        clienteServices.eliminarCliente(1L);

        verify(clienteRepository, times(1)).eliminar(clienteValido);
    }

    @Test
    void eliminarCliente_ConProductosVinculados_LanzaExcepcion() {
        Producto cuenta = new CuentaAhorros();
        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(clienteValido));
        when(productoRepository.listarPorCliente(clienteValido)).thenReturn(List.of(cuenta));

        assertThrows(IllegalStateException.class, () -> {
            clienteServices.eliminarCliente(1L);
        });
    }
}