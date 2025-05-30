package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.Orden;
import com.example.cja_inventario.models.OrdenServicio;
import com.example.cja_inventario.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface I_ordenServices {
    public List<Orden> listar();
    public Optional<Orden> listarId(int id);
    public int save(Orden ord);
    public void delete(int id);

    List<OrdenServicio> ListarOrdenesPorCliente(Usuario cliente);
}
