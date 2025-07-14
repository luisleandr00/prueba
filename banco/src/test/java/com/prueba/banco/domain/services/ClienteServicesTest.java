package com.prueba.banco.domain.services;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.ports.ClienteRepository;
import com.prueba.banco.domain.ports.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServicesTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ClienteServices clienteServices;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombres("Juan");
        cliente.setApellido("Perez");
        cliente.setFechaNacimiento(LocalDate.now().minusYears(20));
        cliente.setEmail("juan@example.com");
    }

    @Test
    void crearCliente_DatosValidos_CreaCliente() {
        when(clienteRepository.guardar(cliente)).thenReturn(cliente);

        Cliente resultado = clienteServices.crearCliente(cliente);

        assertNotNull(resultado);
        assertNotNull(resultado.getFechaCreacion());
        verify(clienteRepository, times(1)).guardar(cliente);
    }

    @Test
    void crearCliente_MenorDeEdad_LanzaExcepcion() {
        cliente.setFechaNacimiento(LocalDate.now().minusYears(17));

        assertThrows(IllegalArgumentException.class, () -> {
            clienteServices.crearCliente(cliente);
        });
    }

    @Test
    void crearCliente_EmailInvalido_LanzaExcepcion() {
        cliente.setEmail("emailinvalido");

        assertThrows(IllegalArgumentException.class, () -> {
            clienteServices.crearCliente(cliente);
        });
    }

    @Test
    void actualizarCliente_ClienteExiste_ActualizaCorrectamente() {

        cliente.setFechaNacimiento(LocalDate.now().minusYears(25));

        Cliente actualizado = new Cliente();
        actualizado.setNombres("Juan Actualizado");
        actualizado.setApellido("Perez");
        actualizado.setFechaNacimiento(LocalDate.now().minusYears(25)); // Fecha de nacimiento requerida
        actualizado.setEmail("juan.actualizado@example.com");
        actualizado.setTipoIdentificacion("CC");
        actualizado.setNumeroIdentificacion("123456789");

        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.guardar(cliente)).thenReturn(cliente);

        Optional<Cliente> resultado = clienteServices.actualizarCliente(1L, actualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Juan Actualizado", resultado.get().getNombres());
        assertNotNull(resultado.get().getFechaModificacion());
    }

    @Test
    void eliminarCliente_SinProductos_EliminaCorrectamente() {
        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.listarPorClienteId(1L)).thenReturn(List.of());

        clienteServices.eliminarCliente(1L);

        verify(clienteRepository, times(1)).eliminar(cliente);
    }


    @Test
    void eliminarCliente_ConProductos_LanzaExcepcion() {
        when(clienteRepository.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        CuentaAhorros cuenta = new CuentaAhorros();
        cuenta.setId(1L);
        cuenta.setNumeroCuenta("5312345678");
        cuenta.setClienteId(1L);

        when(productoRepository.listarPorClienteId(1L)).thenReturn(List.of(cuenta));

        assertThrows(IllegalStateException.class, () -> {
            clienteServices.eliminarCliente(1L);
        });
    }
}