package com.prueba.banco.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransaccionesDTO {
    private Long id;
    private String tipo;
    private BigDecimal monto;
    private Long productoOrigenId;
    private Long productoDestinoId;
    private LocalDateTime fecha;
    private String estado;


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

    public Long getProductoOrigenId() {
        return productoOrigenId;
    }

    public void setProductoOrigenId(Long productoOrigenId) {
        this.productoOrigenId = productoOrigenId;
    }

    public Long getProductoDestinoId() {
        return productoDestinoId;
    }

    public void setProductoDestinoId(Long productoDestinoId) {
        this.productoDestinoId = productoDestinoId;
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