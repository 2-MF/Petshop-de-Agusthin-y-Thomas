package com.petshop.controller;

import com.petshop.model.Cliente;
import com.petshop.model.Mascota;
import com.petshop.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Cliente cliente) {
        try {
            return ResponseEntity.ok(clienteService.registrarCliente(cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{clienteId}/mascotas")
    public ResponseEntity<?> registrarMascota(@PathVariable Long clienteId, @RequestBody Mascota mascota) {
        try {
            return ResponseEntity.ok(clienteService.registrarMascota(clienteId, mascota));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{id}/mascotas")
    public List<Mascota> listarMascotas(@PathVariable Long id) {
        return clienteService.listarMascotasPorCliente(id);
    }
}
