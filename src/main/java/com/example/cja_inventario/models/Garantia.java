package com.example.cja_inventario.models;


import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Garantia")
public class Garantia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date fecha_Incio;
    private Date fecha_Final;

    public Garantia() {

    }

    public Garantia(Integer id, Date fecha_Incio, Date fecha_Final) {
        this.id = id;
        this.fecha_Incio = fecha_Incio;
        this.fecha_Final = fecha_Final;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha_Incio() {
        return fecha_Incio;
    }

    public void setFecha_Incio(Date fecha_Incio) {
        this.fecha_Incio = fecha_Incio;
    }

    public Date getFecha_Final() {
        return fecha_Final;
    }

    public void setFecha_Final(Date fecha_Final) {
        this.fecha_Final = fecha_Final;
    }

    @Override
    public String toString() {
        return "Garantia{" +
                "id=" + id +
                ", fecha_Incio=" + fecha_Incio +
                ", fecha_Final=" + fecha_Final +
                '}';
    }
}