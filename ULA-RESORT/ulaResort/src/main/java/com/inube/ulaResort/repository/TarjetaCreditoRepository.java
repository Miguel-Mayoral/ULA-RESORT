package com.inube.ulaResort.repository;


import com.inube.ulaResort.model.TarjetaCreditoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCreditoModel, Integer> {

    List<TarjetaCreditoModel> findByEstado(Integer estado);

    List<TarjetaCreditoModel> findByClienteIdClienteAndEstado(Integer idCliente, Integer estado);

}