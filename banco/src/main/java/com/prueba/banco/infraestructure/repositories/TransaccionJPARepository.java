package com.prueba.banco.infraestructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.prueba.banco.infraestructure.persistence.TransaccionEntity;
@Repository
public interface TransaccionJPARepository extends JpaRepository<TransaccionEntity, Long> {
    List<TransaccionEntity> findByProductoOrigenIdOrProductoDestinoId(Long productoOrigenId, Long productoDestinoId);
}
