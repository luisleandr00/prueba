package com.prueba.banco.application.mapper;


import com.prueba.banco.application.dto.ProductoDTO;
import com.prueba.banco.domain.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public ProductoDTO toDto(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .tipoCuenta(producto.getTipoCuenta())
                .numeroCuenta(producto.getNumeroCuenta())
                .estado(producto.getEstado())
                .saldo(producto.getSaldo())
                .exentaGMF(producto.isExentaGMF())
                .fechaCreacion(producto.getFechaCreacion())
                .clienteId(producto.getCliente() != null ? producto.getCliente().getId() : null)
                .build();
    }

    public Producto toDomain(ProductoDTO productoDTO) {
        return Producto.builder()
                .id(productoDTO.getId())
                .tipoCuenta(productoDTO.getTipoCuenta())
                .numeroCuenta(productoDTO.getNumeroCuenta())
                .estado(productoDTO.getEstado())
                .saldo(productoDTO.getSaldo())
                .exentaGMF(productoDTO.isExentaGMF())
                .fechaCreacion(productoDTO.getFechaCreacion())
                .build();
    }
}
