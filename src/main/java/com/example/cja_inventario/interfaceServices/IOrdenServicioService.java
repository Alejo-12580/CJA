package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.OrdenServicio;
import com.example.cja_inventario.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IOrdenServicioService {
    List<OrdenServicio> Listar();
    Optional<OrdenServicio> ListarID(int id);
    int save(OrdenServicio o);
    void delete(int id);
    List<OrdenServicio> ListarOrdenesPorCliente(Usuario cliente);
}
