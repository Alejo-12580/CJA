package com.example.cja_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cja_inventario.models.Producto;

@Repository
public interface productoRepository  extends JpaRepository<Producto, Long> {

}
