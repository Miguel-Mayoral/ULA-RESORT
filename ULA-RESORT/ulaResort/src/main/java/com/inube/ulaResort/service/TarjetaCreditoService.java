package com.inube.ulaResort.service;

import com.inube.ulaResort.dto.TarjetaCreditoResponseDTO;
import com.inube.ulaResort.model.TarjetaCreditoModel;
import com.inube.ulaResort.repository.TarjetaCreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import static com.inube.ulaResort.util.UtilConstants.*;

@Service
@RequiredArgsConstructor
public class TarjetaCreditoService {

    private final TarjetaCreditoRepository repository;

    // RF-15 Registrar Tarjeta de Crédito
    public TarjetaCreditoModel registrar(TarjetaCreditoModel tarjeta) {
        tarjeta.setEstado(CODEPOS); // Activa por defecto
        return repository.save(tarjeta);
    }

    // RF-16 Consultar Tarjetas por Cliente (solo activas)
    public List<TarjetaCreditoResponseDTO> listarPorCliente(Integer idCliente) {
        List<TarjetaCreditoModel> tarjetas = repository.findByClienteIdClienteAndEstado(idCliente,CODEPOS);
        return tarjetas.stream().map(this::mapearADTO).toList();
    }

    // RF-17 Actualizar Tarjeta de Crédito
    public TarjetaCreditoModel actualizar(Integer id, TarjetaCreditoModel request) {
        TarjetaCreditoModel tarjeta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG18));

        if (request.getTitular() != null)
            tarjeta.setTitular(request.getTitular());

        if (request.getBanco() != null)
            tarjeta.setBanco(request.getBanco());

        if (request.getTipoTarjeta() != null)
            tarjeta.setTipoTarjeta(request.getTipoTarjeta());

        if (request.getFechaExpiracion() != null)
            tarjeta.setFechaExpiracion(request.getFechaExpiracion());

        if (request.getNumeroTarjeta() != null)
            tarjeta.setNumeroTarjeta(request.getNumeroTarjeta());

        if (request.getCvv() != null)
            tarjeta.setCvv(request.getCvv());

        if (request.getEstado() != null)
            tarjeta.setEstado(request.getEstado());


        return repository.save(tarjeta);
    }

    // RF-18 Eliminar Tarjeta de Crédito (borrado lógico)
    public void eliminar(Integer id) {
        TarjetaCreditoModel tarjeta = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG18));
        tarjeta.setEstado(CODENEG); // Inactiva
        repository.save(tarjeta);
    }

    private TarjetaCreditoResponseDTO mapearADTO(TarjetaCreditoModel tarjeta) {
        TarjetaCreditoResponseDTO dto = new TarjetaCreditoResponseDTO();
        dto.setIdTarjeta(tarjeta.getIdTarjeta());
        dto.setTitular(tarjeta.getTitular());
        dto.setNumeroTarjeta(tarjeta.getNumeroTarjeta()); // número completo
        dto.setFechaExpiracion(tarjeta.getFechaExpiracion());
        dto.setCvv(tarjeta.getCvv());
        dto.setBanco(tarjeta.getBanco());
        dto.setTipoTarjeta(tarjeta.getTipoTarjeta());
        dto.setIdCliente(tarjeta.getCliente().getIdCliente());
        dto.setEstado(tarjeta.getEstado());
        return dto;
    }

}