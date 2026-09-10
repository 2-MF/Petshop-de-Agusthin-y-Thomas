package com.petshop.service;

import com.petshop.model.Cliente;
import com.petshop.model.Mascota;
import com.petshop.repository.ClienteRepository;
import com.petshop.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Transactional
    public Cliente registrarCliente(Cliente cliente) {
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente con ese email");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Mascota registrarMascota(Long clienteId, Mascota mascota) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.addMascota(mascota);
        return mascotaRepository.save(mascota);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public List<Mascota> listarMascotasPorCliente(Long clienteId) {
        return mascotaRepository.findByClienteId(clienteId);
    }
}
