package com.example.cja_inventario.interfaces;

import com.example.cja_inventario.models.Diagnostico;
import com.example.cja_inventario.models.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IDiagnostico extends CrudRepository<Diagnostico, Integer> {

    Optional<Diagnostico> findById(int id);
    List<Diagnostico> findByEquipoUsuario(Usuario cliente);

}
