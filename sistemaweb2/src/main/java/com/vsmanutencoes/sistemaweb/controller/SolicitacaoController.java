package com.vsmanutencoes.sistemaweb.controller;

import com.vsmanutencoes.sistemaweb.models.SolicitacaoOrcamento;
import com.vsmanutencoes.sistemaweb.service.ClienteService;
import com.vsmanutencoes.sistemaweb.service.EquipamentoService;
import com.vsmanutencoes.sistemaweb.service.SolicitacaoOrcamentoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoOrcamentoService solicitacaoOrcamentoService;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private EquipamentoService equipamentoService;

    @GetMapping
    public String listarSolicitacoes(Model model) {
        model.addAttribute("solicitacoes", solicitacaoOrcamentoService.listarTodasSolicitacoes());
        return "solicitacoes";
    }

    @GetMapping("/new")
    public String novoFormulario(Model model) {
        model.addAttribute("solicitacao", new SolicitacaoOrcamento());
        model.addAttribute("clientes", clienteService.listarTodosClientes());
        model.addAttribute("equipamentos", equipamentoService.listarTodosEquipamentos());
        return "solicitacao-form";
    }

    @PostMapping("/save")
    public String salvarSolicitacao(
        @ModelAttribute("solicitacao") SolicitacaoOrcamento solicitacao,
        @RequestParam("clienteId") Long clienteId,
        @RequestParam("equipamentoIds") List<Long> equipamentoIds
    ) {
    	LocalDateTime now = LocalDateTime.now();
        solicitacao.setData(now.toLocalDate());
        solicitacao.setHora(now.toLocalTime());
        solicitacaoOrcamentoService.salvarSolicitacao(solicitacao, clienteId, equipamentoIds);
        return "redirect:/solicitacoes";
    }

    @GetMapping("/edit/{id}")
    public String editarSolicitacaoForm(@PathVariable("id") Long id, Model model) {
        Optional<SolicitacaoOrcamento> solicitacao = solicitacaoOrcamentoService.buscarSolicitacaoPorId(id);
        if (solicitacao.isPresent()) {
            model.addAttribute("solicitacao", solicitacao.get());
            model.addAttribute("clientes", clienteService.listarTodosClientes());
            model.addAttribute("equipamentos", equipamentoService.listarTodosEquipamentos());
            return "solicitacao-form";
        }
        return "redirect:/solicitacoes";
    }

    @GetMapping("/delete/{id}")
    public String excluirSolicitacao(@PathVariable("id") Long id) {
        solicitacaoOrcamentoService.excluirSolicitacao(id);
        return "redirect:/solicitacoes";
    }
}
