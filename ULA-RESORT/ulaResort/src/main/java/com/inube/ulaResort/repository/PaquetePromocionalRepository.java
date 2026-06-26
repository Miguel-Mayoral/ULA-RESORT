package com.inube.ulaResort.repository;


import com.inube.ulaResort.model.PaquetePromocionalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaquetePromocionalRepository extends JpaRepository<PaquetePromocionalModel, Integer> {

    List<PaquetePromocionalModel> findByEstado(Integer estado);

    @Query("""
        SELECT p
        FROM PaquetePromocionalModel p
        WHERE p.estado = 1
        AND CURRENT_DATE BETWEEN p.fechaInicioVigencia
                             AND p.fechaFinVigencia
    """)
    List<PaquetePromocionalModel> findPaquetesVigentes();

}