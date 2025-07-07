package com.prueba.banco.domain.ports;

import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.model.Transacciones;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TransaccionRepository {

    Transacciones guardar(Transacciones transaccion);

    List<Transacciones> listarPorProducto(Producto producto);
}