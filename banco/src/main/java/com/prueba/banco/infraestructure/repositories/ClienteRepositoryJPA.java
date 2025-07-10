package com.prueba.banco.infraestructure.repositories;

import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.ports.ClienteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepositoryJPA extends JpaRepository<Cliente, Long>, ClienteRepository {

    @Override
    default Cliente guardar(Cliente cliente) {
        return save(cliente);
    }

    @Override
    default Optional<Cliente> buscarPorId(Long id) {
        return findById(id);
    }

    @Override
    default List<Cliente> listarTodos() {
        return findAll();
    }

    @Override
    default void eliminar(Cliente cliente) {
        delete(cliente);
    }

    @Override
    default Optional<Cliente> buscarPorIdentificacion(String tipo, String numero) {
        return findByTipoIdentificacionAndNumeroIdentificacion(tipo, numero);
    }


    Optional<Cliente> findByTipoIdentificacionAndNumeroIdentificacion(String tipo, String numero);
}
