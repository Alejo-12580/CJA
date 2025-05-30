package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.Ventas;

import java.util.List;
import java.util.Optional;

public interface IVentasService {
    public List<Ventas> listar();
    public Optional<Ventas> listarId(int id);
    public int save(Ventas p);
    public void delete(int id);
}