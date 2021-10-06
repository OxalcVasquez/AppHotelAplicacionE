/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaNegocio;

import capaDatos.clsJDBCConexion;
import java.sql.*;

/**
 *
 * @author Jordan Oxalc Vásquez Fernández 23-09
 */
public class clsHabitacion {

    clsJDBCConexion objConexion = new clsJDBCConexion();
    String strSQL;
//    Statement sent;
    ResultSet rs = null;

    public ResultSet listarHabitacion() throws Exception {
        strSQL = "select H.*,th.nombre as nombreth from habitacion h inner join tipo_habitacion th on h.codtipohabitacion = th.codtipohabitacion;";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar Habitaciones");

        }

    }

    //Método creado el 9/30
    public ResultSet listarHabitacionesDisponibles(String tipoHabitacion) throws Exception {
        strSQL = "select H.*,th.nombre as nombreth from habitacion h inner join tipo_habitacion th"
                + " on h.codtipohabitacion = th.codtipohabitacion where estado='D' and th.nombre = '" + tipoHabitacion + "'";;

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar Habitaciones");

        }

    }

    public Integer generarCodigoH() throws Exception {
        strSQL = "select coalesce(max(codhabitacion),0)+1 as codigo from habitacion";

        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de Habitación!");
        }
        return 0;
    }

    public Boolean verificarNumHabitacion(Integer numH) throws Exception {
        strSQL = "select * from habitacion where numero = " + numH + "";
        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            throw new Exception("Error al verificar numero de Habitación!");

        }
        return true;
    }

    public Boolean verificarDisponibilidad(Integer cod) throws Exception {
        strSQL = "select estado from habitacion where codhabitacion = " + cod + " and estado='O'";
        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            throw new Exception("Error al verificar disponibilidad de Habitación!");

        }
        return true;
    }

    public void registrarHabitacion(Integer cod, Integer numero, String nom, String des, char est, Integer capacidad, Integer codtipo) throws Exception {
        strSQL = "insert into habitacion(codhabitacion, numero, nombre, descripcion, estado, capacidad, codtipohabitacion) "
                + "values (" + cod + ", " + numero + ", '" + nom + "', '" + des + "', '" + est + "', " + capacidad + ", " + codtipo + ")";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al registrar  Habitacion");

        }

    }

    public void modificarHabitacion(Integer cod, String nom, String des, char est, Integer capacidad, Integer codtipo) throws Exception {
        strSQL = "update habitacion set nombre='" + nom + "', descripcion='" + des + "', estado= '" + est + "' , capacidad=" + capacidad + ", codtipohabitacion=" + codtipo + " where codhabitacion = " + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al modificar  Habitacion");

        }

    }

    public void darDeBajaHabitacion(Integer cod) throws Exception {
        strSQL = "update habitacion set  estado='M' where codhabitacion = " + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al modificar  Habitacion");

        }

    }

    public void eliminarHabitacion(Integer cod) throws Exception {
        strSQL = "delete from habitacion where codhabitacion = " + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al eliminar  Habitacion");

        }

    }

    public ResultSet buscarHabitacion(Integer num) throws Exception {
        strSQL = "select H.*,th.nombre as nombreth from habitacion h inner join tipo_habitacion th on h.codtipohabitacion = th.codtipohabitacion where numero =" + num;

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al buscar habitacion");

        }

    }

    public Integer buscarCodHabitacion(Integer num) throws Exception {
        strSQL = "select codhabitacion from habitacion where numero =" + num;

        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getInt("codhabitacion");
            }

        } catch (Exception e) {
            throw new Exception("Error  al obtener costo de habitacion");

        }

        return 0;

    }

    public Double obtenerCostoHab(Integer numHabitacion) throws Exception {
        strSQL = "select th.costo from habitacion h inner join tipo_habitacion th on h.codtipohabitacion = th.codtipohabitacion where numero =" + numHabitacion;

        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getDouble("costo");
            }

        } catch (Exception e) {
            throw new Exception("Error  al obtener costo de habitacion");

        }

        return 0.0;
    }

}
