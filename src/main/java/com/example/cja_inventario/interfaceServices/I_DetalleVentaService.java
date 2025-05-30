package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.DetalleVenta;

import java.util.List;
import java.util.Optional;

public interface I_DetalleVentaService {
    public List<DetalleVenta> listar();
    public Optional<DetalleVenta> listarId(int id);
    public int save(DetalleVenta dt);
    public void delete(int id);
}
