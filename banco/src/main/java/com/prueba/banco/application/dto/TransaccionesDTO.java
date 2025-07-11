package com.prueba.banco.application.dto;


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
public class TransaccionesDTO {
    private Long id;
    private String tipo;
    private BigDecimal monto;
    private Long productoOrigenId;
    private Long productoDestinoId;
    private LocalDateTime fecha;
    private String estado;
}
