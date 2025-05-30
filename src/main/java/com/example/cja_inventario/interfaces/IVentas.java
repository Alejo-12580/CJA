package com.example.cja_inventario.interfaces;


import com.example.cja_inventario.models.Ventas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVentas extends CrudRepository <Ventas,Integer> {

}