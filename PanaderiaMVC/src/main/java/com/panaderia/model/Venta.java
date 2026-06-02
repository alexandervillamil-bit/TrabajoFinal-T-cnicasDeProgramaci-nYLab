package com.panaderia.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    private Integer cantidad;

    private LocalDate fecha;

    private Double total;

    @Enumerated(EnumType.STRING)
    private EstadoVenta estado;

    public Venta() {}

    public Venta(Long id, Cliente cliente, Producto producto, Integer cantidad,
                 LocalDate fecha, Double total, EstadoVenta estado) {
        this.id = id;
        this.cliente = cliente;
        this.producto = producto;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    // GETTERS Y SETTERS

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Producto getProducto() { return producto; }

    public void setProducto(Producto producto) { this.producto = producto; }

    public Integer getCantidad() { return cantidad; }

    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getTotal() { return total; }

    public void setTotal(Double total) { this.total = total; }

    public EstadoVenta getEstado() { return estado; }

    public void setEstado(EstadoVenta estado) { this.estado = estado; }
}