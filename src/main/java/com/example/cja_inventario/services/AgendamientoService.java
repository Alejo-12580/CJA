package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.IAgendamientoService;
import com.example.cja_inventario.interfaces.IAgendamiento;
import com.example.cja_inventario.models.Agendamiento;
import com.example.cja_inventario.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgendamientoService implements IAgendamientoService {

    @Autowired
    private IAgendamiento repository;

    @Override
    public List<Agendamiento> listar() {
        Iterable<Agendamiento> iterable = repository.findAll();
        List<Agendamiento> lista = new ArrayList<>();
        iterable.forEach(lista::add);
        return lista;
    }

    @Override
    public Optional<Agendamiento> ListarID(int id) {
        return repository.findById(id);
    }

    @Override
    public int save(Agendamiento a) {
        Agendamiento agendamiento = repository.save(a);
        return agendamiento.getId(); // Devuelve el ID del agendamiento guardado
    }

    @Override
    public void delete(int id) {
        repository.deleteById(id);
    }

    @Override
    public List<Agendamiento> ListarAgendamientosPorCliente(Usuario cliente) {
        return repository.findByUsuario(cliente);
    }
}
