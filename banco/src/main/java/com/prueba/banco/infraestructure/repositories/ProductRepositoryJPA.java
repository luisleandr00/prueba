package com.prueba.banco.infraestructure.repositories;


import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ProductoRepository;
import com.prueba.banco.infraestructure.mappers.ProductoEntityMapper;
import com.prueba.banco.infraestructure.persistence.ProductoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryJPA implements ProductoRepository {

    private final ProductoJPADataRepository jpaRepository;
    private final ProductoEntityMapper mapper;

    public ProductRepositoryJPA(ProductoJPADataRepository jpaRepository,
                                  ProductoEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Producto guardar(Producto producto) {
        ProductoEntity entity = mapper.toEntity(producto);
        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Producto> buscarPorNumeroCuenta(String numeroCuenta) {
        return jpaRepository.findByNumeroCuenta(numeroCuenta)
                .map(mapper::toDomain);
    }

    @Override
    public List<Producto> listarPorClienteId(Long clienteId) {
        return jpaRepository.findByClienteId(clienteId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void eliminar(Producto producto) {
        jpaRepository.delete(mapper.toEntity(producto));
    }
}