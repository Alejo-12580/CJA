package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.Agendamiento;
import com.example.cja_inventario.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IAgendamientoService {
    List<Agendamiento> listar();
    Optional<Agendamiento> ListarID(int id);
    int save(Agendamiento a);
    void delete(int id);
    List<Agendamiento> ListarAgendamientosPorCliente(Usuario cliente);
}
