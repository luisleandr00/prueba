package com.prueba.banco.domain.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CORRIENTE")
@Entity
public class CuentaCorriente extends Producto {
    @Override
    public void validarSaldo(BigDecimal monto) {
        Objects.requireNonNull(monto, "El monto no puede ser nulo");


        if ("CANCELADA".equalsIgnoreCase(this.getEstado())) {
            throw new IllegalStateException(
                    "No se pueden realizar movimientos en una cuenta cancelada"
            );
        }


        if ("INACTIVA".equalsIgnoreCase(this.getEstado())) {
            throw new IllegalStateException(
                    "No se pueden realizar movimientos en una cuenta inactiva"
            );
        }

        BigDecimal limiteSobregiro = new BigDecimal("1000000");
        if (this.getSaldo().add(monto).compareTo(limiteSobregiro.negate()) < 0) {
            throw new IllegalStateException(
                    String.format("Operación no permitida. Límite de sobregiro excedido. " +
                                    "Saldo actual: %s, Intento de retiro/transferencia: %s. " +
                                    "Límite de sobregiro: %s",
                            this.getSaldo(), monto.abs(), limiteSobregiro)
            );
        }
    }
}