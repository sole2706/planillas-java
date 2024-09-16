/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author David_Solera
 */
public class RebajoDB {
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public RebajoDB() {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public void Insertar(Rebajo rebajo) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            strSQL = String.format("INSERT INTO Rebajo VALUES (%d, '%s', %f)",
                    rebajo.getIdDetallePlanilla(), rebajo.getDetalle(), rebajo.getTotal());
            accesoDatos.ejecutaSQL(strSQL);

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public LinkedList<Rebajo> getFromIdDetallePlanilla(int IdDetallePlanilla) throws SNMPExceptions, SQLException {
        String select = "SELECT * FROM Rebajo WHERE IdDetallePlanilla = " + IdDetallePlanilla;

        LinkedList<Rebajo> lista = new LinkedList<>();

        try {
            //Se intancia la clase de acceso a datos
            accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {
                int ID = rsPA.getInt("ID");
                String Detalle = rsPA.getString("Detalle");
                float Total = rsPA.getFloat("Total");

                //se construye el objeto.
                Rebajo rebajo = new Rebajo(ID, IdDetallePlanilla, Detalle, Total);

                lista.add(rebajo);
            }

            rsPA.close(); //se cierra el ResultSet.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return lista;
    }

    public void InsertarOActualizar(Rebajo reb) throws SNMPExceptions {
        String select = "SELECT * FROM Rebajo WHERE ID = " + reb.getID();

        try {
            accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                //Si entra aquí es porque sí existe un objeto con ese ID
                Actualizar(reb);
                rsPA.close(); //se cierra el ResultSet.
                return;
            }

            //Si llega aquí es porque el objeto no existe y se crea
            Insertar(reb);
            rsPA.close(); //se cierra el ResultSet.
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public void Eliminar(Rebajo reb) throws SNMPExceptions {
        String strSQL = "SELECT * FROM Rebajo WHERE ID = " + reb.getID();

        try {
            accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);

            while (rsPA.next()) {
                //Si entra aquí es porque sí existe un objeto con ese ID
                strSQL = "DELETE FROM Rebajo WHERE ID = " + reb.getID();
                accesoDatos.ejecutaSQL(strSQL);
            }

            rsPA.close(); //se cierra el ResultSet.
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    private void Actualizar(Rebajo reb) throws SNMPExceptions {
        String strSQL = "";

        try {

            strSQL = String.format("UPDATE Rebajo SET Detalle = '%s', Total = %f WHERE ID = %d",
                    reb.getDetalle(), reb.getTotal(), reb.getID());

            //Se ejecuta la sentencia SQL
            accesoDatos.ejecutaSQL(strSQL);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
}
