/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author David_Solera
 */
public class PlanillaDB {

     private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public PlanillaDB() {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public void Insertar(Planilla planilla) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            strSQL = String.format("INSERT INTO Planilla VALUES (%d,%d, '%s', '%s', '%s', %d)",
                    planilla.getID(),planilla.getIdTipoJornada(), planilla.getFechaInicio().toString(), planilla.getFechaFinal().toString(),
                    planilla.getFechaPago().toString(), planilla.getIdTipoPlanilla());

            accesoDatos.ejecutaSQL(strSQL);

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public Planilla getUltima() throws SNMPExceptions, SQLException {
        String select = "SELECT TOP 1 *  FROM Planilla ORDER BY ID desc";

        Planilla planilla = null;

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {

                int ID = rsPA.getInt("ID");
                int IdJornada = rsPA.getInt("IdTipoJornada");

                Date FechaInicio = rsPA.getDate("FechaInicio");
                Date FechaFinal = rsPA.getDate("FechaFinal");
                Date FechaPago = rsPA.getDate("FechaPago");
                int IdTipoPlanilla = rsPA.getInt("IdTipoPlanilla");

                //se construye el objeto.
                planilla = new Planilla(ID, IdJornada, FechaInicio, FechaFinal, FechaPago, IdTipoPlanilla);
            }

            rsPA.close(); //se cierra el ResultSet.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }

        return planilla;
    }

    public LinkedList<Planilla> moTodo() throws SNMPExceptions, SQLException {
        String select = "SELECT * FROM Planilla order by ID desc";

        LinkedList<Planilla> listaPlanilla = new LinkedList<Planilla>();

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {

                int ID = rsPA.getInt("ID");
                int IdJornada = rsPA.getInt("IdTipoJornada");

                Date FechaInicio = rsPA.getDate("FechaInicio");
                Date FechaFinal = rsPA.getDate("FechaFinal");
                Date FechaPago = rsPA.getDate("FechaPago");
                int IdTipoPlanilla = rsPA.getInt("IdTipoPlanilla");

                //se construye el objeto.
                Planilla planilla = new Planilla(ID, IdJornada, FechaInicio, FechaFinal, FechaPago, IdTipoPlanilla);

                listaPlanilla.add(planilla);
            }

            rsPA.close(); //se cierra el ResultSet.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }

        return listaPlanilla;
    }

    public Planilla getById(int idPlanilla) throws SNMPExceptions {
        String select = "SELECT * FROM Planilla WHERE ID = " + idPlanilla;

        Planilla planilla = null;

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {

                int ID = rsPA.getInt("ID");
                int IdJornada = rsPA.getInt("IdTipoJornada");

                Date FechaInicio = rsPA.getDate("FechaInicio");
                Date FechaFinal = rsPA.getDate("FechaFinal");
                Date FechaPago = rsPA.getDate("FechaPago");
                int IdTipoPlanilla = rsPA.getInt("IdTipoPlanilla");

                //se construye el objeto.
                planilla = new Planilla(ID, IdJornada, FechaInicio, FechaFinal, FechaPago, IdTipoPlanilla);
            }

            rsPA.close(); //se cierra el ResultSet.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }

        return planilla;
    }
}
