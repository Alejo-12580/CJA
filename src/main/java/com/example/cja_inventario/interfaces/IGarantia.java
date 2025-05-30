package com.example.cja_inventario.interfaces;


import com.example.cja_inventario.models.Garantia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGarantia extends CrudRepository<Garantia, Integer > {
}
