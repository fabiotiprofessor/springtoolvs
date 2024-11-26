package com.vsmanutencoes.sistemaweb.service;

import com.vsmanutencoes.sistemaweb.models.Cliente;
import com.vsmanutencoes.sistemaweb.repositories.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    // Método para buscar múltiplos clientes
    public List<Cliente> buscarClientesPorIds(List<Long> clienteIds) {
        return clienteRepositorio.findAllById(clienteIds);
    }

    // Método para buscar um único cliente por ID, retornando um Optional
    public Optional<Cliente> buscarClientePorId(Long clienteId) {
        return clienteRepositorio.findById(clienteId);
    }

    public List<Cliente> listarTodosClientes() {
        return clienteRepositorio.findAll();
    }

    public void salvarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }

    public void inativarCliente(Long id) {
        Optional<Cliente> clienteOpt = clienteRepositorio.findById(id);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            cliente.setAtivo(false);
            clienteRepositorio.save(cliente);
        }
    }    
}
