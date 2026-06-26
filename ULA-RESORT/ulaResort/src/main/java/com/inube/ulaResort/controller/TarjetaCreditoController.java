package com.inube.ulaResort.controller;


import com.inube.ulaResort.dto.ApiResponse;
import com.inube.ulaResort.model.TarjetaCreditoModel;
import com.inube.ulaResort.service.TarjetaCreditoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.inube.ulaResort.util.UtilConstants.*;

@RestController
@RequestMapping("/api/tarjetas")
@RequiredArgsConstructor
@Tag(name = "Tarjetas de Crédito", description = "Operaciones de gestión de tarjetas de crédito")
public class TarjetaCreditoController {

    private final TarjetaCreditoService service;

    // RF-15 Registrar Tarjeta de Crédito
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registrar(@RequestBody TarjetaCreditoModel tarjeta) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG17, service.registrar(tarjeta)));
    }

    // RF-16 Consultar Tarjetas por Cliente
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<ApiResponse<?>> listarPorCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG1, service.listarPorCliente(idCliente)));
    }

    // RF-17 Actualizar Tarjeta de Crédito
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> actualizar(@PathVariable Integer id, @RequestBody TarjetaCreditoModel tarjeta) {
        return ResponseEntity.ok(new ApiResponse<>(true, MSG18, service.actualizar(id, tarjeta)));
    }

    // RF-18 Eliminar Tarjeta de Crédito (Borrado lógico)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.ok(new ApiResponse<>(true, MSG19, null));
    }
}