package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.IGaraniaServices;
import com.example.cja_inventario.interfaces.IGarantia;
import com.example.cja_inventario.models.Garantia;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class GarantiaServices implements IGaraniaServices{
    @Autowired
    private IGarantia data;

    @Override
    public List<Garantia> listar() {
        return (List<Garantia>) data.findAll();
    }

    @Override
    public Optional<Garantia> listarId(int id) {
        return Optional.empty();
    }

    @Override
    public int save(Garantia p) {
        return 0;
    }

    @Override
    public void delete(int id) {

    }
}

