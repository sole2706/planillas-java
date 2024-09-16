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
public class DeduccionDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public DeduccionDB() {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public LinkedList<Deduccion> getFromIdEmpleado(int IdEmpleado) throws SNMPExceptions, SQLException {
        String select = "SELECT * FROM Deduccion WHERE IdEmpleado = " + IdEmpleado;

        LinkedList<Deduccion> lista = new LinkedList<>();

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {

                int ID = rsPA.getInt("ID");
                String Detalle = rsPA.getString("Detalle");
                float Cantidad = rsPA.getFloat("Cantidad");
                boolean Fijo = rsPA.getBoolean("Fijo");

                //se construye el objeto.
                Deduccion deduccion = new Deduccion(ID, IdEmpleado, Detalle, Cantidad, Fijo);

                lista.add(deduccion);
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

    public void InsertarOActualizar(Deduccion ded)
            throws SNMPExceptions, SQLException {
        String select = "SELECT * FROM Deduccion WHERE ID = " + ded.getID();

        try {
            accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                //Si entra aquí es porque sí existe un objeto con ese ID
                Actualizar(ded);
                rsPA.close(); //se cierra el ResultSet.
                return;
            }

            //Si llega aquí es porque el objeto no existe y se crea
            Insertar(ded);
            rsPA.close(); //se cierra el ResultSet.
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public void Insertar(Deduccion deduccion) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            strSQL = String.format("INSERT INTO Deduccion VALUES (%d, '%s', %f, '%s')",
                    deduccion.getIdEmpleado(), deduccion.getDetalle(), deduccion.getCantidad(), deduccion.isFijo());
            accesoDatos.ejecutaSQL(strSQL);

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public void Actualizar(Deduccion deduccion) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {

            strSQL = String.format("UPDATE Deduccion SET IdEmpleado = %d, Detalle = '%s', Cantidad = %f, Fijo = '%s' WHERE ID = %d",
                    deduccion.getIdEmpleado(), deduccion.getDetalle(), deduccion.getCantidad(), deduccion.isFijo(), deduccion.getID());

            //Se ejecuta la sentencia SQL
            accesoDatos.ejecutaSQL(strSQL);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public void Eliminar(Deduccion ded) throws SNMPExceptions {
        String strSQL = "SELECT * FROM Deduccion WHERE ID = " + ded.getID();

        try {
            accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);

            while (rsPA.next()) {
                //Si entra aquí es porque sí existe un objeto con ese ID
                strSQL = "DELETE FROM Deduccion WHERE ID = " + ded.getID();
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
}
