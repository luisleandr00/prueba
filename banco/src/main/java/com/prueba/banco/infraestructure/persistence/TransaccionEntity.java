package com.prueba.banco.infraestructure.persistence;

import com.prueba.banco.domain.model.Producto;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones")
public class TransaccionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;
    private BigDecimal monto;

    @Column(name = "producto_origen_id")
    private Long productoOrigenId;

    @Column(name = "producto_destino_id")
    private Long productoDestinoId;

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

    public Long getProductoOrigenId() { return productoOrigenId; }
    public void setProductoOrigenId(Long productoOrigenId) { this.productoOrigenId = productoOrigenId; }

    public Long getProductoDestinoId() { return productoDestinoId; }
    public void setProductoDestinoId(Long productoDestinoId) { this.productoDestinoId = productoDestinoId; }
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
