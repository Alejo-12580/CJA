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
public class MedidasController {

    @Autowired
    private I_medidaServices services;

    @Autowired
    private I_usuarioServices serviceUser;

    @Autowired
    private I_generoServices serviceGeneros;

    @Autowired
    private I_categoriaServices serviceCategoria;

    private String mensaje;




    @GetMapping("/newMed")
    public String create(@ModelAttribute("categoria") Categoria categoria, Model model){

        List<Generos> generos = serviceGeneros.listarIdCategoria(categoria.getId());
        List<Medidas> medidas=services.listarIdCategoria(categoria.getId());
        Generos genero = new Generos();
        Medidas medida = new Medidas();
        medida.setCategoria(categoria);
        genero.setCategoria(categoria);
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("medida",medida);
        model.addAttribute("medidas",medidas);
        model.addAttribute("genero",genero);
        model.addAttribute("generos",generos);
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        model.addAttribute("categoria",serviceCategoria.listarId(categoria.getId()).orElse(new Categoria()));
mensaje=null;

        return "administrador/CreateMedidasGeneros";
    }


    @PostMapping("/newMedidaRegistro")
    public String save (@Validated Medidas medida, RedirectAttributes redirectAttributes){

        if(!services.validarDuplicados(medida)){
            services.save(medida);
        }else {
            mensaje="La Talla/Capacidad "+medida.getNombre()+" ya existe";
        }

        redirectAttributes.addAttribute("categoria",medida.getCategoria());

        return "redirect:/newMed";
    }

    @GetMapping("/eliminarMedida/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes){
        Optional<Medidas> medidaOptional = services.listarId(id);

        if (medidaOptional.isPresent()) {
            Medidas medida = medidaOptional.get();
            Categoria categoria = medida.getCategoria();

            if (categoria != null) {
                redirectAttributes.addAttribute("categoria", categoria.getId()); // Supongo que hay una propiedad 'id' en la clase 'Categoria'
            }

            services.delete(id);
        }

        return "redirect:/newMed";

    }
}
