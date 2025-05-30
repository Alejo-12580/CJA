package com.example.cja_inventario.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String imagen;

    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;

    @OneToMany(mappedBy = "categoria")
    private List<Medidas> medida;

    @OneToMany(mappedBy = "categoria")
    private List<Generos> generos;



    public Categoria() {

    }

    public Categoria(Integer id, String nombre, String imagen, List<Producto> productos, List<Medidas> medida, List<Generos> generos) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.productos = productos;
        this.medida = medida;
        this.generos = generos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Medidas> getMedida() {
        return medida;
    }

    public void setMedida(List<Medidas> medida) {
        this.medida = medida;
    }

    public List<Generos> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Generos> generos) {
        this.generos = generos;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", imagen='" + imagen + '\'' +
                ", productos=" + productos +
                ", medida=" + medida +
                ", generos=" + generos +
                '}';
    }
}
