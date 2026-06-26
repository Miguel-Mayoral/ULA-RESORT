package com.inube.ulaResort.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class PaquetePromocionalResponseDTO {
    private Integer idPaquete;
    private String nombrePaquete;
    private BigDecimal porcentajeDescuento;
    private Date fechaInicioVigencia;
    private Date fechaFinVigencia;
    private Integer estado;
}