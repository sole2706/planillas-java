/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Bonus;
import Model.BonusDB;
import Model.DetallePlanilla;
import Model.DetallePlanillaDB;
import Model.Planilla;
import Model.PlanillaDB;
import Model.Rebajo;
import Model.RebajoDB;
import Model.TipoJornada;
import Model.TipoJornadaDB;
import Model.TipoPlanilla;
import Model.TipoPlanillaDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.faces.context.FacesContext;

/**
 *
 * @author David_Solera
 */
public class BeanDetallePlanilla {

      private Planilla planilla;
    private DetallePlanilla detalle;

    private LinkedList<Rebajo> listaRebajos = new LinkedList<>();
    private LinkedList<Bonus> listaBonus = new LinkedList<>();

    private LinkedList<Rebajo> eliminarRebajos = new LinkedList<>();
    private LinkedList<Bonus> eliminarBonus = new LinkedList<>();

    public void CalcularSalario() throws SNMPExceptions, SQLException {
        planilla = new PlanillaDB().getById(detalle.getIdPlanilla());

        TipoJornada tipoJornada = new TipoJornadaDB().getByID(planilla.getIdTipoJornada());
        TipoPlanilla tipoPlanilla = new TipoPlanillaDB().getByID(planilla.getIdTipoPlanilla());

        float SalarioNeto = detalle.getSalarioBruto();

        float salarioHora = detalle.getSalarioBruto() / tipoPlanilla.getDias() / tipoJornada.getHoras();

        SalarioNeto += (salarioHora * detalle.getHorasExtra()) * 1.5f;

        for (Rebajo r : listaRebajos) {
            SalarioNeto -= r.getTotal();
        }
        
        for (Bonus b : listaBonus) {
            SalarioNeto += b.getTotal();
        }
        
        detalle.setSalarioNeto(SalarioNeto);
    }

    public void AnadirRebajo() {
        this.listaRebajos.add(new Rebajo(0, detalle.getID(), "Nuevo Rebajo", 0));
    }

    public void AnadirBonus() {
        this.listaBonus.add(new Bonus(0, detalle.getID(), "Nuevo Bonus", 0));
    }

    public void EliminarRebajo(Rebajo rebajo) {
        eliminarRebajos.add(rebajo);
        listaRebajos.remove(rebajo);
    }

    public void EliminarBonus(Bonus bonus) {
        eliminarBonus.add(bonus);
        listaBonus.remove(bonus);
    }

    public void EditarDetalle(DetallePlanilla detalle) throws IOException, SNMPExceptions, SQLException {
        this.detalle = detalle;
        MostrarRebajosYBonus();
        FacesContext.getCurrentInstance().getExternalContext().redirect("EditarDetalle.xhtml");
    }

    public void Guardar() throws SNMPExceptions, SQLException {
        new DetallePlanillaDB().Actualizar(detalle);

        //Actualiza las deducciones y beneficios que ya existen y los nuevos los inserta
        for (Rebajo reb : listaRebajos) {
            if (reb.getTotal() > 0) {
                new RebajoDB().InsertarOActualizar(reb);
            }
        }

        for (Bonus bon : listaBonus) {
            if (bon.getTotal() > 0) {
                new BonusDB().InsertarOActualizar(bon);
            }
        }

        //Elimina de la base de datos las deducciones y beneficios en las listas para eliminar
        for (Rebajo reb : eliminarRebajos) {
            new RebajoDB().Eliminar(reb);
        }
        eliminarRebajos = new LinkedList<>();

        for (Bonus bon : eliminarBonus) {
            new BonusDB().Eliminar(bon);
        }
        eliminarBonus = new LinkedList<>();

        //Muestra la lista actualizada de deducciones y cambios con los cambios realizados
        detalle = new DetallePlanillaDB().getByID(detalle.getID());
        MostrarRebajosYBonus();
    }

    public void Deshacer() throws IOException, SNMPExceptions, SQLException {
        EditarDetalle(new DetallePlanillaDB().getByID(this.detalle.getID()));
        eliminarRebajos = new LinkedList<>();
        eliminarBonus = new LinkedList<>();
    }

    public void Cancelar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("VerPlanilla.xhtml");
    }

    public DetallePlanilla getDetalle() {
        return detalle;
    }

    private void MostrarRebajosYBonus() throws SNMPExceptions, SQLException {
        this.setListaBonus(new BonusDB().getFromIdDetallePlanilla(detalle.getID()));
        this.setListaRebajos(new RebajoDB().getFromIdDetallePlanilla(detalle.getID()));
    }

    public void setDetalle(DetallePlanilla detalle) {
        this.detalle = detalle;
    }

    public LinkedList<Rebajo> getListaRebajos() {
        return listaRebajos;
    }

    public void setListaRebajos(LinkedList<Rebajo> listaRebajos) {
        this.listaRebajos = listaRebajos;
    }

    public LinkedList<Bonus> getListaBonus() {
        return listaBonus;
    }

    public void setListaBonus(LinkedList<Bonus> listaBonus) {
        this.listaBonus = listaBonus;
    }

}
