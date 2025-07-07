package com.prueba.banco.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("AHORROS")
@Entity
public class CuentaAhorros extends Producto {
    @Override
    public void validarSaldo(BigDecimal monto) {
        if (getSaldo().add(monto).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Cuenta de ahorros no puede tener saldo negativo");
        }
    }
}
