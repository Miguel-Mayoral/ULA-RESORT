package com.inube.ulaResort.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TarjetaCreditoResponseDTO {
    private Integer idTarjeta;
    private String titular;
    private String numeroTarjeta;   // número completo
    private Date fechaExpiracion;
    private String cvv;
    private String banco;
    private String tipoTarjeta;
    private Integer idCliente;
    private Integer estado;

    // getters y setters
}
