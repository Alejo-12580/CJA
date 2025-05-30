package com.example.cja_inventario.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name ="Pagos ")
public class Pagos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private Double total;



    public Pagos() {
    }
// contructor //


    public Pagos(Integer id, Double total) {
        this.id = id;
        this.total = total;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "Pagos{" +
                "id=" + id +
                ", total=" + total +
                '}';
    }
}
