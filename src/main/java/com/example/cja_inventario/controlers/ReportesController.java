package com.example.cja_inventario.controlers;


import com.example.cja_inventario.interfaceServices.*;
import com.example.cja_inventario.models.DetalleOrden;
import com.example.cja_inventario.models.Producto;
import com.example.cja_inventario.models.Proveedor;
import com.example.cja_inventario.models.Usuario;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class ReportesController {

    @Autowired
    private I_reportesService serviceReport;

    @Autowired
    private I_productoServices serviceProd;

    @Autowired
    private I_proveedorServices serviceProv;

    @Autowired
    private I_usuarioServices serviceUser;

    @Autowired
    private I_detalleServices serviceDt;

    @PostMapping("/Report")
    public String GenerarReporte(@RequestParam("opcion") int opcion){
        String ruta="";
        switch (opcion){
            case 0:
                ruta="redirect:/reporteProductos";
                break;
            case 1:
                ruta="redirect:/reporteEntradas";
                break;
            case 2:
                break;
            case 3:
                ruta="redirect:/reporteProveedor";
                break;
            case 4:
                ruta="redirect:/reporteOrden";
                break;
            case 5:
                ruta="redirect:/reporteUser";
                break;
        }
        return ruta;
    }
    @GetMapping("/export-pdf-productos")
    public ResponseEntity<byte[]> exportPdfProductos() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("reporteProductos", "reporte.pdf");
        return ResponseEntity.ok().headers(headers).body(serviceReport.exportPdfprod());
    }
    @GetMapping("/export-pdf-proveedor")
    public ResponseEntity<byte[]> exportPdfProveedor() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("OrdenPedido", "OrdenPedido.pdf");
        List<DetalleOrden>dt =serviceDt.listar();
        return ResponseEntity.ok().headers(headers).body(serviceReport.exportPdfOrdenPedido(dt));
    }
    @GetMapping("/export-pdf-entrada")
    public ResponseEntity<byte[]> exportPdfEntradas() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("EntradaProductos", "EntradasReport.pdf");
        return ResponseEntity.ok().headers(headers).body(serviceReport.exportPdfEntrada());
    }
    @GetMapping("/reporteProductos")
    public String vistaReporteProducto( Model model){

        List<Producto> productos=serviceProd.listar();
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());

        model.addAttribute("producto",productos);

        return "administrador/Reportes/viewProductos" ;
    }

    @GetMapping("/reporteProveedor")
    public String vistaReporteProveedor(Model model){

        List<Proveedor> proveedor=serviceProv.listar();
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());

        model.addAttribute("proveedor",proveedor);

        return "administrador/Reportes/viewProveedor" ;
    }
    @GetMapping("/reporteUser")
    public String vistaReporteAdmin(Model model){

        List<Usuario> usuarios=serviceUser.listarAdmin();
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());

        model.addAttribute("usuario",usuarios);

        return "administrador/Reportes/viewUsuarios" ;
    }
    @GetMapping("/reporteOrden")
    public String vistaReporteOrden(Model model){

        List<DetalleOrden> detalle=serviceDt.listar();
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());

        model.addAttribute("detalle",detalle);

        return "administrador/Reportes/viewOrdenPedido" ;
    }

    @GetMapping("/reporteEntradas")
    public String vistaReporteEntradas(Model model){

        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }

        List<DetalleOrden> listDetalle=serviceDt.listar();
        List<DetalleOrden> listDt = new ArrayList<>();

        for (DetalleOrden dt:listDetalle) {
            if(dt.getOrden().getFechaRecibida()!= null) {
                listDt.add(dt);
            }
        }

        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        model.addAttribute("detalle",listDt);

        return "administrador/Reportes/viewEntradas";
    }


}
