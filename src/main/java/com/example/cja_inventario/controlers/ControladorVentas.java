package com.example.cja_inventario.controlers;

import com.example.cja_inventario.interfaceServices.IVentasService;
import com.example.cja_inventario.interfaceServices.I_categoriaServices;
import com.example.cja_inventario.interfaceServices.I_ordenServices;
import com.example.cja_inventario.interfaceServices.I_productoServices;
import com.example.cja_inventario.models.Categoria;
import com.example.cja_inventario.models.Producto;
import com.example.cja_inventario.models.Ventas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping
@Controller
public class ControladorVentas {

    @Autowired
    private IVentasService service;

    @Autowired
    private I_ordenServices serviceOrd;
    @Autowired
    private I_categoriaServices serviceC;

    @Autowired
    private I_productoServices serviceProd;


    @GetMapping("/ordenpedi")
    public String listar(Model model) {
        List<Ventas> Venta = service.listar();
        model.addAttribute("Venta", Venta);
        return "administrador/ordenPedi";
    }

    @GetMapping("/newcompra")
    public String create(Model model) {
        model.addAttribute("producto", new Producto());
        List<Categoria> categoria = serviceC.listar();
        model.addAttribute("categoria", categoria);
        return "compra";
    }
    @GetMapping("/ropas")
    public String catalogoropa(Model model) {
        List<Producto> producto = serviceProd.listar();
        model.addAttribute("producto", producto);
        return "ropas";
    }

    @GetMapping("/ventas")
    public String proctoscategoria(Model model) {
        List<Categoria> Categoria = serviceC.listar();
        model.addAttribute("categoria",Categoria);
        return "administrador/ventas";
    }

    @GetMapping("/categoria/{id}")
    public String productocategoria(@PathVariable int id, Model model) {
        List<Producto> productos = serviceProd.listar();
        List<Producto> listprod = new ArrayList<>();
        for (Producto prod : productos) {
            if (prod.getCategoria().getId() == id) {
                listprod.add(prod);
            }
        }
        model.addAttribute("producto", listprod);
        return "ropas";
    }

}