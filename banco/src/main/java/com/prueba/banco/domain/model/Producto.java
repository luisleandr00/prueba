package com.prueba.banco.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_cuenta")
@Entity
public abstract class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoCuenta;
    private String numeroCuenta;
    private String estado;
    private BigDecimal saldo;
    private boolean exentaGMF;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public abstract void validarSaldo(BigDecimal monto);

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }
}
