package com.example.cja_inventario.controlers;


import com.example.cja_inventario.domain.EmailFileDTO;
import com.example.cja_inventario.interfaceServices.*;
import com.example.cja_inventario.models.DetalleOrden;
import com.example.cja_inventario.models.Orden;
import com.example.cja_inventario.models.Producto;
import com.example.cja_inventario.models.Proveedor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class OrdenController {

    int r;

    @Autowired
    private I_ordenServices service;

    @Autowired
    private I_productoServices serviceProd;

    @Autowired
    private I_proveedorServices serviceProv;

    @Autowired

    private I_detalleServices servicesDet;

    @Autowired
    private I_usuarioServices serviceUser;

    @Autowired
    private MailController emailCon;

    @Autowired
    private I_reportesService serviceReport;

    private String mensaje;

    @GetMapping("/ordenLista")
    public String listar(Model model) {
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        List<Orden> orden = service.listar();
        List<DetalleOrden> dt= servicesDet.listar();

        for (Orden ord:orden) {
            int cont=0;

            for (DetalleOrden detalle:dt) {

                if(ord.getId().equals(detalle.getOrden().getId())){
                    cont++;
                }

            }
            if(cont==0){
                service.delete(ord.getId());
            }

        }
        List<Orden> or = service.listar();
        model.addAttribute("mensaje",mensaje);
        mensaje=null;
        model.addAttribute("orden", or);
        return "administrador/OrdenListar";
    }



    @GetMapping("/newOrden_pedido")
    public String create(Model model) {
        //model.addAttribute("orden", new Orden());
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        Orden orden = new Orden();
        List<Proveedor> proveedor = serviceProv.listar();
        model.addAttribute("proveedor", proveedor);
        LocalDate fechaActual = LocalDate.now();
        model.addAttribute("fechaActual", fechaActual);
        orden.setFechaCreacion(LocalDate.now()); // Establecer la fecha actual en el objeto Orden
        model.addAttribute("orden", orden);
        model.addAttribute("detalle", new DetalleOrden());
        List<Producto> producto = serviceProd.listar();
        model.addAttribute("producto", producto);
        r=0;
        model.addAttribute("respuesta",r);


        return "administrador/CrearOrden";
    }


    @PostMapping("/newOrden/{id}")
    public String save(@Validated Orden ord,@PathVariable int id, Model model, RedirectAttributes redirect) {



        if(!(ord.getId()==null || ord.getId()==0)) {
            /*
            Optional<DetalleOrden> dt = servicesDet.listarId(id);
            DetalleOrden detalle = dt.orElse(null);
             */
            /*
            return "redirect:/editDetalle";
             */
            service.save(ord);
            mensaje="La orden #"+ord.getId()+" se Actualizo correctamente";
            redirect.addAttribute("orden", ord);
            return "redirect:/newOrdenDetalle";
        }
        service.save(ord);
        mensaje="La orden #"+ord.getId()+" se creo correctamente";
        redirect.addAttribute("orden", ord);
        return "redirect:/newOrdenDetalle";

    }

    @GetMapping("/editOrden/{id}")
    public String editar(@PathVariable int id, Model model) {
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
            Optional<Orden> orden = service.listarId(id);
            List<DetalleOrden> detalles = servicesDet.listarIdOrden(id);
            model.addAttribute("detalle", detalles);
            r=1;
            model.addAttribute("fechaActual",orden.get().getFechaCreacion());
            model.addAttribute("total",orden.get().getTotal());
            model.addAttribute("ide",id);
            model.addAttribute("orden",orden);
        model.addAttribute("respuesta", r);
        List<Proveedor> proveedor = serviceProv.listar();
        model.addAttribute("proveedor", proveedor);

        return "administrador/CrearOrden";
    }

    @GetMapping("/eliminarOrden/{id}")
    public String delete(Model model, @PathVariable int id) {
List<DetalleOrden>detalle=servicesDet.listar();
        int cont=0;
        int idDetalle=0;
        for ( DetalleOrden dt:detalle) {
            if(dt.getOrden().getId()==id){
                servicesDet.delete(dt.getId());
            }
        }

        service.delete(id);

        return "redirect:/ordenLista";
    }

    @GetMapping("/regist/{id}")
    public String registrar(Model model, @PathVariable int id) {
        Optional<Orden> ordenOptional = service.listarId(id);
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());

        if (ordenOptional.isPresent()) {
            Orden orden = ordenOptional.get();
            List<DetalleOrden> detalles = servicesDet.listarIdOrden(orden.getId());
            model.addAttribute("orden", orden);
            model.addAttribute("detalles", detalles);
        }
            return "administrador/OrdenView";

        }

        @PostMapping("/validarFecha/{id}")
        public String validarFechaR(@PathVariable int id, Model model,@Validated  Orden ord){

           List<DetalleOrden>dt=servicesDet.listarIdOrden(id);
            for (DetalleOrden detalle:dt) {
                Optional<Producto> producto = serviceProd.listarId(detalle.getProductos().getId());
                Producto prod = producto.orElse(null);
                if (prod != null) {
                    prod.setCantidad(prod.getCantidad()+detalle.getCantidad());
                    serviceProd.save(prod);
                }

            }
            ord.setFechaRecibida(LocalDate.now());
            mensaje="La orden #"+ord.getId()+" se Registro correctamente";

        service.save(ord);

         return "redirect:/ordenLista";
        }

    @GetMapping("/vistaEmailProveedor/{id}")
    public String vistaEnvioCorreoProveedor(@PathVariable int id,Model model){

        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        List<DetalleOrden> dt=servicesDet.listarIdOrden(id);
        Optional<Orden> orden=service.listarId(id);
        model.addAttribute("mensaje",mensaje);
        mensaje=null;

        model.addAttribute("orden",orden.get());
        model.addAttribute("id_orden",id);
        model.addAttribute("detalle",dt);
        return "administrador/VistaEmail/viewEmailOrden";
        }
    @GetMapping("/enviarOrden/{id}")
    public String enviarOrdenEmail(Model model,@PathVariable int id) throws JRException, FileNotFoundException {
        Optional<Orden> ord=service.listarId(id);
        String[] emails={ord.get().getProveedor().getCorreo()};
        String sub="Pedido de Productos";
        String msm="Yo vere con rebaja ";
        List<DetalleOrden>dt =servicesDet.listarIdOrden(id);
        byte[] pdfBytes = serviceReport.exportPdfOrdenPedido(dt);
        MultipartFile pdfFile = new MockMultipartFile("pdfFile", "OrdenPedido.pdf", "application/pdf", pdfBytes);

        EmailFileDTO emailFileDTO= new EmailFileDTO(emails,sub,msm,pdfFile);
        emailCon.receveRequestEmailwithFile(emailFileDTO);
        mensaje="La orden #"+ord.get().getId()+" se Envio correctamente al proveedor "+ord.get().getProveedor().getNombre();

        return "redirect:/ordenLista";
    }

    }

