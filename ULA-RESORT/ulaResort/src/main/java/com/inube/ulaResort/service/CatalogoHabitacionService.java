package com.inube.ulaResort.service;


import com.inube.ulaResort.dto.CatalogoHabitacionResponseDTO;
import com.inube.ulaResort.model.CatalogoHabitacionModel;
import com.inube.ulaResort.repository.CatalogoHabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.inube.ulaResort.util.UtilConstants.CODEPOS;
import static com.inube.ulaResort.util.UtilConstants.*;

@Service
@RequiredArgsConstructor
public class CatalogoHabitacionService {

    private final CatalogoHabitacionRepository repository;

    // RF-05 Registrar Categoría de Habitación
    public CatalogoHabitacionModel registrar(CatalogoHabitacionModel catalogo) {
        catalogo.setEstado(CODEPOS); // Activo por defecto
        return repository.save(catalogo);
    }

    // RF-06 Listar Categorías activas
    public List<CatalogoHabitacionResponseDTO> listar() {
        List<CatalogoHabitacionModel> catalogos = repository.findByEstado(CODEPOS);
        return catalogos.stream().map(this::mapearADTO).toList();
    }

    // RF-07 Obtener Categoría por ID
    public CatalogoHabitacionResponseDTO obtenerPorId(Integer id) {
        CatalogoHabitacionModel catalogo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));
        return mapearADTO(catalogo);
    }

    // RF-08 Actualizar Categoría
    public CatalogoHabitacionModel actualizar(Integer id, CatalogoHabitacionModel request) {
        CatalogoHabitacionModel catalogo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));

        catalogo.setNombreCategoria(request.getNombreCategoria());
        catalogo.setCapacidadPersonas(request.getCapacidadPersonas());
        catalogo.setDescripcion(request.getDescripcion());
        catalogo.setPrecioBase(request.getPrecioBase());
        catalogo.setAmenidades(request.getAmenidades());

        return repository.save(catalogo);
    }

    // RF-09 Eliminar Categoría (borrado lógico)
    public void eliminar(Integer id) {
        CatalogoHabitacionModel catalogo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));
        catalogo.setEstado(CODENEG); // Inactivo
        repository.save(catalogo);
    }

    // Método auxiliar para mapear a DTO
    private CatalogoHabitacionResponseDTO mapearADTO(CatalogoHabitacionModel catalogo) {
        CatalogoHabitacionResponseDTO dto = new CatalogoHabitacionResponseDTO();
        dto.setIdCatalogoHabitacion(catalogo.getIdCatalogoHabitacion());
        dto.setNombreCategoria(catalogo.getNombreCategoria());
        dto.setCapacidadPersonas(catalogo.getCapacidadPersonas());
        dto.setPrecioBase(catalogo.getPrecioBase());
        dto.setEstado(catalogo.getEstado());
        return dto;
    }
}