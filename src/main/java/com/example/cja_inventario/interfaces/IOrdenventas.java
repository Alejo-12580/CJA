package com.example.cja_inventario.interfaces;

import com.example.cja_inventario.models.Ordenventas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenventas  extends CrudRepository<Ordenventas, Integer > {
}
