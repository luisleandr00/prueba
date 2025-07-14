package com.prueba.banco.infraestructure.repositories;


import com.prueba.banco.infraestructure.persistence.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoJPADataRepository extends JpaRepository<ProductoEntity, Long> {
    Optional<ProductoEntity> findByNumeroCuenta(String numeroCuenta);

    List<ProductoEntity> findByClienteId(Long clienteId);

    List<ProductoEntity> findByTipoCuenta(String tipoCuenta);

    List<ProductoEntity> findByEstado(String estado);

    List<ProductoEntity> findBySaldoGreaterThan(BigDecimal saldo);

    List<ProductoEntity> findBySaldoLessThan(BigDecimal saldo);

    List<ProductoEntity> findByExentaGMF(boolean exentaGMF);

    boolean existsByNumeroCuenta(String numeroCuenta);

    List<ProductoEntity> findByFechaCreacionBetween(LocalDateTime desde, LocalDateTime hasta);

    List<ProductoEntity> findByClienteIdAndTipoCuenta(Long clienteId, String tipoCuenta);
}
