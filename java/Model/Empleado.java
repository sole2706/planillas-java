/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.SNMPExceptions;
import java.sql.SQLException;

/**
 *
 * @author David_Solera
 */
public class Empleado {

    int ID;
    String Nombre;
    int IdTipoJornada;
    float Salario;
    boolean Activo;
    String Cedula;
    String Telefono;

    String jornada;

    public Empleado(int ID, String Nombre, int IdTipoJornada, float Salario, boolean Activo, String Cedula, String Telefono) throws SNMPExceptions, SQLException {
        this.ID = ID;
        this.Nombre = Nombre;
        this.IdTipoJornada = IdTipoJornada;
        this.Salario = Salario;
        this.Activo = Activo;
        this.Cedula = Cedula;
        this.Telefono = Telefono;

        jornada = new TipoJornadaDB().getByID(IdTipoJornada).Nombre;
    }

    public String getCedula() {
        return Cedula;
    }

    public void setCedula(String Cedula) {
        this.Cedula = Cedula;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getIdTipoJornada() {
        return IdTipoJornada;
    }

    public void setIdTipoJornada(int IdTipoJornada) {
        this.IdTipoJornada = IdTipoJornada;
    }

    public float getSalario() {
        return Salario;
    }

    public void setSalario(float Salario) {
        this.Salario = Salario;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean Activo) {
        this.Activo = Activo;
    }

}
