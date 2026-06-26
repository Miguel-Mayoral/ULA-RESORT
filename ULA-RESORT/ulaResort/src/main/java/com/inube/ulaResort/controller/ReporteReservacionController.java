package com.inube.ulaResort.controller;

import com.inube.ulaResort.dto.ApiResponse;
import com.inube.ulaResort.service.ReservacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.inube.ulaResort.util.UtilConstants.MSG1;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Reportes de reservaciones")
public class ReporteReservacionController {

    private final ReservacionService service;

    // RF-Reporte de Reservaciones Confirmadas
    @GetMapping("/reservaciones-confirmadas")
    public ResponseEntity<ApiResponse<?>> reporteConfirmadas() {
        return ResponseEntity.ok(
                new ApiResponse<>(true, MSG1, service.reporteConfirmadas())
        );
    }
}