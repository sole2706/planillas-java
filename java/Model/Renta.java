/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author David_Solera
 */
public class Renta {
    int ID;
    float ExentoHasta;
    float Tarifa;

    public Renta(int ID, float ExcentoHasta, float Tarifa) {
        this.ID = ID;
        this.ExentoHasta = ExcentoHasta;
        this.Tarifa = Tarifa;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getExentoHasta() {
        return ExentoHasta;
    }

    public void setExentoHasta(float ExentoHasta) {
        this.ExentoHasta = ExentoHasta;
    }

    public float getTarifa() {
        return Tarifa;
    }

    public void setTarifa(float Tarifa) {
        this.Tarifa = Tarifa;
    }
    

   
}
