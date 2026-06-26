package com.inube.ulaResort.service;

import com.inube.ulaResort.dto.CategoriaReporteDTO;
import com.inube.ulaResort.dto.ReservacionResponseDTO;
import com.inube.ulaResort.model.ReservacionModel;
import com.inube.ulaResort.model.ClienteModel;
import com.inube.ulaResort.model.HabitacionModel;
import com.inube.ulaResort.model.TarjetaCreditoModel;
import com.inube.ulaResort.model.PaquetePromocionalModel;
import com.inube.ulaResort.repository.ReservacionRepository;
import com.inube.ulaResort.repository.ClienteRepository;
import com.inube.ulaResort.repository.HabitacionRepository;
import com.inube.ulaResort.repository.TarjetaCreditoRepository;
import com.inube.ulaResort.repository.PaquetePromocionalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.inube.ulaResort.util.UtilConstants.*;

@Service
@RequiredArgsConstructor
public class ReservacionService {

    private final ReservacionRepository repository;
    private final ClienteRepository clienteRepository;
    private final HabitacionRepository habitacionRepository;
    private final TarjetaCreditoRepository tarjetaRepository;
    private final PaquetePromocionalRepository paqueteRepository;

    // RF-23 Registrar Reservación
    public ReservacionModel registrar(ReservacionModel reservacion) {
        // Validar cliente
        ClienteModel cliente = clienteRepository.findById(reservacion.getCliente().getIdCliente())
                .orElseThrow(() -> new RuntimeException(MSG13));
        if (cliente.getEstado().equals(CODENEG)) throw new RuntimeException(MSG6);

        // Validar habitación
        HabitacionModel habitacion = habitacionRepository.findById(reservacion.getHabitacion().getIdHabitacion())
                .orElseThrow(() -> new RuntimeException(MSG9));
        if (habitacion.getEstado().equals(CODENEG)) throw new RuntimeException(MSG5);

        // Validar capacidad
        if (reservacion.getCantidadHuespedes() > habitacion.getCapacidad())
            throw new RuntimeException(MSG7);

        // Validar tarjeta
        TarjetaCreditoModel tarjeta = tarjetaRepository.findById(reservacion.getTarjeta().getIdTarjeta())
                .orElseThrow(() -> new RuntimeException(MSG8));
        if (tarjeta.getEstado().equals(CODENEG)) throw new RuntimeException(MSG7);


        // Validar paquete promocional (si aplica)
        BigDecimal descuento = BigDecimal.ZERO;
        if (reservacion.getPaquete() != null && reservacion.getPaquete().getIdPaquete() != null) {
            PaquetePromocionalModel paquete = paqueteRepository.findById(reservacion.getPaquete().getIdPaquete())
                    .orElseThrow(() -> new RuntimeException(MSG11));
            reservacion.setPaquete(paquete);
            LocalDate hoy = LocalDate.now();

            // Convertir Date a LocalDate
            LocalDate inicio = paquete.getFechaInicioVigencia().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalDate fin = paquete.getFechaFinVigencia().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (paquete.getEstado().equals(CODENEG) ||
                    hoy.isBefore(inicio) ||
                    hoy.isAfter(fin)) {
                throw new RuntimeException(MSG11);
            }
            descuento = paquete.getPorcentajeDescuento();
        }

        // Verificar disponibilidad de la habitación
        Long traslapes = repository.existeTraslape(
                habitacion.getIdHabitacion(),
                reservacion.getFechaEntrada(),
                reservacion.getFechaSalida()
        );

        if (traslapes != null && traslapes > 0) {
            throw new RuntimeException("La habitación no está disponible en el rango de fechas solicitado");
        }


        // Calcular noches
        LocalDate entrada = reservacion.getFechaEntrada().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate salida = reservacion.getFechaSalida().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        long noches = ChronoUnit.DAYS.between(entrada, salida);

        if (noches <= 0) {
            throw new RuntimeException(MSG11); // Validación: salida debe ser mayor que entrada
        }

        // Calcular subtotal
        BigDecimal subtotal = habitacion.getPrecioBaseNoche().multiply(BigDecimal.valueOf(noches));

        // Aplicar descuento
        BigDecimal total = subtotal.subtract(subtotal.multiply(descuento).divide(BigDecimal.valueOf(100)));

        reservacion.setTotalPagar(total);
        reservacion.setFechaReservacion(new Date());
        reservacion.setEstatusReservacion("CONFIRMADA");
        reservacion.setEstado(CODEPOS);

        reservacion.setCliente(cliente);
        reservacion.setHabitacion(habitacion);
        reservacion.setTarjeta(tarjeta);

        return repository.save(reservacion);
    }

