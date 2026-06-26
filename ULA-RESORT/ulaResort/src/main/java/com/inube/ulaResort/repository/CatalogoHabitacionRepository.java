package com.inube.ulaResort.repository;


import com.inube.ulaResort.model.CatalogoHabitacionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogoHabitacionRepository extends JpaRepository<CatalogoHabitacionModel, Integer> {

    List<CatalogoHabitacionModel> findByEstado(Integer estado);

}