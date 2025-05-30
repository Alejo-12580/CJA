package com.example.cja_inventario.interfaces;

import com.example.cja_inventario.models.Agendamiento;
import com.example.cja_inventario.models.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAgendamiento extends CrudRepository<Agendamiento, Integer> {

    // Consulta personalizada para obtener Agendamientos por Usuario
    @Query("SELECT a FROM Agendamiento a INNER JOIN a.diagnostico d INNER JOIN d.equipo e INNER JOIN e.usuario u WHERE u = ?1")
    List<Agendamiento> findByUsuario(Usuario usuario);
}
