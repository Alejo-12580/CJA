package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.I_DetalleVentaService;
import com.example.cja_inventario.interfaces.I_DetalleVenta;
import com.example.cja_inventario.models.DetalleVenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaServices implements I_DetalleVentaService {

   @Autowired
   private I_DetalleVenta date;

    @Override
    public List<DetalleVenta> listar() {

        return (List<DetalleVenta>) date.findAll();
    }

    @Override
    public Optional<DetalleVenta> listarId(int id) {
        return date.findById(id);
    }

    @Override
    public int save(DetalleVenta dt) {
        int res=0;
        DetalleVenta DetalleVenta =date.save(dt);
        if(!DetalleVenta.equals(null)){
            res=1;

        }
        return res;
    }

    @Override
    public void delete(int id) {
        date.deleteById(id);
    }

}


