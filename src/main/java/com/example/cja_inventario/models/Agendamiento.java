package com.example.cja_inventario.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "agendamiento")
public class Agendamiento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String confirmarDomi;
    private LocalDate fechaServicio;
    private LocalTime horaServicio;
    private String direccion;
    private String pago;

    @ManyToOne
    private Diagnostico diagnostico;

    public Agendamiento() {
    }

    public Agendamiento(int id, String confirmarDomi, LocalDate fechaServicio, LocalTime horaServicio, String direccion, String pago) {
        this.id = id;
        this.confirmarDomi = confirmarDomi;
        this.fechaServicio = fechaServicio;
        this.horaServicio = horaServicio;
        this.direccion = direccion;
        this.pago = pago;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConfirmarDomi() {
        return confirmarDomi;
    }

    public void setConfirmarDomi(String confirmarDomi) {
        this.confirmarDomi = confirmarDomi;
    }

    public LocalDate getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(LocalDate fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public LocalTime getHoraServicio() {
        return horaServicio;
    }

    public void setHoraServicio(LocalTime horaServicio) {
        this.horaServicio = horaServicio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    @Override
    public String toString() {
        return "Agendamiento{" +
                "id=" + id +
                ", confirmarDomi='" + confirmarDomi + '\'' +
                ", fechaServicio=" + fechaServicio +
                ", horaServicio=" + horaServicio +
                ", direccion='" + direccion + '\'' +
                ", pago='" + pago + '\'' +
                ", diagnostico=" + diagnostico +
                '}';
    }
}
