package com.prueba.banco.infrastructure.controller;

import com.prueba.banco.application.dto.ClienteDTO;
import com.prueba.banco.application.mapper.ClienteMapper;
import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.services.ClienteServices;
import com.prueba.banco.infraestructure.controller.ClienteController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteServices clienteService;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombres("Juan");
        cliente.setApellido("Perez");
        cliente.setFechaNacimiento(LocalDate.now().minusYears(20));
        cliente.setEmail("juan@example.com");

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNombres("Juan");
        clienteDTO.setApellido("Perez");
        clienteDTO.setFechaNacimiento(LocalDate.now().minusYears(20));
        clienteDTO.setEmail("juan@example.com");
    }

    @Test
    void crearCliente_DatosValidos_RetornaCreated() {
        when(clienteMapper.toDomain(clienteDTO)).thenReturn(cliente);
        when(clienteService.crearCliente(cliente)).thenReturn(cliente);
        when(clienteMapper.toDto(cliente)).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.crearCliente(clienteDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Juan", response.getBody().getNombres());
    }

    @Test
    void crearCliente_MenorDeEdad_RetornaBadRequest() {
        cliente.setFechaNacimiento(LocalDate.now().minusYears(17));
        clienteDTO.setFechaNacimiento(LocalDate.now().minusYears(17));

        when(clienteMapper.toDomain(clienteDTO)).thenReturn(cliente);
        when(clienteService.crearCliente(cliente)).thenThrow(new IllegalArgumentException("El cliente debe ser mayor de edad"));

        ResponseEntity<ClienteDTO> response = clienteController.crearCliente(clienteDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void buscarPorId_ClienteExiste_RetornaOk() {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));
        when(clienteMapper.toDto(cliente)).thenReturn(clienteDTO);

        ResponseEntity<ClienteDTO> response = clienteController.buscarPorId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void buscarPorId_ClienteNoExiste_RetornaNotFound() {
        when(clienteService.buscarPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<ClienteDTO> response = clienteController.buscarPorId(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void listarTodos_ClientesExisten_RetornaLista() {
        when(clienteService.listarTodos()).thenReturn(List.of(cliente));
        when(clienteMapper.toDto(cliente)).thenReturn(clienteDTO);

        ResponseEntity<List<ClienteDTO>> response = clienteController.listarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }
}
