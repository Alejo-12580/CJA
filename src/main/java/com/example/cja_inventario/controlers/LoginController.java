package com.example.cja_inventario.controlers;

import com.example.cja_inventario.domain.EmailDTO;
import com.example.cja_inventario.interfaceServices.I_rolesServices;
import com.example.cja_inventario.interfaceServices.I_usuarioServices;
import com.example.cja_inventario.models.Roles;
import com.example.cja_inventario.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    private I_usuarioServices serviceUser;

    @Autowired
    private MailController emailCon;

    @Autowired

    private I_rolesServices serviceRol;



    @GetMapping("/IniciarSesion")
    public String login(){
        if(serviceRol.listar().isEmpty()){

            Roles rol1= new Roles();
            rol1.setId(1);
            rol1.setNombre("Administrador");
            serviceRol.save(rol1);
            Roles rol2= new Roles();
            rol2.setId(2);
            rol2.setNombre("Cliente");
            serviceRol.save(rol2);
            if(serviceUser.listar().isEmpty()){
                Usuario user = new Usuario();
                user.setId(1);
                user.setNombre("Alejo");
                user.setApellido("Aguilar");
                user.setEstado(Boolean.FALSE);
                user.setRoles(rol1);
                user.setUserName("A12");
                user.setEmail("alejoa-12@hotmail.com");
                user.setPasswordConfirm("alejo12");
                user.setPassword("alejo12");
                user.setTelefono("3138690048");
                user.setDireccion("Calle 100");
                serviceUser.save(user);
            }
        }

        return "administrador/login";
    }
    @GetMapping("/menu")
    public String menu(Model model){

        model.addAttribute("user",serviceUser.ValidarSesionCliente());

        return "administrador/MenuCJA";
    }


    @GetMapping("/recuperarPassword")
    public String recuperarContraseña(){

        return "administrador/RecuperarContraseña";
    }

    @PostMapping("/emailRecuperacion")
    public String enviarEmail(@RequestParam("Correo") String correo, Model model){

        List<Usuario> list=serviceUser.listar();
        for (Usuario user:list) {

            if(user.getEmail().equals(correo)){
                String[] emails={correo};
                String sub="Recuperacion de Contraseña";
                String mensaje="Tu Usuario es : "+user.getUserName()+"\n"+
                        "Tu contraseña es :  "+user.getPassword();
                EmailDTO emailDTO= new EmailDTO(emails,sub,mensaje);
                emailCon.receveRequestEmail(emailDTO);
                return "redirect:/IniciarSesion";
            }

        }
        String msm="El correo no es valido, o no esta registrado";
        model.addAttribute("mensaje",msm);

        return "administrador/RecuperarContraseña";
    }
    @GetMapping("/menu2")
    public String menu2(Model model){

        model.addAttribute("user",serviceUser.ValidarSesionCliente());

        return "NewMenu";
    }
}
