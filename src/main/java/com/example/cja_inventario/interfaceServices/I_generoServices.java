package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.Generos;

import java.util.List;
import java.util.Optional;

public interface I_generoServices {
    public List<Generos> listar();
    public Optional<Generos> listarId(int id);

    List<Generos> listarIdCategoria(int id);

    Boolean validarDuplicados(Generos genero);

    public int save(Generos gen);
    public void delete(int id);
}
