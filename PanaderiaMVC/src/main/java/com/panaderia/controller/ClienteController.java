package com.panaderia.controller;

import com.panaderia.model.Cliente;
import com.panaderia.model.Venta;
import com.panaderia.service.ClienteService;
import com.panaderia.repository.VentaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final VentaRepository ventaRepository;

    public ClienteController(ClienteService clienteService,
                             VentaRepository ventaRepository) {
        this.clienteService = clienteService;
        this.ventaRepository = ventaRepository;
    }

    // LISTAR CLIENTES
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listar());
        return "clientes/list";
    }

    // NUEVO CLIENTE
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/form";
    }

    // GUARDAR CLIENTE
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }

    // ELIMINAR CLIENTE (CON MENSAJE)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes redirectAttributes) {
        try {
            clienteService.eliminar(id);

            redirectAttributes.addFlashAttribute(
                    "mensajeExito",
                    "Cliente eliminado correctamente"
            );

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    e.getMessage()
            );
        }

        return "redirect:/clientes";
    }

    // 🟢 HISTORIAL DEL CLIENTE
    @GetMapping("/{id}/historial")
    public String historial(@PathVariable Long id, Model model) {

        Cliente cliente = clienteService.buscarPorId(id);

        if (cliente == null) {
            return "redirect:/clientes";
        }

        List<Venta> ventas = ventaRepository.findByClienteId(id);

        double totalGeneral = ventas.stream()
                .mapToDouble(v -> v.getTotal() != null ? v.getTotal() : 0.0)
                .sum();

        model.addAttribute("cliente", cliente);
        model.addAttribute("ventas", ventas);
        model.addAttribute("totalGeneral", totalGeneral);

        return "clientes/historial";
    }
}