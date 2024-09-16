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
import javax.naming.NamingException;

/**
 *
 * @author David_Solera
 */
public class EmpleadoDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;

    public EmpleadoDB() {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public void Insertar(Empleado empleado) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {
            strSQL = String.format("INSERT INTO Empleado VALUES (%d,'%s', %d, %f, '%s', '%s', '%s')",
                    empleado.getID(),empleado.getNombre(), empleado.getIdTipoJornada(), empleado.getSalario(),
                    empleado.isActivo(), empleado.getCedula(), empleado.getTelefono());

            accesoDatos.ejecutaSQL(strSQL);

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public void Actualizar(Empleado empleado) throws SNMPExceptions, SQLException {
        String strSQL = "";

        try {

            strSQL = String.format("UPDATE Empleado SET Nombre = '%s', IdTipoJornada = %d, Salario = %f, Activo = '%s',"
                    + "Cedula = '%s', Telefono = '%s' WHERE ID = %d",
                    empleado.getNombre(), empleado.getIdTipoJornada(), empleado.getSalario(),
                    empleado.isActivo(), empleado.getCedula(), empleado.getTelefono(), empleado.getID());

            //Se ejecuta la sentencia SQL
            accesoDatos.ejecutaSQL(strSQL);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public Empleado getUltima() throws SNMPExceptions, SQLException {
        String select = "SELECT TOP 1 *  FROM Empleado ORDER BY ID desc";

        Empleado empleado = null;

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {

                int ID = rsPA.getInt("ID");
                String Nombre = rsPA.getString("Nombre");
                int IdTipoJornada = rsPA.getInt("IdTipoJornada");
                float Salario = rsPA.getFloat("Salario");
                boolean Activo = rsPA.getBoolean("Activo");
                String Cedula = rsPA.getString("Cedula");
                String Telefono = rsPA.getString("Telefono");

                //se construye el objeto.
                empleado = new Empleado(ID, Nombre, IdTipoJornada, Salario, Activo, Cedula, Telefono);
            }

            rsPA.close(); //se cierra el ResultSet.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }

        return empleado;
    }

    public LinkedList<Empleado> moTodo() throws SNMPExceptions, SQLException {
        String select = "SELECT * FROM Empleado order by ID desc";

        LinkedList<Empleado> lista = new LinkedList<>();

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {

                int ID = rsPA.getInt("ID");
                String Nombre = rsPA.getString("Nombre");
                int IdTipoJornada = rsPA.getInt("IdTipoJornada");
                float Salario = rsPA.getFloat("Salario");
                boolean Activo = rsPA.getBoolean("Activo");
                String Cedula = rsPA.getString("Cedula");
                String Telefono = rsPA.getString("Telefono");

                //se construye el objeto.
                Empleado empleado = new Empleado(ID, Nombre, IdTipoJornada, Salario, Activo, Cedula, Telefono);

                lista.add(empleado);
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

    public Empleado getByID(int id) throws SNMPExceptions, SQLException {
        String select = "SELECT * FROM Empleado WHERE ID =" + id;

        Empleado empleado = null;

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                int ID = rsPA.getInt("ID");
                String Nombre = rsPA.getString("Nombre");
                int IdTipoJornada = rsPA.getInt("IdTipoJornada");
                float Salario = rsPA.getFloat("Salario");
                boolean Activo = rsPA.getBoolean("Activo");
                String Cedula = rsPA.getString("Cedula");
                String Telefono = rsPA.getString("Telefono");

                //se construye el objeto.
                empleado = new Empleado(ID, Nombre, IdTipoJornada, Salario, Activo, Cedula, Telefono);
            }

            //se construye el objeto.
            rsPA.close(); //se cierra el ResultSet.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return empleado;
    }

    public LinkedList<Empleado> getByJornada(int idJornada) throws SNMPExceptions {
        String select = "SELECT * FROM Empleado WHERE IdTipoJornada =" + idJornada;

        LinkedList<Empleado> lista = new LinkedList<>();

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {

                int ID = rsPA.getInt("ID");
                String Nombre = rsPA.getString("Nombre");
                int IdTipoJornada = rsPA.getInt("IdTipoJornada");
                float Salario = rsPA.getFloat("Salario");
                boolean Activo = rsPA.getBoolean("Activo");
                String Cedula = rsPA.getString("Cedula");
                String Telefono = rsPA.getString("Telefono");

                //se construye el objeto.
                Empleado empleado = new Empleado(ID, Nombre, IdTipoJornada, Salario, Activo, Cedula, Telefono);

                lista.add(empleado);
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
}
