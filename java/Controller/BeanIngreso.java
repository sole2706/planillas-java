/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Usuario;
import Model.UsuarioDB;
import java.io.IOException;
import java.sql.SQLException;
import javax.faces.context.FacesContext;

/**
 *
 * @author David_Solera
 */
public class BeanIngreso {
    
    String nombre = "";
    String clave = "";

    String mensaje = "";

    static Usuario usuario = null;

   

    public Usuario getUsuario() {
        
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
