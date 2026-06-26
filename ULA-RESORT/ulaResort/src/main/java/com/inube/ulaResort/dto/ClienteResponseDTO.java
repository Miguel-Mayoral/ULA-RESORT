package com.inube.ulaResort.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ClienteResponseDTO {
    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String direccion;
    private Date fechaRegistro;
    private Integer estado;
}