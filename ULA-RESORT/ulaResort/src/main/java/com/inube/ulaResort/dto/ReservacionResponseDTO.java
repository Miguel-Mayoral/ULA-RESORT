package com.inube.ulaResort.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
public class ReservacionResponseDTO {
    private Integer idReservacion;
    private Integer idCliente;
    private Integer idHabitacion;
    private Date fechaEntrada;
    private Date fechaSalida;
    private Integer cantidadHuespedes;
    private BigDecimal totalPagar;
    private String estatusReservacion;

    // Nuevos campos
    private String categoriaHabitacion;
    private String nombreCliente;
    private String paquetePromocional;

    // Getters y setters
    public String getCategoriaHabitacion() {
        return categoriaHabitacion;
    }
    public void setCategoriaHabitacion(String categoriaHabitacion) {
        this.categoriaHabitacion = categoriaHabitacion;
    }

    public BigDecimal getTotalPagado() {
        return totalPagar;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getPaquetePromocional() {
        return paquetePromocional;
    }
    public void setPaquetePromocional(String paquetePromocional) {
        this.paquetePromocional = paquetePromocional;
    }
}
