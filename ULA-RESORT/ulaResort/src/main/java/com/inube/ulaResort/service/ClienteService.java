package com.inube.ulaResort.service;


import com.inube.ulaResort.dto.ClienteResponseDTO;
import com.inube.ulaResort.model.ClienteModel;
import com.inube.ulaResort.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.inube.ulaResort.util.UtilConstants.*;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    // RF-01 Registrar Cliente
    public ClienteModel registrar(ClienteModel cliente) {
        cliente.setEstado(CODEPOS); // Activo por defecto
        return repository.save(cliente);
    }

    // RF-02 Listar Clientes activos
    public List<ClienteResponseDTO> listar() {
        List<ClienteModel> clientes = repository.findByEstado(CODEPOS);
        return clientes.stream().map(this::mapearADTO).toList();
    }

    // RF-03 Obtener Cliente por ID
    public ClienteResponseDTO obtenerPorId(Integer id) {
        ClienteModel cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG15));
        return mapearADTO(cliente);
    }

    // RF-04 Actualizar Cliente
    public ClienteModel actualizar(Integer id, ClienteModel request) {
        ClienteModel cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG15));

        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setTelefono(request.getTelefono());
        cliente.setCorreo(request.getCorreo());
        cliente.setDireccion(request.getDireccion());

        return repository.save(cliente);
    }

    // RF-05 Eliminar Cliente (borrado lógico)
    public void eliminar(Integer id) {
        ClienteModel cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG15));
        cliente.setEstado(CODENEG); // Inactivo
        repository.save(cliente);
    }

    // Método auxiliar para mapear a DTO
    private ClienteResponseDTO mapearADTO(ClienteModel cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombre(cliente.getNombre());
        dto.setApellido(cliente.getApellido());
        dto.setTelefono(cliente.getTelefono());
        dto.setCorreo(cliente.getCorreo());
        dto.setDireccion(cliente.getDireccion());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        dto.setEstado(cliente.getEstado());
        return dto;
    }
}