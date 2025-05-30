package com.example.cja_inventario.controlers;

import com.example.cja_inventario.interfaceServices.I_rolesServices;
import com.example.cja_inventario.interfaceServices.I_usuarioServices;
import com.example.cja_inventario.models.Roles;
import com.example.cja_inventario.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping()
public class UsuarioController {

    @Autowired
    private I_usuarioServices service;

    @Autowired
    private I_rolesServices serviceRol;

    private String mensaje;


    @GetMapping("/User")
    public String listarAdministradores(Model model){
        int cont=0;
        if (service.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user1", service.ValidarSesionAdmin());
        List<Usuario>user=service.listarAdmin();
        for (Usuario us:user) {
            cont++;
        }
        model.addAttribute("mensaje",mensaje);
        mensaje=null;
        model.addAttribute("cont",cont);
        model.addAttribute("user",user);

        return "administrador/UsuariosListar";

    }

    @GetMapping("/newUser")
    public String crearAdministrador(Model model){
        if (service.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("us", service.ValidarSesionAdmin());
        Usuario user= new Usuario();
        Optional<Roles> r= serviceRol.listarId(1);
        Roles rol=r.orElse(null);
        user.setRoles(rol);
        user.setEstado(Boolean.FALSE);
        model.addAttribute("user",user);
        model.addAttribute("mensaje",mensaje);
        mensaje=null;

         return "administrador/RegistroUsuarios";
    }

    @GetMapping("/newUserCliente")
    public String crearCliente(Model model){
        Usuario user= new Usuario();
        Optional<Roles> r= serviceRol.listarId(2);
        Roles rol=r.orElse(null);
        user.setRoles(rol);
        user.setEstado(Boolean.FALSE);
        model.addAttribute("user",user);
        model.addAttribute("mensaje",mensaje);
        mensaje=null;

        return "administrador/registrarse";
    }

    @PostMapping("/saveUser")
    public String save(@Validated Usuario user,Model model){
        if (user.getId()==null || user.getId()==0 ){

            if(user.getRoles().getId()==2){
                if (service.validarDuplicados(user)) {
                    mensaje="El usuario ya existe";
                    return "redirect:/newUserCliente";
                }
                service.save(user);
                return "redirect:/IniciarSesion";
            }else {
                if (service.validarDuplicados(user)) {
                    mensaje="La categoria "+user.getNombre()+" ya existe";
                    return "redirect:/newUser";
                }

                service.save(user);
                mensaje="El usuario "+user.getNombre()+" se Registro correctamente";
                return "redirect:/User";
            }



        }else {

            if(user.getRoles().getId()==2){
                if (service.validarDuplicados(user)) {

                    return "redirect:/newUserCliente";
                }
                service.save(user);
                return "redirect:/IniciarSesion";
            }else {
                if (service.validarDuplicados(user)) {

                    return "redirect:/newUserCliente";
                }
                service.save(user);
                mensaje="El usuario "+user.getNombre()+" se Actualizo correctamente";
                return "redirect:/User";
            }

        }


    }

    @GetMapping("/editUser/{id}")
    private String edit(@PathVariable int id, Model model){
        if (service.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("us", service.ValidarSesionAdmin());
        Optional<Usuario> user=service.listarId(id);
        model.addAttribute("user",user);
        return "administrador/RegistroUsuarios";
    }

    @PostMapping("/login")
    public String validarCesion(@RequestParam("Correo") String correo, @RequestParam("Contraseña")
    String contraseña,Model model){
        String mensaje="Ya hay un usuario activo";
        List<Usuario> usuarios = service.listar();

        if(service.buscarUsuarioActivo()==null) {

            for (Usuario user : usuarios) {
                if (user.getUserName().equals(correo) && user.getPassword().equals(contraseña)) {
                    if (user.getRoles().getId() == 1 && service.ValidarSesionAdmin() == null) {

                        user.setEstado(Boolean.TRUE);
                        service.save(user);

                        return "redirect:/productos";
                    } else if (user.getRoles().getId() == 2 && service.ValidarSesionCliente() == null) {
                        user.setEstado(Boolean.TRUE);
                        service.save(user);

                        return "redirect:/menu";
                    }
                }
            }
            mensaje="La contraseña y el usuario no coinciden";
        }
        model.addAttribute("mensaje",mensaje);
            return "administrador/login";
          //  return "redirect:/IniciarSesion";
    }
    @GetMapping("cerrarSesion/{id}")
    public String CerrarSesion(@PathVariable int id){
        Optional<Usuario> user=service.listarId(id);
        Usuario usuario=user.orElse(null);
        if(usuario.getRoles().getId()==1){
            service.CerrarSesionAdmin();
        }
        else{
            service.CerrarSesionCliente();
        }
        return "redirect:/IniciarSesion";
    }

    @GetMapping("eliminarUser/{id}")
    public String eliminarUsuario(@PathVariable int id){
        Usuario user=service.listarId(id).orElse(new Usuario());
        mensaje="El usuario "+user.getNombre()+" se Elimino correctamente";
        service.delete(id);
        return "redirect:/User";

    }




}
