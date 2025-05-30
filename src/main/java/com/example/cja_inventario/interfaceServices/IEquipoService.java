package com.example.cja_inventario.interfaceServices;


import com.example.cja_inventario.models.Equipo;
import com.example.cja_inventario.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IEquipoService {
    List<Equipo> Listar();
    Optional<Equipo> ListarID(int id);
    int save(Equipo e);
    void delete(int id);

    List<Equipo> ListarEquiposPorUsuario(Usuario usuario); // Nuevo método


}
