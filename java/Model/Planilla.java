/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.SNMPExceptions;
import java.sql.Date;
import java.sql.SQLException;

/**
 *
 * @author David_Solera
 */
public class Planilla {

   int ID;
    int IdTipoJornada;
    Date FechaInicio;
    Date FechaFinal;
    Date FechaPago;
    int IdTipoPlanilla;

    String jornada;
    String tipo;

    public Planilla(int ID, int IdTipoJornada, Date FechaInicio, Date FechaFinal, Date FechaPago, int IdTipoPlanilla) throws SNMPExceptions, SQLException {
        this.ID = ID;
        this.IdTipoJornada = IdTipoJornada;
        this.FechaInicio = FechaInicio;
        this.FechaFinal = FechaFinal;
        this.FechaPago = FechaPago;
        this.IdTipoPlanilla = IdTipoPlanilla;

        jornada = new TipoJornadaDB().getByID(IdTipoJornada).Nombre;
        tipo = new TipoPlanillaDB().getByID(IdTipoPlanilla).Nombre;
    }
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIdTipoPlanilla() {
        return IdTipoPlanilla;
    }

    public void setIdTipoPlanilla(int IdTipoPlanilla) {
        this.IdTipoPlanilla = IdTipoPlanilla;
    }

    public int getIdTipoJornada() {
        return IdTipoJornada;
    }

    public void setIdTipoJornada(int IdTipoJornada) {
        this.IdTipoJornada = IdTipoJornada;
    }

    public Date getFechaInicio() {
        return FechaInicio;
    }

    public void setFechaInicio(Date FechaInicio) {
        this.FechaInicio = FechaInicio;
    }

    public Date getFechaFinal() {
        return FechaFinal;
    }

    public void setFechaFinal(Date FechaFinal) {
        this.FechaFinal = FechaFinal;
    }

    public Date getFechaPago() {
        return FechaPago;
    }

    public void setFechaPago(Date FechaPago) {
        this.FechaPago = FechaPago;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
