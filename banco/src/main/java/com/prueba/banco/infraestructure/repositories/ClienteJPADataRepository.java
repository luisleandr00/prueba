package com.prueba.banco.infraestructure.repositories;

import com.prueba.banco.infraestructure.persistence.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface ClienteJPADataRepository extends JpaRepository<ClienteEntity, Long> {


    Optional<ClienteEntity> findByTipoIdentificacionAndNumeroIdentificacion(
            String tipoIdentificacion,
            String numeroIdentificacion
    );


}
