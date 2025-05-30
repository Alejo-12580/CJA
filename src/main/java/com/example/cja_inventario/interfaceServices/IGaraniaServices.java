package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.Garantia;

import java.util.List;
import java.util.Optional;

public interface IGaraniaServices {
    public List<Garantia> listar();
    public Optional<Garantia> listarId(int id);
    public int save(Garantia prod);
    public void delete(int id);
}

