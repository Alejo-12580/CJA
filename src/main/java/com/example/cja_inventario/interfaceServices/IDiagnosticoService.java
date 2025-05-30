package com.example.cja_inventario.interfaceServices;


import com.example.cja_inventario.models.Diagnostico;
import com.example.cja_inventario.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface IDiagnosticoService {
    public List<Diagnostico>listar();
    public Optional<Diagnostico>listarID(int id);
    public int save(Diagnostico d);
    public void delete(int id);

    Optional<Diagnostico> ListarID(int id);
    List<Diagnostico> ListarDiagnosticosPorCliente(Usuario cliente);

}
