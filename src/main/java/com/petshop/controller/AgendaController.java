package com.petshop.controller;

import com.petshop.model.Agenda;
import com.petshop.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public ResponseEntity<?> agendar(@RequestBody Agenda agenda) {
        try {
            return ResponseEntity.ok(agendaService.agendar(agenda));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/banador/{banadorId}")
    public List<Agenda> listarPorBanador(@PathVariable Long banadorId) {
        return agendaService.listarPorBanador(banadorId);
    }

    @GetMapping("/fecha/{fecha}")
    public List<Agenda> listarPorFecha(@PathVariable LocalDate fecha) {
        return agendaService.listarPorFecha(fecha);
    }

    @GetMapping("/banador/{banadorId}/fecha/{fecha}")
    public List<Agenda> listarAgendaBanador(@PathVariable Long banadorId, @PathVariable LocalDate fecha) {
        return agendaService.listarAgendaBanador(banadorId, fecha);
    }

    @GetMapping("/disponibilidad/{fecha}")
    public ResponseEntity<?> verificarDisponibilidad(@PathVariable LocalDate fecha) {
        boolean disponible = agendaService.hayDisponibilidad(fecha);
        int cupos = agendaService.cuposDisponibles(fecha);
        return ResponseEntity.ok(Map.of(
            "fecha", fecha,
            "disponible", disponible,
            "cuposDisponibles", cupos,
            "maximoPorDia", 10
        ));
    }
}
