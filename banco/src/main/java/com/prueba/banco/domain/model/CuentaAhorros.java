package com.prueba.banco.domain.model;


import java.math.BigDecimal;

public class CuentaAhorros extends Producto {

    @Override
    public void validarSaldo(BigDecimal monto) {
        if (monto == null) {
            throw new IllegalArgumentException("El monto no puede ser nulo");
        }

        if (this.getSaldo().add(monto).compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException(
                    String.format("Saldo insuficiente. Saldo actual: %s, Intento de retiro/transferencia: %s",
                            this.getSaldo(), monto.abs())
            );
        }
    }
}