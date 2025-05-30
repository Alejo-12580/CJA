package com.example.cja_inventario.controlers;

import com.example.cja_inventario.interfaceServices.I_categoriaServices;
import com.example.cja_inventario.interfaceServices.I_generoServices;
import com.example.cja_inventario.interfaceServices.I_medidaServices;
import com.example.cja_inventario.interfaceServices.I_usuarioServices;
import com.example.cja_inventario.models.Categoria;
import com.example.cja_inventario.models.Generos;
import com.example.cja_inventario.models.Medidas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class GenerosController {

    @Autowired
    private I_generoServices services;

    @Autowired
    private I_categoriaServices serviceCategoria;

    @Autowired
    private I_medidaServices serviceMedida;

    @Autowired
    private I_usuarioServices serviceUser;

    private String mensaje;


    @GetMapping("/newGenero")
    public String create(@ModelAttribute ("categoria") Categoria categoria, Model model){

        List<Generos> generos = services.listarIdCategoria(categoria.getId());
        List<Medidas> medidas=serviceMedida.listarIdCategoria(categoria.getId());
        Generos genero = new Generos();
        Medidas medida = new Medidas();
        medida.setCategoria(categoria);
        genero.setCategoria(categoria);
        model.addAttribute("mensaje",mensaje);
        model.addAttribute("medida",medida);
        model.addAttribute("medidas",medidas);
        model.addAttribute("genero",genero);
        model.addAttribute("generos",generos);
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        model.addAttribute("categoria",serviceCategoria.listarId(categoria.getId()).orElse(new Categoria()));
mensaje=null;

        return "administrador/CreateMedidasGeneros";
    }
    @PostMapping("/newGeneroRegistro")
    public String save (@Validated Generos genero, RedirectAttributes redirectAttributes){

        if(!services.validarDuplicados(genero)){
            services.save(genero);
        }else {
            mensaje="El Genero/Modelo "+genero.getNombre()+" ya existe";
        }

        redirectAttributes.addAttribute("categoria",genero.getCategoria());
        return "redirect:/newGenero";
    }

    @GetMapping("/eliminarGenero/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes){
        Optional<Generos> generoOptional = services.listarId(id);

        if (generoOptional.isPresent()) {
            Generos genero = generoOptional.get();
            Categoria categoria = genero.getCategoria();

            if (categoria != null) {
                redirectAttributes.addAttribute("categoria", categoria.getId()); // Supongo que hay una propiedad 'id' en la clase 'Categoria'
            }

            services.delete(id);
        }

        return "redirect:/newGenero";

    }
}
