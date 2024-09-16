/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author David_Solera
 */
public class Bonus {

    int ID;
    int IdDetallePlanilla;
    String Detalle;
    float Total;

    public Bonus(int ID, int IdDetallePlanilla, String Detalle, float Total) {
        this.ID = ID;
        this.IdDetallePlanilla = IdDetallePlanilla;
        this.Detalle = Detalle;
        this.Total = Total;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIdDetallePlanilla() {
        return IdDetallePlanilla;
    }

    public void setIdDetallePlanilla(int IdDetallePlanilla) {
        this.IdDetallePlanilla = IdDetallePlanilla;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String Detalle) {
        this.Detalle = Detalle;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }
    
}
