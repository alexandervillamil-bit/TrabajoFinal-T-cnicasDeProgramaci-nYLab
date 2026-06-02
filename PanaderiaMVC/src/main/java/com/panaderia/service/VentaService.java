package com.panaderia.service;

import com.panaderia.model.Producto;
import com.panaderia.model.Venta;
import com.panaderia.repository.ProductoRepository;
import com.panaderia.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    public VentaService(VentaRepository ventaRepository,
                        ProductoRepository productoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta guardar(Venta venta) {

        if (venta.getProducto() == null || venta.getCantidad() == null) {
            venta.setSubtotal(0.0);
            venta.setTotal(0.0);
            return ventaRepository.save(venta);
        }

        Producto producto = venta.getProducto();
        int cantidad = venta.getCantidad();

        if (producto.getStock() < cantidad) {
            throw new RuntimeException(
                    "No hay suficiente stock para: " + producto.getNombre()
            );
        }

        double subtotal = producto.getPrecio() * cantidad;

        venta.setSubtotal(subtotal);
        venta.setTotal(subtotal);

        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);

        return ventaRepository.save(venta);
    }

    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }
}