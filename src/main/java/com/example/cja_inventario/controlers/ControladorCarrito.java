package com.example.cja_inventario.controlers;


import com.example.cja_inventario.interfaceServices.*;
import com.example.cja_inventario.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping
@Controller
public class ControladorCarrito {

    @Autowired
    private IVentasService service;

    @Autowired
    private I_categoriaServices serviceC;

    @Autowired
    private I_productoServices serviceProd;

    @Autowired
    private I_ordenServices serviceOrd;

    @Autowired
    private I_usuarioServices serviceUser;

    @Autowired
    private I_DetalleVentaService serviceDV;

    private List<DetalleVenta> detalleVentas = new ArrayList<>();
    ;

    private List<Integer> id = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<Integer> cant = new ArrayList<>();


    private List<Producto> product = new ArrayList<>();
    ;

    public List<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void getDetalleVentas(List<DetalleVenta> detalleVentas) {
        detalleVentas.clear();

    }

    @GetMapping("/SABER MAS")
    public String productocarrito(Model model) {
        List<Producto> producto = serviceProd.listar();
        model.addAttribute("producto", producto);
        return "administrador/Carrito";
    }

    @GetMapping("/Carrito/{id}")
    public String productocarrito(@PathVariable int id, Model model) {
        Producto producto = serviceProd.listarId(id).orElse(null);
        DetalleVenta carrito = new DetalleVenta();
        carrito.setProductos(producto);
        model.addAttribute("detalleVenta", detalleVentas);

        model.addAttribute("producto", producto);
        return "administrador/Carrito";
    }

    @PostMapping("/carritovista")
    public String carritovista(@RequestParam("nombreProducto") String nombreProducto, @RequestParam("idProducto") int idProducto, @RequestParam("cantidad") int cantidad, Model model) {
        DetalleVenta carrito = new DetalleVenta();
        carrito.setProductos(serviceProd.listarId(idProducto).orElse(null));
        carrito.setCantidad(cantidad);
        detalleVentas.add(carrito);
        id.add(idProducto);
        name.add(nombreProducto);
        cant.add(cantidad);

        return "redirect:/facturaVenta";

    }

    @GetMapping("/eliminarcar/{id}")
    public String deleteCartItem(Model model, @PathVariable int id) {
        DetalleVenta carrito = new DetalleVenta();
        detalleVentas.removeIf(objeto -> objeto.getProductos().getId() == id);
        return "redirect:/facturaVenta";
    }


    @GetMapping("/facturaVenta")
    public String factura(Model model) {
        model.addAttribute("user", serviceUser.ValidarSesionCliente());
        model.addAttribute("detalleVenta", detalleVentas);

        return "administrador/AñadirCarrito";

    }

    @GetMapping("/comprar")
    public String compraven(Model model) {

        boolean condicion = true; // Cambia esto según tu lógica.

        if (serviceUser.ValidarSesionCliente()==null) {
            return "redirect:/IniciarSesion";
        } else {
            return "redirect:/comprafactura";
        }
    }
    @GetMapping("/comprafactura")
    public String compra(Model model) {
        model.addAttribute("user", serviceUser.ValidarSesionCliente());
        model.addAttribute("detalleVenta", detalleVentas);
        return "ventas1";

    }

    @GetMapping("/factura")
    public String facruran(Model model) {
        int subTotal=0;
        Ventas ventas= new Ventas();
        for (DetalleVenta dt:detalleVentas) {
            subTotal=(int)dt.getProductos().getPrecio()*dt.getCantidad();
        }
        ventas.setValor_iba((subTotal*ventas.getValor_iba()/100));
        ventas.setSub_total(subTotal);
        LocalDate fventa= LocalDate.now();
        ventas.setTipoPago("Efectivo");
        ventas.setFechaVenta (fventa );
        ventas.setTotal(subTotal+ventas.getValor_iba());
        service.save(ventas);

        for (DetalleVenta dt:detalleVentas) {
            dt.setVenta(ventas);
            serviceDV.save(dt);
        }
        model.addAttribute("detalleVenta", detalleVentas);
        return "/gracias";
    }

    public void guardarDetalleVentas(int id){
        Ventas ventas=service.listarId(id).orElse(new Ventas());
        for (DetalleVenta dt:detalleVentas) {
            dt.setVenta(ventas);
            serviceDV.save(dt);
        }
    }
}