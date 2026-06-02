package com.panaderia.controller;

import com.panaderia.model.Cliente;
import com.panaderia.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Listar cliente
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listar());
        return "clientes/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    // Guardar cliente
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    // Eliminar cliente
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return "redirect:/clientes";
    }
}