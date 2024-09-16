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
public class DetallePlanilla {

    int ID;
    int IdEmpleado;
    int IdPlanilla;
    
    float HorasTrabajadas;
    float SalarioBruto;
    float HorasExtra;
    float SalarioNeto;
    
    String Empleado;

    public DetallePlanilla(int ID, int IdEmpleado, int IdPlanilla, float HorasTrabajadas, float SalarioBruto, float HorasExtra, float SalarioNeto) throws SNMPExceptions, SQLException {
        this.ID = ID;
        this.IdEmpleado = IdEmpleado;
        this.IdPlanilla = IdPlanilla;
        this.HorasTrabajadas = HorasTrabajadas;
        this.SalarioBruto = SalarioBruto;
        this.HorasExtra = HorasExtra;
        this.SalarioNeto = SalarioNeto;
        
        Empleado = new EmpleadoDB().getByID(IdEmpleado).Nombre;
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

    public String getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(String Empleado) {
        this.Empleado = Empleado;
    }

    public void setIdEmpleado(int IdEmpleado) {
        this.IdEmpleado = IdEmpleado;
    }

    public int getIdPlanilla() {
        return IdPlanilla;
    }

    public void setIdPlanilla(int IdPlanilla) {
        this.IdPlanilla = IdPlanilla;
    }

    public float getHorasTrabajadas() {
        return HorasTrabajadas;
    }

    public void setHorasTrabajadas(float HorasTrabajadas) {
        this.HorasTrabajadas = HorasTrabajadas;
    }

    public float getSalarioBruto() {
        return SalarioBruto;
    }

    public void setSalarioBruto(float SalarioBruto) {
        this.SalarioBruto = SalarioBruto;
    }

    public float getHorasExtra() {
        return HorasExtra;
    }

    public void setHorasExtra(float HorasExtra) {
        this.HorasExtra = HorasExtra;
    }

    public float getSalarioNeto() {
        return SalarioNeto;
    }

    public void setSalarioNeto(float SalarioNeto) {
        this.SalarioNeto = SalarioNeto;
    }
}
