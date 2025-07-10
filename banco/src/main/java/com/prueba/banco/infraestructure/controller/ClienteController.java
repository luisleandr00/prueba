package com.prueba.banco.infraestructure.controller;


import com.prueba.banco.application.dto.ClienteDTO;
import com.prueba.banco.application.mapper.ClienteMapper;
import com.prueba.banco.domain.ports.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        return new ResponseEntity<>(
                clienteMapper.toDto(clienteService.crearCliente(clienteMapper.toDomain(clienteDTO))),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(
            @PathVariable Long id,
            @RequestBody ClienteDTO clienteDTO
    ) {
        return clienteService.actualizarCliente(id, clienteMapper.toDomain(clienteDTO))
                .map(cliente -> ResponseEntity.ok(clienteMapper.toDto(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(cliente -> ResponseEntity.ok(clienteMapper.toDto(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(
                clienteService.listarTodos().stream()
                        .map(clienteMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}
