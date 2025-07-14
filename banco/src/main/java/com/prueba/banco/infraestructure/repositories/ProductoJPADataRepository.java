package com.prueba.banco.infraestructure.repositories;


import com.prueba.banco.infraestructure.persistence.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoJPADataRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByNumeroCuenta(String numeroCuenta);

    List<ProductoEntity> findByClienteId(Long clienteId);

}
