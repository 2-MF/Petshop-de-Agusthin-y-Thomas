package com.petshop.service;

import com.petshop.model.*;
import com.petshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class AgendaService {

    private static final int MAX_BANOS_POR_DIA = 10;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Transactional
    public Agenda agendar(Agenda agenda) {
        // Validar que el funcionario sea un bañador
        Funcionario banador = funcionarioRepository.findById(agenda.getBanador().getId())
            .orElseThrow(() -> new RuntimeException("Bañador no encontrado"));

        if (banador.getRol() != RolFuncionario.BAÑADOR) {
            throw new RuntimeException("El funcionario asignado no es un bañador");
        }

        // Validar máximo 10 baños por día
        long count = agendaRepository.countActivosByFecha(agenda.getFecha());
        if (count >= MAX_BANOS_POR_DIA) {
            throw new RuntimeException("No se pueden agendar más de " + MAX_BANOS_POR_DIA + 
                " baños para el día " + agenda.getFecha());
        }

        // Validar que la mascota exista
        Mascota mascota = mascotaRepository.findById(agenda.getMascota().getId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        agenda.setBanador(banador);
        agenda.setMascota(mascota);

        return agendaRepository.save(agenda);
    }

    public List<Agenda> listarPorBanador(Long banadorId) {
        Funcionario banador = funcionarioRepository.findById(banadorId)
            .orElseThrow(() -> new RuntimeException("Bañador no encontrado"));
        return agendaRepository.findByBanador(banador);
    }

    public List<Agenda> listarPorFecha(LocalDate fecha) {
        return agendaRepository.findByFecha(fecha);
    }

    public List<Agenda> listarAgendaBanador(Long banadorId, LocalDate fecha) {
        return agendaRepository.findByBanadorIdAndFecha(banadorId, fecha);
    }

    public long contarBaniosPorDia(LocalDate fecha) {
        return agendaRepository.countActivosByFecha(fecha);
    }

    public boolean hayDisponibilidad(LocalDate fecha) {
        return agendaRepository.countActivosByFecha(fecha) < MAX_BANOS_POR_DIA;
    }

    public int cuposDisponibles(LocalDate fecha) {
        return MAX_BANOS_POR_DIA - (int) agendaRepository.countActivosByFecha(fecha);
    }
}
