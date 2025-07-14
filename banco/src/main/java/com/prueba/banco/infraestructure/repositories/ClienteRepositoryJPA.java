package com.prueba.banco.infraestructure.repositories;


import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.ports.ClienteRepository;
import com.prueba.banco.infraestructure.persistence.ClienteEntity;
import com.prueba.banco.infraestructure.mappers.ClienteEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryJPA implements ClienteRepository {

    private final ClienteJPADataRepository jpaRepository;
    private final ClienteEntityMapper mapper;

    public ClienteRepositoryJPA(ClienteJPADataRepository jpaRepository,
                                ClienteEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        return mapper.toDomain(jpaRepository.save(entity));
    }
    @Override
    public boolean existePorId(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Cliente> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void eliminar(Cliente cliente) {
        jpaRepository.delete(mapper.toEntity(cliente));
    }

    @Override
    public Optional<Cliente> buscarPorIdentificacion(String tipo, String numero) {
        return jpaRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipo, numero)
                .map(mapper::toDomain);
    }
}