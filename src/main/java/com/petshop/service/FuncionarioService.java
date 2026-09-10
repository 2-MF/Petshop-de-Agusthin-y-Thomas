package com.petshop.service;

import com.petshop.model.Funcionario;
import com.petshop.model.RolFuncionario;
import com.petshop.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    // Solo el ADMINISTRADOR puede registrar nuevos funcionarios
    @Transactional
    public Funcionario registrarFuncionario(Funcionario nuevo, Long adminId) {
        Funcionario admin = funcionarioRepository.findById(adminId)
            .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        if (admin.getRol() != RolFuncionario.ADMINISTRADOR) {
            throw new RuntimeException("Solo el administrador puede registrar nuevos funcionarios");
        }

        if (funcionarioRepository.existsByEmail(nuevo.getEmail())) {
            throw new RuntimeException("Ya existe un funcionario con ese email");
        }

        return funcionarioRepository.save(nuevo);
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }

    public List<Funcionario> listarPorRol(RolFuncionario rol) {
        return funcionarioRepository.findByRol(rol);
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Funcionario no encontrado"));
    }
}
