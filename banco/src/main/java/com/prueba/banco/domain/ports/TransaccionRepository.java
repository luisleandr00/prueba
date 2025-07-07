package com.prueba.banco.domain.ports;

import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.domain.model.Transacciones;

import java.util.List;

public interface TransaccionRepository {

    Transacciones guardar(Transacciones transaccion);

    List<Transacciones> listarPorProducto(Producto producto);
}