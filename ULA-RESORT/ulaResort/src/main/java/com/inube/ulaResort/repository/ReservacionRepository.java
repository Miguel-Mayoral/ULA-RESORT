package com.inube.ulaResort.repository;


import com.inube.ulaResort.model.ReservacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservacionRepository extends JpaRepository<ReservacionModel, Integer> {

    List<ReservacionModel> findByEstado(Integer estado);

    List<ReservacionModel> findByClienteIdClienteAndEstado(Integer idCliente, Integer estado);

    List<ReservacionModel> findByEstadoAndEstatusReservacion(Integer estado, String estatusReservacion);

    @Query("""
           SELECT COUNT(r)
           FROM ReservacionModel r
           WHERE r.habitacion.idHabitacion = :idHabitacion
           AND r.estado = 1
           AND r.estatusReservacion <> 'CANCELADA'
           AND (
                :fechaEntrada <= r.fechaSalida
                AND
                :fechaSalida >= r.fechaEntrada
           )
           """)
    Long existeTraslape(Integer idHabitacion, Date fechaEntrada, Date fechaSalida);

}