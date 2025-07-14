package com.prueba.banco.infraestructure.repositories;

import com.prueba.banco.infraestructure.persistence.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteJPADataRepository extends JpaRepository<ClienteEntity, Long> {


    Optional<ClienteEntity> findByTipoIdentificacionAndNumeroIdentificacion(
            String tipoIdentificacion,
            String numeroIdentificacion
    );


    boolean existsByTipoIdentificacionAndNumeroIdentificacion(
            String tipoIdentificacion,
            String numeroIdentificacion
    );


    List<ClienteEntity> findByNombresContainingIgnoreCase(String nombresFragment);


    List<ClienteEntity> findByApellidoIgnoreCase(String apellido);


    List<ClienteEntity> findByFechaNacimientoBetween(LocalDate desde, LocalDate hasta);
}
