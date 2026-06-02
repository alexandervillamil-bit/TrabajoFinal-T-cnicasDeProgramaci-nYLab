package com.panaderia.controller;

import com.panaderia.model.Venta;
import com.panaderia.model.EstadoVenta;
import com.panaderia.service.VentaService;
import com.panaderia.service.ClienteService;
import com.panaderia.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    public VentaController(VentaService ventaService,
                           ClienteService clienteService,
                           ProductoService productoService) {
        this.ventaService = ventaService;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listar());
        return "ventas/list";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("estados", EstadoVenta.values());
        return "ventas/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Venta venta, RedirectAttributes redirectAttributes) {

        try {
            venta.setFecha(LocalDate.now());
            ventaService.guardar(venta);

            redirectAttributes.addFlashAttribute(
                    "mensajeExito",
                    "Venta registrada correctamente 🎉"
            );

        } catch (RuntimeException e) {

            redirectAttributes.addFlashAttribute(
                    "mensajeError",
                    e.getMessage()
            );
        }

        return "redirect:/ventas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return "redirect:/ventas";
    }
}