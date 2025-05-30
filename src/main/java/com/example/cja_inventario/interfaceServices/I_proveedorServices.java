package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.Proveedor;

import java.util.List;
import java.util.Optional;

public interface I_proveedorServices {

    List<Proveedor> listar();

    Optional<Proveedor> listarId(int id);

    int save(Proveedor prov);

    void delete(int id);

    Boolean ValidarDuplicados(Proveedor prov);
}
