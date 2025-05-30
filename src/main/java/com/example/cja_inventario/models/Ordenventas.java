package com.example.cja_inventario.models;

import jakarta.persistence.*;

@Entity
@Table(name="Ordenventas")
public class Ordenventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double cantidad;
    @ManyToOne
    private Ventas Ventas;
    @ManyToOne
    private Producto productos;

    public Ordenventas() {
    }

    public Ordenventas(Integer id, Double cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Ventas getVentas() {
        return Ventas;
    }

    public void setVentas(Ventas ventas) {
        this.Ventas = ventas;
    }

    public Producto getProducto() {
        return productos;
    }

    public void setProducto(Producto producto) {
        this.productos = producto;
    }


}