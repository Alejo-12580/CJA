package com.example.cja_inventario.interfaces;

import com.example.cja_inventario.models.DetalleOrden;
import com.example.cja_inventario.models.DetalleVenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface I_DetalleVenta extends CrudRepository<DetalleVenta, Integer>{
}


