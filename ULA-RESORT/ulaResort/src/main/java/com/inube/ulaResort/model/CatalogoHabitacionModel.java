package com.inube.ulaResort.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "CATALOGO_HABITACION")
@Data
public class CatalogoHabitacionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_catalogo_habitacion")
    @SequenceGenerator(
            name = "seq_catalogo_habitacion",
            sequenceName = "SEQ_CATALOGO_HABITACION",
            allocationSize = 1
    )
    @Column(name = "ID_CATALOGO_HABITACION")
    private Integer idCatalogoHabitacion;

    @Column(name = "NOMBRE_CATEGORIA")
    private String nombreCategoria;

    @Column(name = "CAPACIDAD_PERSONAS")
    private Integer capacidadPersonas;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "PRECIO_BASE")
    private BigDecimal precioBase;

    @Column(name = "AMENIDADES")
    private String amenidades;

    @Column(name = "ESTADO")
    private Integer estado = 1;
}