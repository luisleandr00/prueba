package com.prueba.banco.infraestructure.controller;

import com.prueba.banco.application.dto.ClienteDTO;
import com.prueba.banco.application.mapper.ClienteMapper;
import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.services.ClienteServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteServices clienteService;
    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteServices clienteService,
                             ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteMapper.toDomain(clienteDTO);
            Cliente clienteCreado = clienteService.crearCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(clienteMapper.toDto(clienteCreado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(cliente -> ResponseEntity.ok(clienteMapper.toDto(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = clienteService.listarTodos().stream()
                .map(clienteMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientes);
    }
}