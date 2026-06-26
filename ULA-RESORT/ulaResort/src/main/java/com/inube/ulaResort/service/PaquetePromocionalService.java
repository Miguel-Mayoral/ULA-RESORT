package com.inube.ulaResort.service;


import com.inube.ulaResort.dto.PaquetePromocionalResponseDTO;
import com.inube.ulaResort.model.PaquetePromocionalModel;
import com.inube.ulaResort.repository.PaquetePromocionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.inube.ulaResort.util.UtilConstants.*;

@Service
@RequiredArgsConstructor
public class PaquetePromocionalService {

    private final PaquetePromocionalRepository repository;

    // RF-19 Registrar Paquete Promocional
    public PaquetePromocionalModel registrar(PaquetePromocionalModel paquete) {
        paquete.setEstado(CODEPOS); // Activo por defecto
        return repository.save(paquete);
    }

    // RF-20 Listar Paquetes Promocionales vigentes
    public List<PaquetePromocionalResponseDTO> listar() {
        List<PaquetePromocionalModel> paquetes = repository.findByEstado(CODEPOS);
        return paquetes.stream().map(this::mapearADTO).toList();
    }

    // Obtener Paquete por ID
    public PaquetePromocionalResponseDTO obtenerPorId(Integer id) {
        PaquetePromocionalModel paquete = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));
        return mapearADTO(paquete);
    }

    // RF-21 Actualizar Paquete Promocional
    public PaquetePromocionalModel actualizar(Integer id, PaquetePromocionalModel request) {
        PaquetePromocionalModel paquete = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));

        paquete.setNombrePaquete(request.getNombrePaquete());
        paquete.setDescripcion(request.getDescripcion());
        paquete.setPorcentajeDescuento(request.getPorcentajeDescuento());
        paquete.setFechaInicioVigencia(request.getFechaInicioVigencia());
        paquete.setFechaFinVigencia(request.getFechaFinVigencia());

        return repository.save(paquete);
    }

    // RF-22 Eliminar Paquete Promocional (borrado lógico)
    public void eliminar(Integer id) {
        PaquetePromocionalModel paquete = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));
        paquete.setEstado(CODENEG); // Inactivo
        repository.save(paquete);
    }

    // Método auxiliar para mapear a DTO
    private PaquetePromocionalResponseDTO mapearADTO(PaquetePromocionalModel paquete) {
        PaquetePromocionalResponseDTO dto = new PaquetePromocionalResponseDTO();
        dto.setIdPaquete(paquete.getIdPaquete());
        dto.setNombrePaquete(paquete.getNombrePaquete());
        dto.setPorcentajeDescuento(paquete.getPorcentajeDescuento());
        dto.setFechaInicioVigencia(paquete.getFechaInicioVigencia());
        dto.setFechaFinVigencia(paquete.getFechaFinVigencia());
        dto.setEstado(paquete.getEstado());
        return dto;
    }
}