package com.prueba.banco.application.dto;


import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class TransaccionesDTO {
    private Long id;
    private String tipo;
    private BigDecimal monto;
    private Long productoOrigenId;
    private Long productoDestinoId;
    private LocalDateTime fecha;
    private String estado;
}
