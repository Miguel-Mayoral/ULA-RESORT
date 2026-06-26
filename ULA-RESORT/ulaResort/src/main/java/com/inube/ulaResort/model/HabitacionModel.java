package com.inube.ulaResort.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "HABITACIONES")
@Data
public class HabitacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_habitacion")
    @SequenceGenerator(
            name = "seq_habitacion",
            sequenceName = "SEQ_HABITACIONES",
            allocationSize = 1
    )
    @Column(name = "ID_HABITACION")
    private Integer idHabitacion;

    @Column(name = "NUMERO_HABITACION")
    private String numeroHabitacion;

    @Column(name = "PISO")
    private Integer piso;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO_BASE_NOCHE")
    private BigDecimal precioBaseNoche;

    @Column(name = "CAPACIDAD")
    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "ID_CATALOGO_HABITACION")
    private CatalogoHabitacionModel catalogoHabitacion;

    @Column(name = "DISPONIBLE")
    private String disponible;

    @Column(name = "ESTADO")
    private Integer estado = 1;
}