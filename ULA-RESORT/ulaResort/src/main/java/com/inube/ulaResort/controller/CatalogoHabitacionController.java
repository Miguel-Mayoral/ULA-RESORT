package com.inube.ulaResort.controller;


import com.inube.ulaResort.dto.ApiResponse;
import com.inube.ulaResort.model.CatalogoHabitacionModel;
import com.inube.ulaResort.service.CatalogoHabitacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.inube.ulaResort.util.UtilConstants.*;

@RestController
@RequestMapping("/api/catalogos-habitaciones")
@RequiredArgsConstructor
@Tag(name = "Catálogo de Habitaciones", description = "Operaciones de gestión del catálogo de habitaciones")
public class CatalogoHabitacionController {

    private final CatalogoHabitacionService service;

    // RF-05 Registrar Categoría de Habitación
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registrar(@RequestBody CatalogoHabitacionModel catalogo) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG2, service.registrar(catalogo)));
    }

    // RF-06 Listar Categorías de Habitación
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.listar()));
    }

    // RF-07 Obtener Categoría por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.obtenerPorId(id)));
    }

    // RF-08 Actualizar Categoría de Habitación
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @RequestBody CatalogoHabitacionModel catalogo) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG3, service.actualizar(id, catalogo)));
    }

    // RF-09 Eliminar Categoría (Borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, MSG4, null));
    }
}