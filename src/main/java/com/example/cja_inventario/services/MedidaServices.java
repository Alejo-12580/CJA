package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.I_medidaServices;
import com.example.cja_inventario.interfaces.I_medidas;
import com.example.cja_inventario.models.Medidas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedidaServices implements I_medidaServices {
    @Autowired
    private I_medidas date;
    @Override
    public List<Medidas> listar() {
        return (List<Medidas>) date.findAll();
    }

    @Override
    public Optional<Medidas> listarId(int id) {

        return date.findById(id);
    }
    @Override
    public List<Medidas> listarIdCategoria(int id){

        List<Medidas> medidas =listar();
        List<Medidas> medida=new ArrayList<>();
        for (Medidas med:medidas) {
            if (med.getCategoria().getId()==id){
                medida.add(med);
            }

        }
        return medida;
    }

    @Override
    public Boolean validarDuplicados(Medidas medida){
        List<Medidas> medidas=listar();
        for (Medidas med:medidas) {
            String nombreMed = med.getNombre().replaceAll("\\s+", "");
            String nombreMedida = medida.getNombre().replaceAll("\\s+", "");

            if (nombreMed.equalsIgnoreCase(nombreMedida) && med.getCategoria().getId().equals(medida.getCategoria().getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int save(Medidas med) {
        int res=0;
        Medidas medidas=date.save(med);
        if(!medidas.equals(null)){
            res=1;

        }
        return res;
    }

    @Override
    public void delete(int id) {
        date.deleteById(id);

    }
}
