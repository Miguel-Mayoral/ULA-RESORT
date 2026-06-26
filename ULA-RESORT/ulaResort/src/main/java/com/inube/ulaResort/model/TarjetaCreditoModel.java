package com.inube.ulaResort.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "TARJETAS_CREDITO")
@Data
public class TarjetaCreditoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tarjeta")
    @SequenceGenerator(
            name = "seq_tarjeta",
            sequenceName = "SEQ_TARJETAS_CREDITO",
            allocationSize = 1
    )
    @Column(name = "ID_TARJETA")
    private Integer idTarjeta;

    @Column(name = "TITULAR")
    private String titular;

    @Column(name = "NUMERO_TARJETA")
    private String numeroTarjeta;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_EXPIRACION")
    private Date fechaExpiracion;

    @Column(name = "CVV")
    private String cvv;

    @Column(name = "BANCO")
    private String banco;

    @Column(name = "TIPO_TARJETA")
    private String tipoTarjeta;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "ID_CLIENTE")
    private ClienteModel cliente;

    @Column(name = "ESTADO")
    private Integer estado = 1;
}