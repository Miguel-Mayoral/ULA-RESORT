package com.inube.ulaResort.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "RESERVACION")
@Data
public class ReservacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_reservacion")
    @SequenceGenerator(
            name = "seq_reservacion",
            sequenceName = "seq_reservacion",
            allocationSize = 1
    )
    @Column(name = "ID_RESERVACION")
    private Integer idReservacion;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ID_CLIENTE")
    private ClienteModel cliente;

    @ManyToOne
    @JoinColumn(name = "ID_HABITACION")
    private HabitacionModel habitacion;

    @ManyToOne
    @JoinColumn(name = "ID_TARJETA")
    private TarjetaCreditoModel tarjeta;

    @ManyToOne
    @JoinColumn(name = "ID_PAQUETE")
    private PaquetePromocionalModel paquete;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ENTRADA")
    private Date fechaEntrada;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_SALIDA")
    private Date fechaSalida;

    @Column(name = "CANTIDAD_HUESPEDES")
    private Integer cantidadHuespedes;

    @Column(name = "TOTAL_PAGAR")
    private BigDecimal totalPagar;

    @Column(name = "ESTATUS_RESERVACION")
    private String estatusReservacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_RESERVACION")
    private Date fechaReservacion;

    @Column(name = "ESTADO")
    private Integer estado = 1;
}