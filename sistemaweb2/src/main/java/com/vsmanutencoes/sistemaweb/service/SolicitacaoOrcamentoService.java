package com.vsmanutencoes.sistemaweb.service;

import com.vsmanutencoes.sistemaweb.models.SolicitacaoOrcamento;
import com.vsmanutencoes.sistemaweb.models.Cliente;
import com.vsmanutencoes.sistemaweb.models.Equipamento;
import com.vsmanutencoes.sistemaweb.repositories.SolicitacaoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SolicitacaoOrcamentoService {

    @Autowired
    private SolicitacaoRepositorio solicitacaoRepositorio;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EquipamentoService equipamentoService;

    public void salvarSolicitacao(SolicitacaoOrcamento solicitacao, Long clienteId, List<Long> equipamentoIds) {
        // Busca e define o cliente selecionado
        Optional<Cliente> cliente = clienteService.buscarClientePorId(clienteId);
        cliente.ifPresent(solicitacao::setCliente);

        // Busca e define a lista de equipamentos selecionados
        List<Equipamento> equipamentos = equipamentoService.buscarEquipamentosPorIds(equipamentoIds);
        solicitacao.setEquipamentos(equipamentos);

        solicitacaoRepositorio.save(solicitacao);
    }

    public List<SolicitacaoOrcamento> listarTodasSolicitacoes() {
        return solicitacaoRepositorio.findAll();
    }

    public Optional<SolicitacaoOrcamento> buscarSolicitacaoPorId(Long id) {
        return solicitacaoRepositorio.findById(id);
    }

    public void excluirSolicitacao(Long id) {
        solicitacaoRepositorio.deleteById(id);
    }
}
