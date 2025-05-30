package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.IVentasService;
import com.example.cja_inventario.interfaces.IVentas;
import com.example.cja_inventario.models.Usuario;
import com.example.cja_inventario.models.Ventas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentasServices implements IVentasService {
    @Autowired
    private IVentas data;

    @Override
    public List<Ventas> listar() {
        return (List<Ventas>) data.findAll();
    }

    @Override
    public Optional<Ventas> listarId(int id) {
        return Optional.empty();
    }

    @Override
    public int save(Ventas user) {
        int res=0;
        Ventas us=data.save(user);
        if(!us.equals(null)){
            res=1;

        }
        return res;
    }
    @Override
    public void delete(int id) {

    }
}
