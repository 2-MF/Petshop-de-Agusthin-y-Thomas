package com.petshop.service;

import com.petshop.model.*;
import com.petshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Transactional
    public Solicitud crearSolicitud(Long clienteId, Long mascotaId, TipoServicio tipoServicio, String observaciones) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Mascota mascota = mascotaRepository.findById(mascotaId)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        // Validar que la mascota pertenezca al cliente
        if (!mascota.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("La mascota no pertenece a este cliente");
        }

        // Validar que el tipo de servicio sea válido
        if (tipoServicio == null) {
            throw new RuntimeException("Debe seleccionar un tipo de servicio: CORTE_PELO, BAÑO o CORTE_Y_BAÑO");
        }

        Solicitud solicitud = Solicitud.builder()
            .cliente(cliente)
            .mascota(mascota)
            .tipoServicio(tipoServicio)
            .observaciones(observaciones)
            .build();

        return solicitudRepository.save(solicitud);
    }

    public List<Solicitud> listarPorCliente(Long clienteId) {
        return solicitudRepository.findByClienteId(clienteId);
    }

    public List<Solicitud> listarTodas() {
        return solicitudRepository.findAll();
    }

    @Transactional
    public Solicitud cambiarEstado(Long solicitudId, EstadoSolicitud nuevoEstado) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(nuevoEstado);
        return solicitudRepository.save(solicitud);
    }
}
