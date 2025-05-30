package com.example.cja_inventario.controlers;

import com.example.cja_inventario.interfaceServices.*;
import com.example.cja_inventario.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class ProductoController {

    @Autowired
    private I_usuarioServices serviceUser;

    @Autowired
    private I_productoServices service;
    @Autowired
    private I_categoriaServices serviceC;

    @Autowired
    private I_detalleServices serviceDet;

    @Autowired
    private I_reportesService serviceRep;

    @Autowired
    private  I_medidaServices serviceMedida;

    @Autowired
    private  I_generoServices serviceGeneros;

    private String mensaje;




    @GetMapping("/productos")
    public String listar(Model model){

        List<Producto> productos = service.listar();

        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("mensaje",mensaje);
        mensaje=null;

        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        model.addAttribute("producto", productos);

        return "administrador/ProductosInventario";}

    @GetMapping("/newProducto")
    public String create(Model model)  {
        if (serviceUser.ValidarSesionAdmin() == null) {
            return "redirect:/IniciarSesion";
        }
        String state[] = {"nuevo", "usado"};
        List<Medidas> medidas = serviceMedida.listar();
        List<Generos> generos = serviceGeneros.listar();

        model.addAttribute("generos",generos);

        model.addAttribute("capConsolas", medidas);
        model.addAttribute("state", state);
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        model.addAttribute("producto", new Producto());
        List<Categoria> categoria = serviceC.listar();

        model.addAttribute("categoria", categoria);
        model.addAttribute("mensaje",mensaje);
        mensaje=null;
        return "administrador/seleccionCategoriaProductos";
    }
    @PostMapping("/seveProducto")
    public String save(@Validated Producto prod, BindingResult result, Model model, @RequestParam("imagen") MultipartFile imagen) throws IOException {
        if (result.hasErrors()) {
            // Manejar los errores de validación si es necesario
        }

        // No hay errores de validación, continuar con el proceso de guardado

        // Verificar si es un nuevo registro o una edición
        if (prod.getId() == null || prod.getId() == 0) {
            // Nuevo registro
            if (service.ValidarDuplicados(prod)) {
                mensaje="El producto ya existe";
                return "redirect:/newProducto";
            }
            // Guardar la nueva imagen solo si se está creando un nuevo producto
            if (!imagen.isEmpty()) {
                String rutaArchivo = guardarArchivo(imagen);
                String nombreImagen = imagen.getOriginalFilename();
                prod.setImagen(nombreImagen);
            }
            mensaje="El producto "+prod.getNombre()+" se Registro correctamente";
        } else {
            // Edición
            Optional<Producto> productoExistente = service.listarId(prod.getId());
            if (productoExistente.isPresent()) {
                Producto productoAnterior = productoExistente.get();

                // Eliminar la imagen anterior solo si se proporciona una nueva imagen
                if (!imagen.isEmpty()) {
                    String rutaImagenAnterior = productoAnterior.getImagen();
                    if (rutaImagenAnterior != null && !rutaImagenAnterior.isEmpty()) {
                        File archivoAnterior = new File("src/main/resources/static/imagenes/" + rutaImagenAnterior);
                        if (archivoAnterior.exists()) {
                            archivoAnterior.delete();
                        }
                    }

                    // Guardar la nueva imagen y actualizar el campo imagen del producto
                    String rutaArchivo = guardarArchivo(imagen);
                    String nombreImagen = imagen.getOriginalFilename();
                    prod.setImagen(nombreImagen);
                } else {
                    // Si no se proporciona una nueva imagen, mantener la imagen existente
                    prod.setImagen(productoAnterior.getImagen());
                }
            }
            mensaje="El producto "+prod.getNombre()+" se Actualizo correctamente";
        }

        // Guardar el producto en la base de datos (tanto para nuevo registro como para edición)
        service.save(prod);

        return "redirect:/productos";
    }



    private String guardarArchivo(MultipartFile file) throws IOException {
        // Obtener la ruta donde se guardarán los archivos
        String rutaDirectorio = "src/main/resources/static/imagenes";

        // Generar un nombre único para el archivo
        String nombreArchivo =file.getOriginalFilename();

        // Guardar el archivo en el sistema de archivos
        Path rutaArchivo = Paths.get(rutaDirectorio, nombreArchivo);
        Files.copy(file.getInputStream(), rutaArchivo, StandardCopyOption.REPLACE_EXISTING);

        // Devolver la ruta completa del archivo
        return rutaArchivo.toString();
    }

    @PostMapping("/selectCategoria")
    public String ValidarCategoria(Model model,@Validated Producto producto ){

        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        String state[]={"nuevo","usado"};
        List<Categoria> categoria=serviceC.listar();
        List<Medidas> medidas = serviceMedida.listar();
        List<Generos> generos = serviceGeneros.listar();

        model.addAttribute("generos",generos);
        model.addAttribute("capConsolas", medidas);
        model.addAttribute("state",state);
        model.addAttribute("producto",producto);
        model.addAttribute("categoria",categoria);

        return "administrador/RegistroProductos";
    }


    @GetMapping("/editP/{id}")
    public String editar(@PathVariable int id, Model model){
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        String state[]={"nuevo","usado"};

        List<Medidas> medidas = serviceMedida.listar();
        List<Generos> generos = serviceGeneros.listar();

        model.addAttribute("generos",generos);
        model.addAttribute("capConsolas", medidas);
        model.addAttribute("state",state);
        Optional<Producto> producto =service.listarId(id);
        Producto prod = service.listarId(id).orElse(new Producto()); // Si el producto no existe, crea uno nuevo
        model.addAttribute("producto",prod);
        List<Categoria> categoria=serviceC.listar();
        model.addAttribute("categoria",categoria);

        return "administrador/seleccionCategoriaProductos";
    }
    @GetMapping("/eliminarP/{id}")
    public String delete(Model model, @PathVariable int id) {
        List<DetalleOrden> detalle = serviceDet.listar();
        int cont = 0;
        for (DetalleOrden detalleOrden : detalle) {
            if (detalleOrden.getProductos().getId() == id) {
                cont++;
            }
        }
        Producto prod=service.listarId(id).orElse(new Producto());
        mensaje="El producto "+prod.getNombre()+" no se elimino correctamente , por que este producto esta relacionado con algunas ordenenes de pedido";

        if (cont == 0) {
            // Obtener el producto antes de eliminarlo para obtener la ruta de la imagen
            Optional<Producto> productoAEliminar = service.listarId(id);
            if (productoAEliminar.isPresent()) {
                Producto producto = productoAEliminar.get();
                // Eliminar la imagen si existe
                eliminarImagenProducto(producto.getImagen());
            }
            mensaje="El producto "+prod.getNombre()+" se Elimino correctamente";
            service.delete(id);
        }

        return "redirect:/productos";
    }

    private void eliminarImagenProducto(String nombreImagen) {
        if (nombreImagen != null && !nombreImagen.isEmpty()) {
            // Obtener la ruta donde se guardan las imágenes
            String rutaDirectorio = "src/main/resources/static/imagenes";

            // Crear el objeto File correspondiente a la imagen
            File archivoImagen = new File(rutaDirectorio, nombreImagen);

            // Verificar si el archivo existe y eliminarlo
            if (archivoImagen.exists()) {
                archivoImagen.delete();
            }
        }
    }


}
