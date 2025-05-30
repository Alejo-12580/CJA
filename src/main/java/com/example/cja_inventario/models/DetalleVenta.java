package com.example.cja_inventario.models;

import jakarta.persistence.*;

@Entity
@Table(name="detalleVenta")
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer cantidad;

    @ManyToOne
    private Producto productos;
    @ManyToOne
    private Ventas venta;


    public DetalleVenta() {

    }

    public DetalleVenta(Integer id, Integer cantidad, Producto productos, Ventas venta) {
        this.id = id;
        this.cantidad = cantidad;
        this.productos = productos;
        this.venta = venta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProductos() {
        return productos;
    }

    public void setProductos(Producto productos) {
        this.productos = productos;
    }

    public Ventas getVenta() {
        return venta;
    }

    public void setVenta(Ventas venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", productos=" + productos +
                ", venta=" + venta +
                '}';
    }
}
