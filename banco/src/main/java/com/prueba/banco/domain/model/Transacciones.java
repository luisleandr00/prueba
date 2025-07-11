package com.prueba.banco.domain.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
public class Transacciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "producto_origen_id")
    private Producto productoOrigen;

    @ManyToOne
    @JoinColumn(name = "producto_destino_id")
    private Producto productoDestino;

    private LocalDateTime fecha;
    private String estado;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Producto getProductoOrigen() {
        return productoOrigen;
    }

    public void setProductoOrigen(Producto productoOrigen) {
        this.productoOrigen = productoOrigen;
    }

    public Producto getProductoDestino() {
        return productoDestino;
    }

    public void setProductoDestino(Producto productoDestino) {
        this.productoDestino = productoDestino;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}