package com.prueba.banco.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("AHORROS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CuentaAhorros extends Producto {
    @Override
    public void validarSaldo(BigDecimal monto) {
        Objects.requireNonNull(monto, "El monto no puede ser nulo");

        // La cuenta de ahorros no puede tener saldo negativo
        if (this.getSaldo().add(monto).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException(
                    String.format("Operación no permitida. Saldo insuficiente. " +
                                    "Saldo actual: %s, Intento de retiro/transferencia: %s. " +
                                    "Las cuentas de ahorros no pueden tener saldo negativo.",
                            this.getSaldo(), monto.abs())
            );
        }

        // Validación adicional para cuentas canceladas
        if ("CANCELADA".equalsIgnoreCase(this.getEstado())) {
            throw new IllegalStateException(
                    "No se pueden realizar movimientos en una cuenta cancelada"
            );
        }

        // Validación para cuentas inactivas
        if ("INACTIVA".equalsIgnoreCase(this.getEstado())) {
            throw new IllegalStateException(
                    "No se pueden realizar movimientos en una cuenta inactiva"
            );
        }
    }
}
