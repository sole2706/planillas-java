/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author David_Solera
 */
public class Persona {

    private int idPersona;
    private int idRol;
    private String nombre;
    private String password;

    public Persona() {
    }

    public Persona(int idPersona, int idRol, String nombre, String password) {
        this.idPersona = idPersona;
        this.idRol = idRol;
        this.nombre = nombre;
        this.password = password;
    }

   

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

  

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }

}
