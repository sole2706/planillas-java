/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author David_Solera
 */
public class Beneficio {

     int ID;
    int IdEmpleado;
    String Detalle;
    float Cantidad;
    boolean Fijo;

    public Beneficio(int ID, int IdEmpleado, String Detalle, float Cantidad, boolean Fijo) {
        this.ID = ID;
        this.IdEmpleado = IdEmpleado;
        this.Detalle = Detalle;
        this.Cantidad = Cantidad;
        this.Fijo = Fijo;
    }

    public Beneficio() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIdEmpleado() {
        return IdEmpleado;
    }

    public void setIdEmpleado(int IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String Detalle) {
        this.Detalle = Detalle;
    }

    public float getCantidad() {
        return Cantidad;
    }

    public void setCantidad(float Cantidad) {
        this.Cantidad = Cantidad;
    }

    public boolean isFijo() {
        return Fijo;
    }

    public void setFijo(boolean Fijo) {
        this.Fijo = Fijo;
    }


}
