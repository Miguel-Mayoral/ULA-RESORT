package com.inube.ulaResort.controller;


import com.inube.ulaResort.dto.ApiResponse;
import com.inube.ulaResort.model.ClienteModel;
import com.inube.ulaResort.service.ClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.inube.ulaResort.util.UtilConstants.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operaciones de gestión de clientes")
public class ClienteController {

    private final ClienteService service;

    // RF-01 Registrar Cliente
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registrar(@RequestBody ClienteModel cliente) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG5, service.registrar(cliente)));
    }

    // RF-02 Listar Clientes
    @GetMapping
    public ResponseEntity<ApiResponse<?>> listar() {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.listar()));
    }

    // RF-03 Obtener Cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.obtenerPorId(id)));
    }

    // RF-04 Actualizar Cliente
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @RequestBody ClienteModel cliente) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG6, service.actualizar(id, cliente)));
    }

    // RF-05 Eliminar Cliente (Borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, MSG7, null));
    }
}