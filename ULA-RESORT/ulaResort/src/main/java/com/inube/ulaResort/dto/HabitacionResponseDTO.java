package com.inube.ulaResort.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class HabitacionResponseDTO {
    private Integer idHabitacion;
    private String numeroHabitacion;
    private Integer piso;
    private BigDecimal precioBaseNoche;
    private Integer capacidad;
    private String disponible;
    private Integer estado;
}