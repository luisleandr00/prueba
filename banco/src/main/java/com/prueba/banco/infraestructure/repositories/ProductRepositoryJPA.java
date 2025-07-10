package com.prueba.banco.infraestructure.repositories;


import com.prueba.banco.domain.model.Cliente;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.ports.ProductoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepositoryJPA extends JpaRepository<Producto, Long>, ProductoRepository {

    @Override
    default Producto guardar(Producto producto) {
        return save(producto);
    }

    @Override
    default Optional<Producto> buscarPorId(Long id) {
        return findById(id);
    }

    @Override
    default Optional<Producto> buscarPorNumeroCuenta(String numeroCuenta) {
        return findByNumeroCuenta(numeroCuenta);
    }

    @Override
    default List<Producto> listarPorCliente(Cliente cliente) {
        return findByCliente(cliente);
    }

    @Override
    default void eliminar(Producto producto) {
        delete(producto);
    }


    Optional<Producto> findByNumeroCuenta(String numeroCuenta);
    List<Producto> findByCliente(Cliente cliente);
}
