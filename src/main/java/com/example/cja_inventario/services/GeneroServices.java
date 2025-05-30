package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.I_generoServices;
import com.example.cja_inventario.interfaces.I_generos;
import com.example.cja_inventario.models.Generos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneroServices implements I_generoServices {
    @Autowired
    private I_generos date;
    @Override
    public List<Generos> listar() {
        return (List<Generos>) date.findAll();
    }

    @Override
    public Optional<Generos> listarId(int id) {

        return date.findById(id);
    }
    @Override
    public List<Generos> listarIdCategoria(int id){

        List<Generos> generos =listar();
        List<Generos> modelos=new ArrayList<>();
        for (Generos gen:generos) {
            if (gen.getCategoria().getId()==id){
                modelos.add(gen);
            }

        }
        return modelos;
    }
    @Override
    public Boolean validarDuplicados(Generos genero){
        List<Generos> generos=listar();
        for (Generos gen:generos) {
            String nombreMed = gen.getNombre().replaceAll("\\s+", "");
            String nombreMedida = genero.getNombre().replaceAll("\\s+", "");

            if (nombreMed.equalsIgnoreCase(nombreMedida) && genero.getCategoria().getId().equals(gen.getCategoria().getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int save(Generos gen) {
        int res=0;
        Generos generos=date.save(gen);
        if(!generos.equals(null)){
            res=1;

        }
        return res;
    }

    @Override
    public void delete(int id) {
        date.deleteById(id);

    }


}
