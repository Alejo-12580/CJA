package com.example.cja_inventario.interfaces;

import com.example.cja_inventario.models.OrdenServicio;
import com.example.cja_inventario.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IOrdenServicio extends CrudRepository<OrdenServicio,Integer> {

    Optional<OrdenServicio> findById(int id);

    List<OrdenServicio> findByEquipoUsuario(Usuario cliente);

}
