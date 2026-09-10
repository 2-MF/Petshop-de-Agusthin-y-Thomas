package com.petshop.controller;

import com.petshop.model.EstadoSolicitud;
import com.petshop.model.Solicitud;
import com.petshop.model.TipoServicio;
import com.petshop.service.SolicitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private SolicitudService solicitudService;

    @PostMapping
    public ResponseEntity<?> crear(
            @RequestParam Long clienteId,
            @RequestParam Long mascotaId,
            @RequestParam TipoServicio tipoServicio,
            @RequestParam(required = false) String observaciones) {
        try {
            return ResponseEntity.ok(solicitudService.crearSolicitud(clienteId, mascotaId, tipoServicio, observaciones));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Solicitud> listarPorCliente(@PathVariable Long clienteId) {
        return solicitudService.listarPorCliente(clienteId);
    }

    @GetMapping
    public List<Solicitud> listarTodas() {
        return solicitudService.listarTodas();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestParam EstadoSolicitud estado) {
        try {
            return ResponseEntity.ok(solicitudService.cambiarEstado(id, estado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
