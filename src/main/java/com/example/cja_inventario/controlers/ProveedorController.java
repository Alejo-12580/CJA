package com.example.cja_inventario.controlers;


import com.example.cja_inventario.interfaceServices.*;
import com.example.cja_inventario.models.Orden;
import com.example.cja_inventario.models.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class ProveedorController {

    @Autowired
    private I_reportesService serviceReport;

    @Autowired
    private MailController emailCon;

    @Autowired
 private I_proveedorServices service;
    @Autowired
    private I_ordenServices serviceOrd;

    @Autowired
    private I_detalleServices serviceDt;

    @Autowired
    private I_usuarioServices serviceUser;

    private String mensaje;

    @GetMapping("/proveedores")

        public String listar(Model model){
            List<Proveedor> proveedor=service.listar();
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        model.addAttribute("mensaje",mensaje);
        mensaje=null;

            model.addAttribute("proveedor",proveedor);

        return "administrador/Proveedores";
    }

    @GetMapping("/newProveedor")
    public String create(Model model){
        model.addAttribute("proveedor", new Proveedor());
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        model.addAttribute("mensaje",mensaje);
        mensaje=null;

        return "administrador/RegistProveedor" ;
    }
    @PostMapping("/newProveedor")
    public String save(@Validated Proveedor prov, Model model){

        List<Proveedor>proveedor=service.listar();
        if( prov.getId()==null || prov.getId()==0){
            if (service.ValidarDuplicados(prov)) {

                mensaje="El proveedor  ya existe";
                return "redirect:/newProveedor";
            }
            mensaje="El proveedor "+prov.getNombre()+" se Registro correctamente";

            }
        else {
            mensaje="El proveedor "+prov.getNombre()+" se Actualizo correctamente";
        }

        service.save(prov);

        return "redirect:/proveedores";
    }
    @GetMapping("/editProv/{id}")
    public String editar(@PathVariable int id, Model model){
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        Optional<Proveedor> proveedor =service.listarId(id);
        model.addAttribute("proveedor",proveedor);

        return "administrador/RegistProveedor" ;

    }

    @GetMapping("/eliminarProveedor/{id}")
    public String delete(Model model,@PathVariable int id){
      List<Orden> ord=serviceOrd.listar();
        int cont=0;
        for (Orden orden:ord) {
            if(orden.getProveedor().getId()==id){
                cont++;
            }

        }
        Proveedor prov=service.listarId(id).orElse(new Proveedor());
        mensaje="El proveedor "+prov.getNombre()+" no se Elimino correctamente , por que esta asociado a varias ordenes de pedido";
        if (cont==0){
            mensaje="El proveedor "+prov.getNombre()+" se Elimino correctamente";
            service.delete(id);
        }

        return "redirect:/proveedores";
    }


}
