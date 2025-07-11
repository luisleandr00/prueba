package com.prueba.banco.application.mapper;

import com.prueba.banco.application.dto.ProductoDTO;
import com.prueba.banco.domain.model.Producto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductoMapper {
    public ProductoDTO toDto(Producto producto) {
        if (producto == null) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setTipoCuenta(producto.getTipoCuenta());
        dto.setNumeroCuenta(producto.getNumeroCuenta());
        dto.setEstado(producto.getEstado());
        dto.setSaldo(producto.getSaldo());
        dto.setExentaGMF(producto.isExentaGMF());
        dto.setFechaCreacion(producto.getFechaCreacion());

        if (producto.getCliente() != null) {
            dto.setClienteId(producto.getCliente().getId());
        }

        return dto;
    }

    public Producto toDomain(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            return null;
        }

        Producto producto = new Producto() {
            @Override
            public void validarSaldo(BigDecimal monto) {

            }
        };

        producto.setId(productoDTO.getId());
        producto.setTipoCuenta(productoDTO.getTipoCuenta());
        producto.setNumeroCuenta(productoDTO.getNumeroCuenta());
        producto.setEstado(productoDTO.getEstado());
        producto.setSaldo(productoDTO.getSaldo());
        producto.setExentaGMF(productoDTO.isExentaGMF());
        producto.setFechaCreacion(productoDTO.getFechaCreacion());

        return producto;
    }
}