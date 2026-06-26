package com.inube.ulaResort.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "PAQUETE_PROMOCIONAL")
@Data
public class PaquetePromocionalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paquete_promocional")
    @SequenceGenerator(
            name = "seq_paquete_promocional",
            sequenceName = "SEQ_PAQUETE_PROMOCIONAL",
            allocationSize = 1
    )
    @Column(name = "ID_PAQUETE")
    private Integer idPaquete;

    @Column(name = "NOMBRE_PAQUETE")
    private String nombrePaquete;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PORCENTAJE_DESCUENTO")
    private BigDecimal porcentajeDescuento;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_INICIO_VIGENCIA")
    private Date fechaInicioVigencia;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_FIN_VIGENCIA")
    private Date fechaFinVigencia;

    @Column(name = "CONDICIONES")
    private String condiciones;

    @Column(name = "ESTADO")
    private Integer estado = 1;
}