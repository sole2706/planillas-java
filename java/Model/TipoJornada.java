/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author David_Solera
 */
public class TipoJornada {
    int ID = 0;
    String Nombre;
    float horas;

    public TipoJornada(int ID, String Nombre, float horas) {
        this.ID = ID;
        this.Nombre = Nombre;
        this.horas = horas;
    }

    public TipoJornada() {
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

    public float getHoras() {
        return horas;
    }

    public void setHoras(float horas) {
        this.horas = horas;
    }

    @Override
    public String toString() {
        return Nombre;
    }
     
}
