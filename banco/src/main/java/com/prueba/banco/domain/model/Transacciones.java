package com.prueba.banco.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transacciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // CONSIGNACION, RETIRO, TRANSFERENCIA
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "producto_origen_id")
    private Producto productoOrigen;

    @ManyToOne
    @JoinColumn(name = "producto_destino_id")
    private Producto productoDestino;

    private LocalDateTime fecha;
    private String estado;
}