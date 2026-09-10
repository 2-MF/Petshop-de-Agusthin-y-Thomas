package com.petshop.controller;

import com.petshop.model.Funcionario;
import com.petshop.model.RolFuncionario;
import com.petshop.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping("/registrar/{adminId}")
    public ResponseEntity<?> registrar(@PathVariable Long adminId, @RequestBody Funcionario funcionario) {
        try {
            return ResponseEntity.ok(funcionarioService.registrarFuncionario(funcionario, adminId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Funcionario> listar() {
        return funcionarioService.listarTodos();
    }

    @GetMapping("/rol/{rol}")
    public List<Funcionario> listarPorRol(@PathVariable RolFuncionario rol) {
        return funcionarioService.listarPorRol(rol);
    }
}
