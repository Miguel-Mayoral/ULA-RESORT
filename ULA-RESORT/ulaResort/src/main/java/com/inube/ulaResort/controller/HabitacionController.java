package com.inube.ulaResort.controller;


import com.inube.ulaResort.dto.ApiResponse;
import com.inube.ulaResort.model.HabitacionModel;
import com.inube.ulaResort.service.HabitacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.inube.ulaResort.util.UtilConstants.*;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
@Tag(name = "Habitaciones", description = "Operaciones de gestión de habitaciones")
public class HabitacionController {

    private final HabitacionService service;

    // RF-09 Registrar Habitación
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registrar(@RequestBody HabitacionModel habitacion) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG8, service.registrar(habitacion)));
    }

    // RF-12 Listar Habitaciones activas
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.listar()));
    }

    // RF-11 Obtener Habitación por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.obtenerPorId(id)));
    }

    // RF-13 Actualizar Habitación
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @RequestBody HabitacionModel habitacion) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG9, service.actualizar(id, habitacion)));
    }

    // RF-14 Eliminar Habitación (Borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, MSG10, null));
    }

    // RF-19 Consultar Habitaciones Disponibles por Fechas
    @GetMapping("/disponibles")
    public ResponseEntity<ApiResponse<?>> consultarDisponibles(
            @RequestParam String fechaEntrada,
            @RequestParam String fechaSalida,
            @RequestParam Integer huespedes) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.consultarDisponibles(fechaEntrada, fechaSalida, huespedes)));
    }
}