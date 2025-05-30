package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.I_productoServices;
import com.example.cja_inventario.interfaces.I_Producto;
import com.example.cja_inventario.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
 public class ProductoServices implements I_productoServices{

    @Autowired
    private I_Producto data;


    @Override
    public List<Producto> listar() {

        return (List<Producto>) data.findAll();
    }

    @Override
    public Optional<Producto> listarId(int id) {

         return data.findById(id);
    }

    @Override
    public int save(Producto prod) {
        int res=0;
        Producto producto=data.save(prod);
        if(!producto.equals(null)){
            res=1;

        }
        return res;
    }

    @Override
    public void delete(int id) {
        data.deleteById(id);
    }

    @Override
    public Boolean ValidarDuplicados(Producto prod) {
        List<Producto> productos = listar();
        for (Producto producto : productos) {
            if (prod.getNombre().replaceAll("\\s+", "").equalsIgnoreCase(producto.getNombre().replaceAll("\\s+", "")) &&
                    prod.getDisenho().replaceAll("\\s+", "").equalsIgnoreCase(producto.getDisenho().replaceAll("\\s+", "")) &&
                    prod.getGenero_modelo().replaceAll("\\s+", "").equalsIgnoreCase(producto.getGenero_modelo().replaceAll("\\s+", "")) &&
                    prod.getTalla_capacidad().replaceAll("\\s+", "").equalsIgnoreCase(producto.getTalla_capacidad().replaceAll("\\s+", "")) &&
                    prod.getCategoria().getId().equals(producto.getCategoria().getId()) &&
                    prod.getEstado().replaceAll("\\s+", "").equalsIgnoreCase(producto.getEstado().replaceAll("\\s+", ""))) {
                return true;
            }
        }
        return false;
    }
}
