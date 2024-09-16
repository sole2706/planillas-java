/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Rol;
import Model.RolDB;
import Model.Usuario;
import Model.UsuarioDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.faces.context.FacesContext;

/**
 *
 * @author David_Solera
 */
public class BeanUsuarios {

    private LinkedList<Usuario> listaUsuarios = new LinkedList<>();
    private LinkedList<Rol> listaRoles = new LinkedList<>();

    private boolean admin = false;

    //Objeto empleado para editar
    public static Usuario usuario = null;

    private String contraActual = "";
    private String contraNueva = "";
    private String confirmaContra = "";

    private String mensaje;

    public void CrearUsuario() throws SNMPExceptions, IOException {
        if (usuario.getNombre().isEmpty()) {
            mensaje = "Ingrese el nombre de usuario.";
            return;
        }

        if (contraNueva.isEmpty()) {
            mensaje = "Ingrese una contraseña.";
            return;
        }

        if (!contraNueva.equals(confirmaContra)) {
            mensaje = "Las contraseñas no coinciden";
            return;
        }

        if (contraNueva.length() < 8) {
            mensaje = "La contraseña debe tener un largo de al menos 8 carácteres.";
            return;
        }

        //Obtiene el id del rol seleccionado
        for (int i = 0; i < listaRoles.size(); i++) {
            if (listaRoles.get(i).getNombre().equals(usuario.getRol())) {
                usuario.setIdRol(listaRoles.get(i).getID());
                break;
            }
        }

        new UsuarioDB().Insertar(usuario, contraNueva);
        FacesContext.getCurrentInstance().getExternalContext().redirect("AdministrarUsuarios.xhtml");
    }

    public void AnadirUsuario() throws SNMPExceptions, SQLException, IOException {
        contraActual = "";
        contraNueva = "";
        confirmaContra = "";
        usuario = new Usuario(0, "", 1);
        FacesContext.getCurrentInstance().getExternalContext().redirect("CrearUsuario.xhtml");
    }

    public void Guardar() throws SNMPExceptions {
        for (int i = 0; i < listaRoles.size(); i++) {
            if (listaRoles.get(i).getNombre().equals(usuario.getRol())) {
                usuario.setIdRol(listaRoles.get(i).getID());
                break;
            }
        }

        new UsuarioDB().Actualizar(usuario);

        //Solo se intenta cambiar la contraseña si el usuario ingresó algo en el campo de contraseña nueva
        if (!contraNueva.isEmpty() || !confirmaContra.isEmpty()) {
            if (!contraNueva.equals(confirmaContra)) {
                mensaje = "Las contraseñas no coinciden";
                return;
            }

            if (contraNueva.length() < 8) {
                mensaje = "La contraseña debe tener un largo de al menos 8 carácteres.";
                return;
            }

            //Intenta cambiar la contraseña, retorna true si se cambió exitosamente
            if (new UsuarioDB().CambiarContra(usuario.getID(), contraActual, contraNueva)) {
                mensaje = "";
            } else {
                mensaje = "La conraseña vieja es incorrecta.";
            }
        }

    }

    public void Deshacer() throws SNMPExceptions, IOException, SQLException {
        this.usuario = new UsuarioDB().getByID(this.usuario.getID());

        Editar(new UsuarioDB().getByID(this.usuario.getID()), admin);

        contraActual = "";
        contraNueva = "";
        confirmaContra = "";
    }

    public void Cancelar() throws IOException {
        if (admin) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("AdministrarUsuarios.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("ListaPlanillas.xhtml");
        }
    }

    public void Editar(Usuario usuario, boolean admin) throws IOException {
        this.usuario = usuario;
        this.admin = admin;

        contraActual = "";
        contraNueva = "";
        confirmaContra = "";
        try {
            listaRoles = new RolDB().moTodo();
            mensaje = "";
        } catch (Exception e) {
            mensaje = "Ha ocurrido un error al conectar con la base de datos";
        }

        if (admin) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("EditarUsuario.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("EditarPerfil.xhtml");
            this.usuario = BeanIngreso.usuario;
        }
    }

    private void MostrarListaUsuarios() throws SNMPExceptions {
        this.setListaUsuarios(new UsuarioDB().moTodo());
    }

    public LinkedList<Usuario> getListaUsuarios() {
        try {
            MostrarListaUsuarios();
            mensaje = "";
        } catch (Exception e) {
            mensaje = "Ha ocurrido un error al conectar con la base de datos";
        }

        return listaUsuarios;
    }

    public void setListaUsuarios(LinkedList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuario() throws SNMPExceptions, SQLException {
        if (usuario == null) {
            this.usuario = BeanIngreso.usuario;
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LinkedList<Rol> getListaRoles() throws SNMPExceptions {
        listaRoles = new RolDB().moTodo();
        return listaRoles;
    }

    public void setListaRoles(LinkedList<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public String getContraActual() {
        return contraActual;
    }

    public void setContraActual(String contraActual) {
        this.contraActual = contraActual;
    }

    public String getContraNueva() {
        return contraNueva;
    }

    public void setContraNueva(String contraNueva) {
        this.contraNueva = contraNueva;
    }

    public String getConfirmaContra() {
        return confirmaContra;
    }

    public void setConfirmaContra(String confirmaContra) {
        this.confirmaContra = confirmaContra;
    }

}
