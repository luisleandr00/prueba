package com.prueba.banco.domain.ports;


import com.prueba.banco.domain.model.Transacciones;
import java.util.List;
import java.util.Optional;

public interface TransaccionRepository {
    Transacciones guardar(Transacciones transaccion);
    Optional<Transacciones> buscarPorId(Long id);
    List<Transacciones> listarPorProductoId(Long productoId);
}