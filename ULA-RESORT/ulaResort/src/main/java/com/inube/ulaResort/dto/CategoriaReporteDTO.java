package com.inube.ulaResort.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaReporteDTO {
    private List<ReservacionResponseDTO> reservaciones;
    private int totalReservaciones;
    private double totalGenerado;
}
