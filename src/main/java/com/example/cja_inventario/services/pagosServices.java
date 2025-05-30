package com.example.cja_inventario.services;


import com.example.cja_inventario.interfaceServices.IpagosService;

import com.example.cja_inventario.interfaces.Ipagos;
import com.example.cja_inventario.models.Pagos;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class pagosServices implements IpagosService {

   @Autowired
    private Ipagos data;

    @Override
    public List<Pagos> listar() {
        return (List<Pagos>)data.findAll();
    }

    @Override
    public Optional<Pagos> listarId(int id) {
        return Optional.empty();
    }

    @Override
    public int save(Pagos p) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }
}