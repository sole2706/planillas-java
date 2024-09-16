/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Persona;
import Model.PersonaDB;
import Model.Usuario;
import Model.UsuarioDB;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author David_Solera
 */
public class BeanPersona {

    private int idPersona;
    private int idRol;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String mensaje;
    private String mensajeError;
    private int idPersonaAutenticar;
    private String passwordPersonaAutenticar;
    private Persona usuarioEnSesion;

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getapellido() {
        return apellido;
    }

    public void setapellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public int getIdPersonaAutenticar() {
        return idPersonaAutenticar;
    }

    public void setIdPersonaAutenticar(int idPersonaAutenticar) {
        this.idPersonaAutenticar = idPersonaAutenticar;
    }

    public String getPasswordPersonaAutenticar() {
        return passwordPersonaAutenticar;
    }

    public void setPasswordPersonaAutenticar(String passwordPersonaAutenticar) {
        this.passwordPersonaAutenticar = passwordPersonaAutenticar;
    }

    public Persona getUsuarioEnSesion() {
        return usuarioEnSesion;
    }

    public void setUsuarioEnSesion(Persona usuarioEnSesion) {
        this.usuarioEnSesion = usuarioEnSesion;
    }

    public void borrarCampos() {
        this.setIdPersona(0);
        this.setNombre("");
        this.setapellido("");
        this.setEmail("");
        this.setPassword("");
        this.setMensaje("");
        this.setMensajeError("");
    }

    public void autenticar()
            throws SNMPExceptions, SQLException {
        this.setMensaje("");
        this.setMensajeError("");
        try {
            PersonaDB perDB = new PersonaDB();
            usuarioEnSesion = PersonaDB.ConsultarIdPersonaYContra(idPersonaAutenticar, passwordPersonaAutenticar);
            if (usuarioEnSesion != null) {
                if (usuarioEnSesion.getIdRol() == 1) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("Inicio_Admin.xhtml");
                } else {
                    if (usuarioEnSesion.getIdRol() == 2) {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("Inicio_RH.xhtml");
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("Inicio_Planillero.xhtml");
                    }
                }

            } else {
                this.setMensajeError("La contraseña o identificación no son correctos. O bien, no ha sido registrado.");
            }

        } catch (Exception e) {

        }

    }

    public void consultarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Persona");

        final ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        final Map<String, Object> session = context.getSessionMap();

        final Persona user = (Persona) session.get("Persona");

        if (user != null) {
            try {
                int userId = user.getIdPersona();
                this.setIdPersona(userId);

                int userIdRol = user.getIdRol();
                this.setIdRol(userIdRol);

                String userNom = user.getNombre();
                this.setNombre(userNom);

                String userContra = user.getpassword();
                this.setPassword(userContra);

            } catch (ClassCastException e) {

            }
        } else {
            context.invalidateSession();

        }

    }

}
