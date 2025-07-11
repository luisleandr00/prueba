package com.prueba.banco.domain.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@DiscriminatorValue("AHORROS")
public class CuentaAhorros extends Producto {

    public CuentaAhorros() {

    }

    @Override
    public void validarSaldo(BigDecimal monto) {
        Objects.requireNonNull(monto, "El monto no puede ser nulo");


        if (this.getSaldo().add(monto).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException(
                    String.format("Operación no permitida. Saldo insuficiente. " +
                                    "Saldo actual: %s, Intento de retiro/transferencia: %s. " +
                                    "Las cuentas de ahorros no pueden tener saldo negativo.",
                            this.getSaldo(), monto.abs())
            );
        }


        if ("CANCELADA".equalsIgnoreCase(this.getEstado())) {
            throw new IllegalStateException("No se pueden realizar movimientos en una cuenta cancelada");
        }


        if ("INACTIVA".equalsIgnoreCase(this.getEstado())) {
            throw new IllegalStateException("No se pueden realizar movimientos en una cuenta inactiva");
        }
    }
}