package com.example.cja_inventario.repository;

import com.example.cja_inventario.models.DetalleOrden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface detalleOrdenRepository extends JpaRepository<DetalleOrden,Long> {

}
