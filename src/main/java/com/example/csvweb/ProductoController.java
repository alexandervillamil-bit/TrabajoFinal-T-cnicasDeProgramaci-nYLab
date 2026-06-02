package com.example.csvweb;

import com.opencsv.CSVReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class ProductoController {

    @GetMapping("/")
    public String inicio(Model model) throws Exception {
        List<Producto> productos = new ArrayList<>();

        CSVReader reader = new CSVReader(
                new InputStreamReader(
                        getClass().getResourceAsStream("/datos.csv"),
                        java.nio.charset.StandardCharsets.UTF_8
                )
        );

        String[] linea;
        reader.readNext();

        while ((linea = reader.readNext()) != null) {
            String nombre = linea[0];
            double precio = Double.parseDouble(linea[1].trim());
            productos.add(new Producto(nombre, precio));
        }

        double promedio = productos.stream()
                .mapToDouble(Producto::getPrecio)
                .average()
                .orElse(0);
        Producto masCostoso = productos.stream()
                .max(Comparator.comparingDouble(Producto::getPrecio))
                .orElse(null);

        Producto masBarato = productos.stream()
                .min(Comparator.comparingDouble(Producto::getPrecio))
                .orElse(null);

        model.addAttribute("productos", productos);
        model.addAttribute("promedio", promedio);
        model.addAttribute("masCostoso", masCostoso);
        model.addAttribute("masBarato", masBarato);

        return "index";
    }
}