package com.example.cja_inventario.interfaces;

import com.example.cja_inventario.models.Pagos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ipagos extends CrudRepository<Pagos, Integer > {
}

