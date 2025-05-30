package com.example.cja_inventario.controlers;

import com.example.cja_inventario.interfaceServices.IAgendamientoService;
import com.example.cja_inventario.interfaceServices.IDiagnosticoService;
import com.example.cja_inventario.interfaceServices.IEquipoService;
import com.example.cja_inventario.interfaceServices.I_usuarioServices;
import com.example.cja_inventario.models.Agendamiento;
import com.example.cja_inventario.models.Diagnostico;
import com.example.cja_inventario.models.Equipo;
import com.example.cja_inventario.models.Usuario;
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
@RequestMapping
public class ControladorAgendamiento {
    @Autowired
    private  IAgendamientoService service;
    @Autowired
    private  IDiagnosticoService diagnosticoService;
    @Autowired
    private  IEquipoService equipoService;
    @Autowired
    private I_usuarioServices usuarioService;
    @Autowired
    private I_usuarioServices serviceUser;

    @Autowired
    public ControladorAgendamiento(IAgendamientoService agendamientoService,
                                   IDiagnosticoService diagnosticoService,
                                   IEquipoService equipoService,
                                   I_usuarioServices usuarioService) {
        this.service = agendamientoService;
        this.diagnosticoService = diagnosticoService;
        this.equipoService = equipoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listarAgen")
    public String listar(Model model) {
        Usuario usuarioAutenticado = usuarioService.ValidarSesionCliente();

        if (usuarioAutenticado == null) {
            // Puedes redirigir al usuario a una página de inicio de sesión o mostrar un mensaje de error
            return "redirect:/IniciarSesion";
        }

        List<Agendamiento> agendamientos = service.ListarAgendamientosPorCliente(usuarioAutenticado);
        List<Equipo> equipos = equipoService.ListarEquiposPorUsuario(usuarioAutenticado);
        List<Diagnostico> diagnosticos = diagnosticoService.ListarDiagnosticosPorCliente(usuarioAutenticado);

        model.addAttribute("user", usuarioAutenticado);
        model.addAttribute("diagnosticos", diagnosticos);
        model.addAttribute("agendamientos", agendamientos);
        model.addAttribute("equipos", equipos);

        return "indexAgen";
    }

    @GetMapping("/listarAgenAdmin")
    public String listarAdmin(Model model) {
        if (usuarioService.ValidarSesionAdmin() == null) {
            // Puedes redirigir al usuario a una página de inicio de sesión o mostrar un mensaje de error
            return "redirect:/IniciarSesion";
        }

        List<Agendamiento> agendamientos = service.listar();
        List<Equipo> equipos = equipoService.Listar();
        List<Diagnostico> diagnosticos = diagnosticoService.listar();

        model.addAttribute("user", usuarioService.ValidarSesionAdmin());
        model.addAttribute("diagnosticos", diagnosticos);
        model.addAttribute("agendamientos", agendamientos);
        model.addAttribute("equipos", equipos);

        return "administrador/AgendamientoServicio";
    }

    @GetMapping("/newAgendamiento/{id}")
    public String agregar(@PathVariable int id, Model model) {
        Agendamiento agendamiento = new Agendamiento();
        Optional<Diagnostico> diagnostico = diagnosticoService.listarID(id);
        Diagnostico di = diagnostico.orElse(null);
        agendamiento.setDiagnostico(di);

        if (usuarioService.ValidarSesionCliente() == null) {
            // Puedes redirigir al usuario a una página de inicio de sesión o mostrar un mensaje de error
            return "redirect:/IniciarSesion";
        }

        model.addAttribute("user", usuarioService.ValidarSesionCliente());
        model.addAttribute("diagnostico", di);
        model.addAttribute("agendamiento", agendamiento);

        return "formAgen";
    }

    @PostMapping("/saveAgendamiento")
    public String save(@Validated Agendamiento a, Model model) {
        service.save(a);
        return "redirect:/listarAgen";
    }
    @GetMapping("/editarAgen/{id}")
    public String editar(@PathVariable int id, Model model) {
        Optional<Agendamiento> agendamiento = service.ListarID(id);
        model.addAttribute("agendamiento", agendamiento);
        return "formAdminAgen";
    }
    @PostMapping("/saveAgendamientoAdmin")
    public String saveAdmin(@Validated Agendamiento a, Model model) {
        service.save(a);
        return "redirect:/listarAgenAdmin";
    }
}