    // RF-31 Consultar Reservación por ID
    public ReservacionResponseDTO obtenerPorId(Integer id) {
        ReservacionModel reservacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));
        return mapearADTO(reservacion);
    }

    // RF-32 Consultar Reservaciones por Cliente
    public List<ReservacionResponseDTO> listarPorCliente(Integer idCliente) {
        List<ReservacionModel> reservaciones = repository.findByClienteIdClienteAndEstado(idCliente, CODEPOS);
        return reservaciones.stream().map(this::mapearADTO).toList();
    }

    // RF-33 Cancelar Reservación
    public void cancelar(Integer id) {
        ReservacionModel reservacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(MSG11));
        reservacion.setEstado(CODENEG);
        reservacion.setEstatusReservacion("CANCELADA");
        repository.save(reservacion);
    }

    // RF-34 Listar Reservaciones activas
    public List<ReservacionResponseDTO> listarActivas() {
        List<ReservacionModel> reservaciones = repository.findByEstado(CODEPOS);
        return reservaciones.stream().map(this::mapearADTO).toList();
    }

    // Reporte de Reservaciones Confirmadas
    public List<ReservacionResponseDTO> reporteConfirmadas() {
        List<ReservacionModel> reservaciones = repository.findByEstadoAndEstatusReservacion(CODEPOS,"CONFIRMADA");
        return reservaciones.stream().map(this::mapearADTO).toList();
    }


    // Servicio
    public Map<String, CategoriaReporteDTO> reporteConfirmadasConsolidado() {
        List<ReservacionModel> reservaciones = repository
                .findByEstadoAndEstatusReservacion(CODEPOS, "CONFIRMADA");

        return reservaciones.stream()
                .map(this::mapearADTO)
                .collect(Collectors.groupingBy(
                        ReservacionResponseDTO::getCategoriaHabitacion,
                        Collectors.collectingAndThen(Collectors.toList(), lista -> {
                            double totalGenerado = lista.stream()
                                    .map(ReservacionResponseDTO::getTotalPagado)
                                    .mapToDouble(BigDecimal::doubleValue)
                                    .sum();
                            return new CategoriaReporteDTO(lista, lista.size(), totalGenerado);
                        })
                ));

    }


    private ReservacionResponseDTO mapearADTO(ReservacionModel reservacion) {
        ReservacionResponseDTO dto = new ReservacionResponseDTO();
        dto.setIdReservacion(reservacion.getIdReservacion());
        dto.setIdCliente(reservacion.getCliente().getIdCliente());
        dto.setNombreCliente(reservacion.getCliente().getNombre());
        dto.setIdHabitacion(reservacion.getHabitacion().getIdHabitacion());
        dto.setCategoriaHabitacion(reservacion.getHabitacion().getCatalogoHabitacion().getNombreCategoria());
        dto.setFechaEntrada(reservacion.getFechaEntrada());
        dto.setFechaSalida(reservacion.getFechaSalida());
        dto.setCantidadHuespedes(reservacion.getCantidadHuespedes());
        dto.setTotalPagar(reservacion.getTotalPagar());
        dto.setEstatusReservacion(reservacion.getEstatusReservacion());

        if (reservacion.getPaquete() != null) {
            dto.setPaquetePromocional(reservacion.getPaquete().getNombrePaquete());
        }
        return dto;
    }

}