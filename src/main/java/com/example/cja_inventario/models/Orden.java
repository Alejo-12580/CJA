package com.example.cja_inventario.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="orden")
public class Orden {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	//@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDate fechaCreacion;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDate fechaRecibida;
	private double total;
	/*
	@ManyToOne
	private Usuario usuario;
*/
	@OneToMany(mappedBy = "orden")
	private List<DetalleOrden> detalle;

	@ManyToOne
	private Proveedor proveedor;

	public Orden() {
		
	}

	public Orden(Integer id, LocalDate fechaCreacion, LocalDate fechaRecibida, double total, List<DetalleOrden> detalle, Proveedor proveedor) {
		this.id = id;
		this.fechaCreacion = fechaCreacion;
		this.fechaRecibida = fechaRecibida;
		this.total = total;
		this.detalle = detalle;
		this.proveedor = proveedor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDate getFechaRecibida() {
		return fechaRecibida;
	}

	public void setFechaRecibida(LocalDate fechaRecibida) {
		this.fechaRecibida = fechaRecibida;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public List<DetalleOrden> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<DetalleOrden> detalle) {
		this.detalle = detalle;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

}
