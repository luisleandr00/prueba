package com.prueba.banco.infraestructure.repositories;


import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.model.Transacciones;
import com.prueba.banco.domain.ports.TransaccionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransaccionRepositoryJPA extends JpaRepository<Transacciones, Long>, TransaccionRepository {

    @Override
    default Transacciones guardar(Transacciones transaccion) {
        return save(transaccion);
    }

    @Override
    default List<Transacciones> listarPorProducto(Producto producto) {
        return findByProductoOrigenOrProductoDestino(producto, producto);
    }

    List<Transacciones> findByProductoOrigenOrProductoDestino(Producto origen, Producto destino);
}
