package com.inube.ulaResort.controller;


import com.inube.ulaResort.dto.ApiResponse;
import com.inube.ulaResort.model.PaquetePromocionalModel;
import com.inube.ulaResort.service.PaquetePromocionalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.inube.ulaResort.util.UtilConstants.*;

@RestController
@RequestMapping("/api/paquetes")
@RequiredArgsConstructor
@Tag(name = "Paquetes Promocionales", description = "Operaciones de gestión de paquetes promocionales")
public class PaquetePromocionalController {

    private final PaquetePromocionalService service;

    // RF-19 Registrar Paquete Promocional
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registrar(@RequestBody PaquetePromocionalModel paquete) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG11, service.registrar(paquete)));
    }

    // RF-20 Listar Paquetes Promocionales vigentes
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.listar()));
    }

    // Obtener Paquete por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.obtenerPorId(id)));
    }

    // RF-21 Actualizar Paquete Promocional
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @RequestBody PaquetePromocionalModel paquete) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG12, service.actualizar(id, paquete)));
    }

    // RF-22 Eliminar Paquete Promocional (Borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, MSG13, null));
    }
}