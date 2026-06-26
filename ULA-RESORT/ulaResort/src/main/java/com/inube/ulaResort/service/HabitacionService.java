package com.inube.ulaResort.service;


import com.inube.ulaResort.dto.HabitacionResponseDTO;
import com.inube.ulaResort.model.HabitacionModel;
import com.inube.ulaResort.repository.HabitacionRepository;
import com.inube.ulaResort.repository.CatalogoHabitacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.inube.ulaResort.util.UtilConstants.*;

@Service
@RequiredArgsConstructor
public class HabitacionService {

    private final HabitacionRepository repository;
    private final CatalogoHabitacionRepository catalogoRepository;

    // RF-09 Registrar Habitación
    public HabitacionModel registrar(HabitacionModel habitacion) {
        // Validar existencia del catálogo
        catalogoRepository.findById(habitacion.getCatalogoHabitacion().getIdCatalogoHabitacion())
                .orElseThrow(() -> new RuntimeException(MSG11));

        habitacion.setEstado(CODEPOS); // Activa por defecto
        habitacion.setDisponible("Si"); // Inicializar disponibilidad
        return repository.save(habitacion);
    }

    // RF-12 Listar Habitaciones activas
    public List<HabitacionResponseDTO> listar() {
        List<HabitacionModel> habitaciones = repository.findByEstado(CODEPOS);
        return habitaciones.stream().map(this::mapearADTO).toList();
    }

    // RF-11 Obtener Habitación por ID
    public HabitacionResponseDTO obtenerPorId(Integer id) {
        HabitacionModel habitacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));
        return mapearADTO(habitacion);
    }

    // RF-13 Actualizar Habitación
    public HabitacionModel actualizar(Integer id, HabitacionModel request) {
        HabitacionModel habitacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));

        habitacion.setNumeroHabitacion(request.getNumeroHabitacion());
        habitacion.setPiso(request.getPiso());
        habitacion.setDescripcion(request.getDescripcion());
        habitacion.setPrecioBaseNoche(request.getPrecioBaseNoche());
        habitacion.setCapacidad(request.getCapacidad());
        habitacion.setCatalogoHabitacion(request.getCatalogoHabitacion());

        return repository.save(habitacion);
    }

    // RF-14 Eliminar Habitación (borrado lógico)
    public void eliminar(Integer id) {
        HabitacionModel habitacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));
        habitacion.setEstado(CODENEG); // Inactiva
        repository.save(habitacion);
    }

    // RF-19 Consultar Habitaciones Disponibles por Fechas
    public List<HabitacionResponseDTO> consultarDisponibles(String fechaEntrada, String fechaSalida, Integer huespedes) {
        // Aquí iría la lógica de validación de traslapes de fechas en reservaciones
        List<HabitacionModel> disponibles = repository.findHabitacionesDisponibles(CODEPOS, huespedes);
        return disponibles.stream().map(this::mapearADTO).toList();
    }

    // Método auxiliar para mapear a DTO
    private HabitacionResponseDTO mapearADTO(HabitacionModel habitacion) {
        HabitacionResponseDTO dto = new HabitacionResponseDTO();
        dto.setIdHabitacion(habitacion.getIdHabitacion());
        dto.setNumeroHabitacion(habitacion.getNumeroHabitacion());
        dto.setPiso(habitacion.getPiso());
        dto.setPrecioBaseNoche(habitacion.getPrecioBaseNoche());
        dto.setCapacidad(habitacion.getCapacidad());
        dto.setDisponible(habitacion.getDisponible());
        dto.setEstado(habitacion.getEstado());
        return dto;
    }
}