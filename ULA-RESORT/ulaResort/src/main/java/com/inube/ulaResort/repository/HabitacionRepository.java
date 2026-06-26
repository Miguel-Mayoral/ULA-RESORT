package com.inube.ulaResort.repository;


import com.inube.ulaResort.model.HabitacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepository extends JpaRepository<HabitacionModel, Integer> {

    List<HabitacionModel> findByEstado(Integer estado);

    List<HabitacionModel> findByEstadoAndDisponible(Integer estado, String disponible);

    List<HabitacionModel> findByCatalogoHabitacionIdCatalogoHabitacion(Integer idCatalogoHabitacion);

    // Método personalizado para disponibilidad
    @Query("SELECT h FROM HabitacionModel h " +
            "WHERE h.estado = :estado " +
            "AND h.disponible = 'Si' " +
            "AND h.capacidad >= :huespedes")
    List<HabitacionModel> findHabitacionesDisponibles(Integer estado, Integer huespedes);
}
