package com.example.cja_inventario.interfaceServices;

import com.example.cja_inventario.models.Medidas;

import java.util.List;
import java.util.Optional;

public interface I_medidaServices {

    public List<Medidas> listar();
    public Optional<Medidas> listarId(int id);

    List<Medidas> listarIdCategoria(int id);

    Boolean validarDuplicados(Medidas medida);

    public int save(Medidas med);
    public void delete(int id);
}
