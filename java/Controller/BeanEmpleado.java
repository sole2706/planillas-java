/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.SNMPExceptions;
import Model.Beneficio;
import Model.BeneficioDB;
import Model.Deduccion;
import Model.DeduccionDB;
import Model.Empleado;
import Model.EmpleadoDB;
import Model.Persona;
import Model.PersonaDB;
import Model.TipoJornada;
import Model.TipoJornadaDB;
import Model.TipoPlanilla;
import Model.TipoPlanillaDB;
import Model.Usuario;
import Model.UsuarioDB;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

/**
 *
 * @author David_Solera
 */
public class BeanEmpleado {

    //Lista de los empleados registrados
    private LinkedList<Empleado> listaEmpleados = new LinkedList<>();

    //Objeto empleado para editar
    private Empleado empleado;

    //Propiedades para mostrar la lista de jornadas en el combobox
    private String jornada = "";
    private LinkedList<TipoJornada> listaJornadas;

    //Listas para Mostrar los beneficios y deducciones del empleado
    private LinkedList<Deduccion> listaDeducciones;
    private LinkedList<Beneficio> listaBeneficios;

    //Listas de beneficios y deducciones que el usuario quiera eliminar
    private LinkedList<Deduccion> eliminarDeducciones = new LinkedList<>();
    private LinkedList<Beneficio> eliminarBeneficios = new LinkedList<>();

    private String mensaje = "";

    //Pasa el empleado seleccionado y abre la página de editar
    public void Editar(Empleado empleado) throws IOException {
        this.empleado = empleado;

        try {
            listaJornadas = new TipoJornadaDB().moTodo();
            MostrarDeduccionesYBeneficios();
            mensaje = "";
        } catch (Exception e) {
            mensaje = "Ha ocurrido un error al conectar con la base de datos";
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("EditarEmpleado.xhtml");
    }

    //Guarda los cambios realizados en el empleado y sus deducciones y beneficios
    public void Guardar() throws SNMPExceptions, SQLException {
        for (int i = 0; i < listaJornadas.size(); i++) {
            if (listaJornadas.get(i).getNombre().equals(empleado.getJornada())) {
                empleado.setIdTipoJornada(listaJornadas.get(i).getID());
                break;
            }
        }

        new EmpleadoDB().Actualizar(empleado);

        mensaje = "";

        //Actualiza las deducciones y beneficios que ya existen y los nuevos los inserta
        for (Deduccion ded : listaDeducciones) {
            if (ded.getCantidad() > 0) {
                new DeduccionDB().InsertarOActualizar(ded);
            } else {
                mensaje += "\nEl valor de '" + ded.getDetalle() + "' no se ha guardado por tener un valor menor a 0.";
            }
        }

        for (Beneficio ben : listaBeneficios) {
            if (ben.getCantidad() > 0) {
                new BeneficioDB().InsertarOActualizar(ben);
            } else {
                mensaje += "\nEl valor de '" + ben.getDetalle() + "' no se ha guardado por tener un valor menor a 0.";
            }
        }

        //Elimina de la base de datos las deducciones y beneficios en las listas para eliminar
        for (Deduccion ded : eliminarDeducciones) {
            if (ded.getCantidad() > 0) {
                new DeduccionDB().Eliminar(ded);
            }
        }
        eliminarDeducciones = new LinkedList<>();

        for (Beneficio ben : eliminarBeneficios) {
            if (ben.getCantidad() > 0) {
                new BeneficioDB().Eliminar(ben);
            }
        }
        eliminarBeneficios = new LinkedList<>();

        //Muestra la lista actualizada de deducciones y cambios con los cambios realizados
        empleado = new EmpleadoDB().getByID(empleado.getID());
        MostrarDeduccionesYBeneficios();
    }

    //Añade el objeto a una lista para ser eliminada al guardar
    public void EliminarDeduccion(Deduccion deduccion) {
        eliminarDeducciones.add(deduccion);
        listaDeducciones.remove(deduccion);
    }

    public void EliminarBeneficio(Beneficio beneficio) {
        eliminarBeneficios.add(beneficio);
        listaBeneficios.remove(beneficio);
    }

    public void Deshacer() throws IOException, SNMPExceptions, SQLException {
        Editar(new EmpleadoDB().getByID(this.empleado.getID()));
        eliminarDeducciones = new LinkedList<>();
        eliminarBeneficios = new LinkedList<>();
    }

    //Vuelve a la página de la lista de empleados
    public void Cancelar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("AdministrarEmpleados.xhtml");
    }

    public void MostrarListaEmpleados() throws SNMPExceptions, SQLException {
        this.setListaEmpleados(new EmpleadoDB().moTodo());
    }

    public LinkedList<Empleado> getListaEmpleados() {
        try {
            MostrarListaEmpleados();
            mensaje = "";
        } catch (Exception e) {
            mensaje = "Ha ocurrido un error al conectar con la base de datos";
        }

        return listaEmpleados;
    }

    //Crea un empleado y le añade la deducción de la ccss y la renta
    public void AnadirEmpleado() throws SNMPExceptions, SQLException, IOException {
        int consecutivo = 7;
        consecutivo++;
        new EmpleadoDB().Insertar(new Empleado(consecutivo, "Empleado Nuevo", 1, 0, true, "", "8888-8888"));
        MostrarListaEmpleados();
        Editar(new EmpleadoDB().getUltima());

    }

    public void AnadirDeduccion() {
        this.listaDeducciones.add(new Deduccion(0, empleado.getID(), "Nuevo cobro", 0, false));
    }

    public void AnadirBeneficio() {
        this.listaBeneficios.add(new Beneficio(0, empleado.getID(), "Nuevo beneficio", 0, false));
    }

    public void MostrarDeduccionesYBeneficios() throws SNMPExceptions, SQLException {
        this.setListaDeducciones(new DeduccionDB().getFromIdEmpleado(this.empleado.getID()));
        this.setListaBeneficios(new BeneficioDB().getFromIdEmpleado(this.empleado.getID()));
    }

    public LinkedList<Deduccion> getListaDeducciones() {

        return listaDeducciones;
    }

    public LinkedList<Beneficio> getListaBeneficios() {
        return listaBeneficios;
    }

    public void setListaBeneficios(LinkedList<Beneficio> listaBeneficios) {
        this.listaBeneficios = listaBeneficios;
    }

    public void setListaDeducciones(LinkedList<Deduccion> listaDeducciones) {
        this.listaDeducciones = listaDeducciones;
    }

    public void setListaEmpleados(LinkedList<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public LinkedList<TipoJornada> getListaJornadas() throws SNMPExceptions, SQLException {
        return listaJornadas;
    }

    public void setListaJornadas(LinkedList<TipoJornada> listaJornadas) {
        this.listaJornadas = listaJornadas;
    }
}
