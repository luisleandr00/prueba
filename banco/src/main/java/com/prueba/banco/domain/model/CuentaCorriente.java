package com.prueba.banco.domain.model;

import jakarta.persistence.DiscriminatorValue;
import java.math.BigDecimal;
import java.util.Objects;


@DiscriminatorValue("CORRIENTE")
public class CuentaCorriente extends Producto {
    private static final BigDecimal LIMITE_SOBREGIRO = new BigDecimal("1000000");

    public CuentaCorriente() {

    }

    @Override
    public void validarSaldo(BigDecimal monto) {
        Objects.requireNonNull(monto, "El monto no puede ser nulo");

        if ("CANCELADA".equalsIgnoreCase(this.getEstado())) {
            throw new IllegalStateException("No se pueden realizar movimientos en una cuenta cancelada");
        }

        if ("INACTIVA".equalsIgnoreCase(this.getEstado())) {
            throw new IllegalStateException("No se pueden realizar movimientos en una cuenta inactiva");
        }

        if (this.getSaldo().add(monto).compareTo(LIMITE_SOBREGIRO.negate()) < 0) {
            throw new IllegalStateException(
                    String.format("Operación no permitida. Límite de sobregiro excedido. " +
                                    "Saldo actual: %s, Intento de retiro/transferencia: %s. " +
                                    "Límite de sobregiro: %s",
                            this.getSaldo(), monto.abs(), LIMITE_SOBREGIRO)
            );
        }
    }
}