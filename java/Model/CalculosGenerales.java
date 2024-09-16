/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.SNMPExceptions;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author David_Solera
 */
public class CalculosGenerales {

    public static float CalcularRenta(Empleado empleado) throws SNMPExceptions, SQLException {
        //Trae todos los tramos de renta en orden de menor a mayor
        LinkedList<Renta> tramos = new RentaDB().moTodo();
        float salario = empleado.getSalario();

        float total = 0;
        float tramoAnterior = 0;

        for (int i = 0; i < tramos.size(); i++) {
            float hasta = tramos.get(i).getExentoHasta();
            float tarifa = tramos.get(i).getTarifa() / 100;

            //Si el salario es mayor al tramo anterior se debe cobrar este tramo
            if (salario > tramoAnterior) {
                //Si el salario es mayor que el valor hasta el que se extiende
                //se cobra solo desde el tramo anterior hasta el tramo actual
                if (salario > hasta) {
                    total += (hasta - tramoAnterior) * tarifa;
                } //Si el salario es menor que la cifra hasta la que se extiende el
                //tramo actual se cobra hasta donde llega el salario
                else {
                    total += (salario - tramoAnterior) * tarifa;
                }

                tramoAnterior = hasta;
            }
        }

        return total;
    }
}
