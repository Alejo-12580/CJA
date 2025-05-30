package com.example.cja_inventario.controlers;


import com.example.cja_inventario.interfaceServices.*;
import com.example.cja_inventario.models.Categoria;
import com.example.cja_inventario.models.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
public class CategoriaController {

    @Autowired

    private I_categoriaServices service;
    @Autowired

    private I_productoServices servicesProd;

    @Autowired
    private I_usuarioServices serviceUser;

    @Autowired
    private I_generoServices serviceGeneros;
    @Autowired
    private I_medidaServices serviceMedidas;

    private String mensaje;


    @GetMapping("/categoria")
    public String listar(Model model){
       List<Categoria>categoria=service.listar();
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
model.addAttribute("mensaje",mensaje);
       model.addAttribute("categoria",categoria);
mensaje=null;
        return "administrador/CategoriasIndex";
    }

        @GetMapping("/newCategoria")
        public String create(Model model){
            if (serviceUser.ValidarSesionAdmin()==null){
                return "redirect:/IniciarSesion";
            }
            model.addAttribute("mensaje",mensaje);
            mensaje=null;
            model.addAttribute("user", serviceUser.ValidarSesionAdmin());
            model.addAttribute("categoria", new Categoria());

            return "administrador/CreateCategoria";
        }

        @PostMapping("/new")
        public String save(@Validated Categoria categoria, BindingResult result, Model model, @RequestParam("imagen") MultipartFile imagen,RedirectAttributes redirect) throws IOException {
            if (result.hasErrors()) {
                // Manejar los errores de validación si es necesario

            }

            // No hay errores de validación, continuar con el proceso de guardado

            // Verificar si es un nuevo registro o una edición
            if (categoria.getId() == null || categoria.getId() == 0) {
                // Nuevo registro
                if (service.validarDuplicados(categoria)) {
                    mensaje="La categoria "+categoria.getNombre()+" ya existe";
                    return "redirect:/newCategoria";
                }
                // Guardar la nueva imagen solo si se está creando un nuevo producto
                if (!imagen.isEmpty()) {
                    String rutaArchivo = guardarArchivo(imagen);
                    String nombreImagen = imagen.getOriginalFilename();
                    categoria.setImagen(nombreImagen);
                }
                mensaje="La categoria "+categoria.getNombre()+" se Registro correctamente";
            } else {
                // Edición
                Optional<Categoria> categoriaExistente = service.listarId(categoria.getId());
                if (categoriaExistente.isPresent()) {
                    Categoria categoriaAnterior = categoriaExistente.get();

                    // Eliminar la imagen anterior solo si se proporciona una nueva imagen
                    if (!imagen.isEmpty()) {
                        String rutaImagenAnterior = categoriaAnterior.getImagen();
                        if (rutaImagenAnterior != null && !rutaImagenAnterior.isEmpty()) {
                            File archivoAnterior = new File("src/main/resources/static/imagenes/" + rutaImagenAnterior);
                            if (archivoAnterior.exists()) {
                                archivoAnterior.delete();
                            }
                        }

                        // Guardar la nueva imagen y actualizar el campo imagen del producto
                        String rutaArchivo = guardarArchivo(imagen);
                        String nombreImagen = imagen.getOriginalFilename();
                        categoria.setImagen(nombreImagen);
                    } else {
                        // Si no se proporciona una nueva imagen, mantener la imagen existente
                        categoria.setImagen(categoriaAnterior.getImagen());
                    }
                }mensaje="La categoria "+categoria.getNombre()+" se Actualizo correctamente";
            }


            // Guardar el producto en la base de datos (tanto para nuevo registro como para edición)
            service.save(categoria);

            redirect.addAttribute("categoria",categoria);

            return "redirect:/newGenero";
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

    private void eliminarImagenCategoria(String nombreImagen) {
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
    /* public String save(@Validated Categoria cat,Model model){

        if (cat.getId() == null || cat.getId() == 0) {

            if( service.validarDuplicados(cat)){
                return "redirect:/categoria";            }

        }

            service.save(cat);

        return "redirect:/categoria";
    }

*/
    @GetMapping("/edit/{id}")
    public String editar(@PathVariable int id, Model model){
        if (serviceUser.ValidarSesionAdmin()==null){
            return "redirect:/IniciarSesion";
        }
        model.addAttribute("user", serviceUser.ValidarSesionAdmin());
        Categoria categoria =service.listarId(id).orElse(new Categoria());
        model.addAttribute("categoria",categoria);

        return "administrador/CreateCategoria";

    }
    /*public String editar(@PathVariable int id, Model model){
        Optional<Categoria> optionalCategoria = service.listarId(id);
        if (optionalCategoria.isPresent()) {
            Categoria categoria = optionalCategoria.get();
            model.addAttribute("categoria", categoria);
        }

        return "administrador/CreateCategoria";
    }

*/
    @GetMapping("/eliminarCategoria/{id}")
public String delete(Model model, @PathVariable int id){

        List<Producto> productos=servicesProd.listar();
        int cont=0;
        for ( Producto producto:productos) {
            if(producto.getCategoria().getId()==id){
                cont++;
            }

        }
        Categoria ct= service.listarId(id).orElse(new Categoria());
        mensaje="La categoria "+ct.getNombre()+"  no se Elimino correctamente , por que esta asocida a diferentes Productos";
        if (cont==0){
            Optional<Categoria> categoria=service.listarId(id);
            if(categoria.isPresent()) {
                Categoria categ = categoria.get();
                eliminarImagenCategoria(categ.getImagen());
            }

            mensaje="La categoria "+ct.getNombre()+" se Elimino correctamente";
            service.delete(id);
        }


    return "redirect:/categoria";
}

}
