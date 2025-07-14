package com.prueba.banco.infraestructure.mappers;

import com.prueba.banco.domain.model.CuentaAhorros;
import com.prueba.banco.domain.model.CuentaCorriente;
import com.prueba.banco.domain.model.Producto;
import com.prueba.banco.infraestructure.persistence.ProductoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoEntityMapper {

    public Producto toDomain(ProductoEntity entity) {
        Producto producto = switch(entity.getTipoCuenta()) {
            case "AHORROS" -> new CuentaAhorros();
            case "CORRIENTE" -> new CuentaCorriente();
            default -> throw new IllegalArgumentException("Tipo de cuenta no soportado");
        };

        producto.setId(entity.getId());
        producto.setTipoCuenta(entity.getTipoCuenta());
        producto.setNumeroCuenta(entity.getNumeroCuenta());
        producto.setEstado(entity.getEstado());
        producto.setSaldo(entity.getSaldo());
        producto.setExentaGMF(entity.isExentaGMF());
        producto.setFechaCreacion(entity.getFechaCreacion());
        producto.setFechaModificacion(entity.getFechaModificacion());
        producto.setClienteId(entity.getClienteId());

        return producto;
    }

    public ProductoEntity toEntity(Producto producto) {
        ProductoEntity entity = new ProductoEntity();
        entity.setId(producto.getId());
        entity.setTipoCuenta(producto.getTipoCuenta());
        entity.setNumeroCuenta(producto.getNumeroCuenta());
        entity.setEstado(producto.getEstado());
        entity.setSaldo(producto.getSaldo());
        entity.setExentaGMF(producto.isExentaGMF());
        entity.setClienteId(producto.getClienteId());
        return entity;
    }
}