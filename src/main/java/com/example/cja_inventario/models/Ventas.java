package com.example.cja_inventario.models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "venta")
public class Ventas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate fechaVenta;
    private double sub_total;
    private double valor_iba;
    private String tipoPago;
    private Date fechaentrega;

    private double total;

    @OneToMany(mappedBy = "venta")
    private List<DetalleVenta> detalleVenta;

    public Ventas() {
    }

    public Ventas(Integer id, LocalDate fechaVenta, double sub_total, double valor_iba, String tipoPago, Date fechaentrega, double total, List<DetalleVenta> detalleVenta) {
        this.id = id;
        this.fechaVenta = fechaVenta;
        this.sub_total = sub_total;
        this.valor_iba = valor_iba;
        this.tipoPago = tipoPago;
        this.fechaentrega = fechaentrega;
        this.total = total;
        this.detalleVenta = detalleVenta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getValor_iba() {
        return valor_iba;
    }

    public void setValor_iba(double valor_iba) {
        this.valor_iba = valor_iba;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Date getFechaentrega() {
        return fechaentrega;
    }

    public void setFechaentrega(Date fechaentrega) {
        this.fechaentrega = fechaentrega;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<DetalleVenta> getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(List<DetalleVenta> detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    @Override
    public String toString() {
        return "Ventas{" +
                "id=" + id +
                ", fechaVenta=" + fechaVenta +
                ", sub_total=" + sub_total +
                ", valor_iba=" + valor_iba +
                ", tipoPago='" + tipoPago + '\'' +
                ", fechaentrega=" + fechaentrega +
                ", total=" + total +
                ", detalleVenta=" + detalleVenta +
                '}';
    }
}