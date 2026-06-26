package com.inube.ulaResort.controller;


import com.inube.ulaResort.dto.ApiResponse;
import com.inube.ulaResort.model.ReservacionModel;
import com.inube.ulaResort.service.ReservacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.inube.ulaResort.util.UtilConstants.*;

@RestController
@RequestMapping("/api/reservaciones")
@RequiredArgsConstructor
@Tag(name = "Reservaciones", description = "Operaciones de gestión de reservaciones")
public class ReservacionController {

    private final ReservacionService service;

    // RF-23 Registrar Reservación
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registrar(@RequestBody ReservacionModel reservacion) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG14, service.registrar(reservacion)));
    }

    // RF-31 Consultar Reservación por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.obtenerPorId(id)));
    }

    // RF-32 Consultar Reservaciones por Cliente
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<ApiResponse<?>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.listarPorCliente(idCliente)));
    }

    // RF-33 Cancelar Reservación (borrado lógico / cambio de estatus)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> cancelar(@PathVariable Integer id) {
        service.cancelar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, MSG15, null));
    }

    // RF-34 Listar Reservaciones activas
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listarActivas() {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG16, service.listarActivas()));
    }

}
