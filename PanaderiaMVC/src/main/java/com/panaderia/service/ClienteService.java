package com.panaderia.service;

import com.panaderia.model.Cliente;
import com.panaderia.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {

        Cliente cliente = clienteRepository.findById(id).orElse(null);

        if (cliente == null) return;

        if (cliente.getVentas() != null && !cliente.getVentas().isEmpty()) {
            throw new RuntimeException(
                    "No puedes eliminar este cliente porque tiene ventas registradas"
            );
        }

        clienteRepository.deleteById(id);
    }
}