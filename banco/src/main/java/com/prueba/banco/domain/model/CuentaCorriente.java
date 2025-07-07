package com.prueba.banco.domain.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CORRIENTE")
@Entity
public class CuentaCorriente extends Producto {
    @Override
    public void validarSaldo(BigDecimal monto) {
        // Lógica específica para cuenta corriente
    }
}