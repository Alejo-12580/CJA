package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.Pagos;

import java.util.List;
import java.util.Optional;

public interface IpagosService {
    public List<Pagos> listar();
    public Optional<Pagos> listarId(int id);
    public int save(Pagos prod);
    public void delete(int id);
}

