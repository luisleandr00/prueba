package com.prueba.banco.infraestructure.repositories;

import com.prueba.banco.domain.model.Transacciones;
import com.prueba.banco.domain.ports.TransaccionRepository;
import com.prueba.banco.infraestructure.mappers.TransaccionEntityMapper;
import com.prueba.banco.infraestructure.persistence.TransaccionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TransaccionRepositoryJPA implements TransaccionRepository {

    private final TransaccionJPARepository jpaRepository;
    private final TransaccionEntityMapper mapper;

    public TransaccionRepositoryJPA(TransaccionJPARepository jpaRepository,
                                     TransaccionEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Transacciones guardar(Transacciones transaccion) {
        TransaccionEntity entity = mapper.toEntity(transaccion);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Transacciones> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Transacciones> listarPorProductoId(Long productoId) {
        return jpaRepository.findByProductoOrigenIdOrProductoDestinoId(productoId, productoId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
