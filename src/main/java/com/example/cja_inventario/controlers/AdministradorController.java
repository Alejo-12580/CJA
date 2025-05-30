package com.example.cja_inventario.controlers;

import com.example.cja_inventario.domain.EmailDTO;
import com.example.cja_inventario.domain.EmailFileDTO;
import com.example.cja_inventario.interfaceServices.I_usuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("")
public class AdministradorController {


    @Autowired

    private I_usuarioServices serviceUser;

    @Autowired
    private MailController emailCon;



    private String mensaje;

@GetMapping("/correoMasivo")
    public String homeEnviarCorreo(Model model){
    if (serviceUser.ValidarSesionAdmin()==null){
        return "redirect:/IniciarSesion";
    }
    model.addAttribute("mensaje",mensaje);
    model.addAttribute("user", serviceUser.ValidarSesionAdmin());

    return "administrador/VistaEmail/viewEmailMasivo";
    }

    @PostMapping("/enviarEmailMasivo")
    public String procesarFormulario(@RequestParam("opcion") int opcionRadio,
                                     @RequestParam("asunto") String asunto,
                                     @RequestParam("cuerpo") String mens,
                                     @RequestParam("archivo") MultipartFile archivoFile) {

        if(opcionRadio == 1){
            String emails[] = serviceUser.devolverCorreos(serviceUser.listarAdmin());
            if(!archivoFile.isEmpty()) {

                EmailFileDTO emailFileDTO = new EmailFileDTO(emails, asunto, mens, archivoFile);
                emailCon.receveRequestEmailwithFile(emailFileDTO);
                mensaje="El correo con archivo a administradores";

            }else {
                
                EmailDTO emailDTO = new EmailDTO( emails, asunto, mens);
                emailCon.receveRequestEmail(emailDTO);
                mensaje="El correo sin archivo a administradores";
            }
        }else {
            String emails[] = serviceUser.devolverCorreos(serviceUser.listarCliente());
            if(!archivoFile.isEmpty()) {

                EmailFileDTO emailFileDTO = new EmailFileDTO(emails, asunto, mens, archivoFile);
                emailCon.receveRequestEmailwithFile(emailFileDTO);
                mensaje="El correo con archivo a Clientes";
            }else {
                EmailDTO emailDTO = new EmailDTO(emails, asunto, mens);
                emailCon.receveRequestEmail(emailDTO);
                mensaje="El correo sin archivo a Clientes";
            }
        }


        return "redirect:/productos"; // Nombre de la vista Thymeleaf que mostrará el resultado
    }
}



