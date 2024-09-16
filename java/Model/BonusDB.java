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
public class BonusDB {
   
    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public BonusDB() {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public void Insertar(Bonus bonus) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            strSQL = String.format("INSERT INTO Bonus VALUES ('%s', %f, %d)",
                    bonus.getDetalle(), bonus.getTotal(), bonus.getIdDetallePlanilla());
            accesoDatos.ejecutaSQL(strSQL);

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public LinkedList<Bonus> getFromIdDetallePlanilla(int IdDetallePlanilla) throws SNMPExceptions, SQLException {
        String select = "SELECT * FROM Bonus WHERE IdDetallePlanilla = " + IdDetallePlanilla;

        LinkedList<Bonus> lista = new LinkedList<>();

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {
                int ID = rsPA.getInt("ID");
                String Detalle = rsPA.getString("Detalle");
                float Total = rsPA.getFloat("Total");

                //se construye el objeto.
                Bonus bonus = new Bonus(ID, IdDetallePlanilla, Detalle, Total);

                lista.add(bonus);
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

    public void InsertarOActualizar(Bonus bon) throws SNMPExceptions {
        String select = "SELECT * FROM Bonus WHERE ID = " + bon.getID();

        try {
            accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                //Si entra aquí es porque sí existe un objeto con ese ID
                Actualizar(bon);
                rsPA.close(); //se cierra el ResultSet.
                return;
            }

            //Si llega aquí es porque el objeto no existe y se crea
            Insertar(bon);
            rsPA.close(); //se cierra el ResultSet.
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public void Eliminar(Bonus bon) throws SNMPExceptions {
        String strSQL = "SELECT * FROM Bonus WHERE ID = " + bon.getID();

        try {
            accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);

            while (rsPA.next()) {
                //Si entra aquí es porque sí existe un objeto con ese ID
                strSQL = "DELETE FROM Bonus WHERE ID = " + bon.getID();
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

    private void Actualizar(Bonus bon) throws SNMPExceptions {
        String strSQL = "";

        try {

            strSQL = String.format("UPDATE Bonus SET Detalle = '%s', Total = %f WHERE ID = %d",
                    bon.getDetalle(), bon.getTotal(), bon.getID());

            //Se ejecuta la sentencia SQL
            accesoDatos.ejecutaSQL(strSQL);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }
}
