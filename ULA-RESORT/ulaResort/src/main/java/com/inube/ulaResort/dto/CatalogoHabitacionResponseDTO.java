package com.inube.ulaResort.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CatalogoHabitacionResponseDTO {
    private Integer idCatalogoHabitacion;
    private String nombreCategoria;
    private Integer capacidadPersonas;
    private BigDecimal precioBase;
    private Integer estado;
}