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
public class UsuarioDB {

    private AccesoDatos accesoDatos = new AccesoDatos();
    private Connection conn;
    // manejar un arrelgo que se encarga de trasladar los registros de resultser a la pagina

    public UsuarioDB(Connection conn) {
        accesoDatos = new AccesoDatos();
        accesoDatos.setDbConn(conn);
    }

    public UsuarioDB() {
        super();
    }

    public LinkedList<Usuario> ConsultarTodosUsuarios()
            throws SNMPExceptions, SQLException {

        String strSQL = "";
        LinkedList<Usuario> listUsuario = new LinkedList<Usuario>();

        try {

            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            strSQL = "SELECT idRol,idUsuario "
                    + "FROM Usuario" + ";";

            //Se ejecuta la sentencia SQL
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);
            //se llama el array con los proyectos
            while (rsPA.next()) {
                int idRol = rsPA.getInt("idRol");
                int idUsuario = rsPA.getInt("idUsuario");

                Usuario usuarioSiguiente = new Usuario(idRol, "",idUsuario);

                listUsuario.add(usuarioSiguiente);
            }
            rsPA.close();

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
        return listUsuario;
    }

    public Usuario ConsultarUsuarioXId(int pIdUsuario)
            throws SNMPExceptions, SQLException {

        String strSQL = "";
        Usuario user = null;

        try {

            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            strSQL = "SELECT idRol,idUsuario "
                    + "FROM Usuario WHERE idUsuario = " + pIdUsuario + ";";

            //Se ejecuta la sentencia SQL
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(strSQL);
            //se llama el array con los proyectos
            while (rsPA.next()) {
                int idRol = rsPA.getInt("idRol");
                int idPersona = rsPA.getInt("idUsuario");

                Usuario usuarioEncontrado = new Usuario(idRol,"", idPersona);

                user = usuarioEncontrado;
            }
            rsPA.close();

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        } finally {

        }
        return user;
    }

     public void Actualizar(Usuario usuario) throws SNMPExceptions {
        String strSQL = "";

        try {

            strSQL = String.format("UPDATE Usuario SET Nombre = '%s', IdRol = %d WHERE ID = %d",
                    usuario.getNombre(), usuario.getIdRol(), usuario.getID());

            //Se ejecuta la sentencia SQL
            accesoDatos.ejecutaSQL(strSQL);
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
    }

    public boolean Insertar(Usuario usuario, String contrasenna) throws SNMPExceptions {
        String select = String.format("EXECUTE CrearUsuario @Usuario = '%s', @IdRol = %d, @Contrasenna = '%s'",
                usuario.getNombre(), usuario.getIdRol(), contrasenna);

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                return rsPA.getBoolean("Aprobado");
            }

            rsPA.close(); //se cierra el ResultSet.
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }

        return false;
    }

    //Utiliza el stored procedure de la base de datos, retorna true si se cambió exitosamente
    public boolean CambiarContra(int ID, String vieja, String nueva) throws SNMPExceptions {
        String select = String.format("EXECUTE CambiarContrasenna @ID = %d, "
                + "@ContrasennaVieja = '%s', @ContrasennaNueva = '%s'",
                ID, vieja, nueva);

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                return rsPA.getBoolean("Aprobado");
            }

            rsPA.close(); //se cierra el ResultSet.
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }

        return false;
    }
 public boolean VerificarUsuario(String Usuario, String Contrasenna) throws SNMPExceptions {
        String select = String.format("EXECUTE VerificarLogin @Usuario = '%s', @Contrasenna = '%s'", Usuario, Contrasenna);

        try {
            AccesoDatos accesoDatos = new AccesoDatos();

            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                return rsPA.getBoolean("Aprobado");
            }

            //se construye el objeto.
            rsPA.close(); //se cierra el ResultSet.
        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }

        return false;
    }

    public Usuario getByNombre(String nombre) throws SNMPExceptions, SQLException {
        String select = "SELECT * FROM Usuario WHERE Nombre = '" + nombre + "'";

        Usuario usuario = null;

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                int ID = rsPA.getInt("ID");
                String Nombre = rsPA.getString("Nombre");
                int IdRol = rsPA.getInt("IdRol");

                usuario = new Usuario(ID, Nombre, IdRol);
            }

            //se construye el objeto.
            rsPA.close(); //se cierra el ResultSet.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return usuario;
    }

    public Usuario getByID(int ID) throws SNMPExceptions {
        String select = "SELECT * FROM Usuario WHERE ID = " + ID;

        Usuario usuario = null;

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            while (rsPA.next()) {
                String Nombre = rsPA.getString("Nombre");
                int IdRol = rsPA.getInt("IdRol");

                usuario = new Usuario(ID, Nombre, IdRol);
            }

            //se construye el objeto.
            rsPA.close(); //se cierra el ResultSet.

        } catch (SQLException e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION,
                    e.getMessage(), e.getErrorCode());
        } catch (Exception e) {
            throw new SNMPExceptions(SNMPExceptions.SQL_EXCEPTION, e.getMessage());
        }
        return usuario;
    }

    public LinkedList<Usuario> moTodo() throws SNMPExceptions {
        String select = "SELECT * FROM Usuario order by ID desc";

        LinkedList<Usuario> lista = new LinkedList<>();

        try {
            //Se intancia la clase de acceso a datos
            AccesoDatos accesoDatos = new AccesoDatos();

            //se ejecuta la sentencia sql
            ResultSet rsPA = accesoDatos.ejecutaSQLRetornaRS(select);

            //se llama el array con los proyectos
            while (rsPA.next()) {

                int ID = rsPA.getInt("ID");
                String Nombre = rsPA.getString("Nombre");
                int IdRol = rsPA.getInt("IdRol");

                Usuario usuario = new Usuario(ID, Nombre, IdRol);
                lista.add(usuario);
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
