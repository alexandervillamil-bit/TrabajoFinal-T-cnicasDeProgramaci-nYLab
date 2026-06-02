package com.panaderia.service;

import com.panaderia.model.Cliente;
import com.panaderia.model.Producto;
import com.panaderia.model.Venta;
import com.panaderia.repository.ClienteRepository;
import com.panaderia.repository.ProductoRepository;
import com.panaderia.repository.VentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    public VentaService(VentaRepository ventaRepository,
                        ProductoRepository productoRepository,
                        ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public Venta guardar(Venta venta) {

        if (venta.getProducto() == null ||
                venta.getCliente() == null ||
                venta.getCantidad() == null) {
            throw new RuntimeException("Faltan datos para la venta");
        }

        Producto producto = productoRepository.findById(venta.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Cliente cliente = clienteRepository.findById(venta.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        venta.setProducto(producto);
        venta.setCliente(cliente);

        int cantidad = venta.getCantidad();

        if (producto.getStock() == null || producto.getStock() < cantidad) {
            throw new RuntimeException("No hay suficiente stock");
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