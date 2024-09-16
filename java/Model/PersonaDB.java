/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.AccesoDatos;
import DAO.SNMPExceptions;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author David_Solera
 */
public class PersonaDB {

    private AccesoDatos accesoDatos;
    private Connection conn;

    public PersonaDB() {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public LinkedList<Persona> ConsultarIdPersona(int idPersonaSesion)
            throws SNMPExceptions, SQLException {

        String strSQL = "";
        LinkedList<Persona> listPersona = new LinkedList<Persona>();

        try {

            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            strSQL = "SELECT ID,IdRol,Nombre,Contrasenna"
                    + "FROM Usuario "
                    + "WHERE ID= " + idPersonaSesion + ";";

            //Se ejecuta la sentencia SQL
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);
            //se llama el array con los proyectos
            while (rsPA.next()) {
                int idPersona = rsPA.getInt("ID");
                int idRol = rsPA.getInt("IdRol");
                String nombre = rsPA.getString("Nombre");
                String password = rsPA.getString("Contrasenna");

                Persona persona = new Persona(idPersona,idRol, nombre,
                        password);

                listPersona.add(persona);
            }
            rsPA.close();

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
        return listPersona;
    }

    public Persona ConsultarPersonaXId(int idPersonaSesion)
            throws SNMPExceptions, SQLException {

        String strSQL = "";
        Persona personaABuscar = null;

        try {

            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            strSQL = "SELECT ID,IdRol,Nombre,Contrasenna"
                    + "FROM Usuario "
                    + "WHERE ID= " + idPersonaSesion + ";";

            //Se ejecuta la sentencia SQL
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);
            //se llama el array con los proyectos
            while (rsPA.next()) {
                int idPersona = rsPA.getInt("ID");
                int idRol = rsPA.getInt("IdRol");
                String nombre = rsPA.getString("Nombre");
                String password = rsPA.getString("Contrasenna");

                Persona persona = new Persona(idPersona, idRol, nombre,
                        password);

                personaABuscar = persona;
            }
            rsPA.close();

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
        return personaABuscar;
    }

    public static Persona ConsultarIdPersonaYContra(int idPersonaSesion, String passwordIdentificar)
            throws SNMPExceptions, SQLException {

        String strSQL = "";
        Persona personaNuevo = null;

        try {

            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            strSQL = "SELECT ID,IdRol, Nombre,Contrasenna "
                    + "FROM Usuario "
                    + "WHERE ID= " + idPersonaSesion + " AND Contrasenna = " + "'" + passwordIdentificar + "';";

            //Se ejecuta la sentencia SQL
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);
            //se llama el array con los proyectos
            while (rsPA.next()) {
                int idPersona = rsPA.getInt("ID");
                int idRol = rsPA.getInt("IdRol");
                String nombre = rsPA.getString("Nombre");
                String password = rsPA.getString("Contrasenna");

                Persona persona = new Persona(idPersona, idRol, nombre,
                        password);

                personaNuevo = persona;
            }
            rsPA.close();

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
        return personaNuevo;
    }
 public void insertarPersona(Persona pPersona)
     throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            //Se obtienen los valores del objeto InventarioProducto
           Persona persona = new Persona();
           persona = pPersona;

            strSQL
                    = "INSERT INTO Usuario(ID, IdRol , nombre,"
                    + " Contrasenna) VALUES "
                    + "(" + persona.getIdPersona()+ ","
                    + "'" + persona.getIdRol()+ "'" + ","
                    + "'" + persona.getNombre()+ "'" + ","
                    + persona.getpassword()+ ","
                    + "'" + ")";

            //Se ejecuta la sentencia SQL
            accesoDatos.ejecutaSQL(strSQL);

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
}

}
