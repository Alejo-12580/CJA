package com.example.cja_inventario.services;

import com.example.cja_inventario.interfaceServices.I_usuarioServices;
import com.example.cja_inventario.interfaces.I_usuario;
import com.example.cja_inventario.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServices implements I_usuarioServices {

    @Autowired
    private I_usuario date;

    @Override
    public List<Usuario> listar() {

        return (List<Usuario>) date.findAll();
    }

    @Override
    public List<Usuario> listarAdmin(){
        List<Usuario>us=listar();
        List<Usuario> usuarios=new ArrayList<>();
        for (Usuario user:us) {
            if(user.getRoles().getId()==1){
                usuarios.add(user);
            }

        }
        return usuarios;
    }
    @Override
    public List<Usuario> listarCliente(){
        List<Usuario>us=listar();
        List<Usuario> usuarios=new ArrayList<>();
        for (Usuario user:us) {
            if(user.getRoles().getId()==2){
                usuarios.add(user);
            }

        }
        return usuarios;
    }

    @Override
    public Optional<Usuario> listarId(int id) {

        return date.findById(id);
    }

    @Override
    public int save(Usuario user) {
        int res=0;
        Usuario us=date.save(user);
        if(!us.equals(null)){
            res=1;

        }
        return res;
    }

    @Override
    public void delete(int id) {
        date.deleteById(id);

    }



    @Override
    public Usuario buscarUsuarioActivo() {
        List<Usuario>listaUsuarios=listar();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEstado()) {
                return usuario;
            }
        }
        return null;
    }
    @Override
    public void CerrarSesionAdmin(){
        List<Usuario>listaUsuarios=listar();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEstado() && usuario.getRoles().getId()==1) {
                usuario.setEstado(Boolean.FALSE);
                save(usuario);
            }
        }
    }
    @Override
    public void CerrarSesionCliente(){
        List<Usuario>listaUsuarios=listar();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEstado() && usuario.getRoles().getId()==2) {
                usuario.setEstado(Boolean.FALSE);
                save(usuario);
            }
        }
    }
    @Override
    public Usuario ValidarSesionAdmin(){
        List<Usuario>listaUsuarios=listar();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEstado() && usuario.getRoles().getId()==1) {
                return usuario;
            }
        }
        return null;
    }

    @Override
    public Usuario ValidarSesionCliente(){
        List<Usuario>listaUsuarios=listar();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getEstado() && usuario.getRoles().getId()==2) {
                return usuario;
            }
        }
        return null;
    }
    @Override
    public Boolean validarDuplicados(Usuario user){

        List<Usuario>us=listar();

            for (Usuario usuario : us) {

                String userName = usuario.getUserName().replaceAll("\\s+", "");
                String email = usuario.getEmail().replaceAll("\\s+", "");
                String password = usuario.getPassword().replaceAll("\\s+", "");
                 String cedula= usuario.getCedula();


                if (userName.equalsIgnoreCase(user.getUserName().replaceAll("\\s+", "")) || (email.equalsIgnoreCase(user.getEmail().replaceAll("\\s+", ""))) || (password.equalsIgnoreCase(user.getPasswordConfirm().replaceAll("\\s+", "")))) {
                    return true;
                }

            }

        return false;
    }

    @Override
    public String[] devolverCorreos(List<Usuario> user){
        String emails[]=new String[user.size()];
         int cont=0;
        for (Usuario us:user) {
            emails[cont]=us.getEmail();
            cont++;
        }
        return emails;
    }
}
