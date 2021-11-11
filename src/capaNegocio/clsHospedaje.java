/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capaNegocio;

import capaDatos.clsJDBCConexion;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Jordan Oxalc Vásquez Fernández
 */
public class clsHospedaje {

    clsJDBCConexion objConexion = new clsJDBCConexion();
    String strSQL = "";
    ResultSet rs = null;
    CallableStatement cs = null;
    Connection con;

    public Integer generarNumHospedaje() throws Exception {
        strSQL = "select COALESCE(max(numhospedaje),0)+1 as numero from hospedaje";
        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getInt("numero");
            }

        } catch (Exception e) {
            throw new Exception("Error al generar el número de hospedaje");
        }
        return 0;
    }

    public void registrarHospedaje(Integer num, String fecInicio, String fecFin, String mot, Double cos, Integer codHue, String dniEm, Integer codHabitacion, String obs) throws Exception {

        strSQL = "INSERT INTO hospedaje(numhospedaje, fechainicio, fechafin, motivo, costo, estadohos, estadopagohos, codhuesped, dniempleado, codhabitacion, observacion) "
                + " VALUES (" + num + ",'" + fecInicio + "','" + fecFin + "','" + mot + "'," + cos + ", true, false, " + codHue + ", '" + dniEm + "'," + codHabitacion + ",' " + obs + "')";

        try {
            objConexion.ejecutarBD(strSQL);

        } catch (Exception e) {
            throw new Exception("Error al  el registrar el hospedaje");
        }
    }

    public ResultSet listarHospedaje() throws Exception {
        strSQL = "select H.*, HU.nombres || ' ' || HU.apellidos as huesped, E.nombre || ' ' || E.apellidos as empleado, HA.numero as numHab "
                + " from hospedaje H inner join huesped HU on H.codhuesped=HU.codhuesped "
                + " inner join empleado E on H.dniempleado=E.dniempleado inner join habitacion HA on H.codhabitacion=HA.codhabitacion";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar hospedjaes");

        }

    }

    public ResultSet buscarHospedaje(Integer num) throws Exception {
        strSQL = "select H.*,HU.tipodocumento,Hu.numdocumento, E.dniempleado, HA.numero as numHab "
                + " from hospedaje H inner join huesped HU on H.codhuesped=HU.codhuesped "
                + " inner join empleado E on H.dniempleado=E.dniempleado inner join habitacion HA on H.codhabitacion=HA.codhabitacion"
                + " where H.numhospedaje=" + num;

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al buscar hospedjaes");

        }

    }

    public void modificarHospedaje(Integer num, String fecInicio, String fecFin, String mot, Double cos, Integer codHue, String dniEm, Integer codHabitacion, String obs) throws Exception {
        strSQL = "UPDATE hospedaje SET  fechainicio='" + fecInicio + "', fechafin='" + fecFin + "', motivo='" + mot + "', costo=" + cos + ", codhuesped=" + codHue + ", dniempleado='" + dniEm + "', codhabitacion=" + codHabitacion + ", observacion=' " + obs + "'"
                + " WHERE numhospedaje=" + num;

        try {
            objConexion.ejecutarBD(strSQL);

        } catch (Exception e) {
            throw new Exception("Error al modificar el hospedaje");
        }
    }

    public void eliminarHospedaje(Integer num) throws Exception {
        strSQL = "DELETE FROM  hospedaje WHERE numhospedaje=" + num;

        try {
            objConexion.ejecutarBD(strSQL);

        } catch (Exception e) {
            throw new Exception("Error al eliminar el hospedaje");
        }
    }

    public void eliminarHospedajeF(Integer num) throws Exception {
        strSQL = "{call f_eliminar_hospedaje(?)}";
        try {
            objConexion.conectarBD(); //ConectaBd
            con = objConexion.getCon(); //Jala Conexión de CapaDatos
            cs = con.prepareCall(strSQL);//Prepara la función
            cs.setInt(1, num);;
            cs.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Error al eliminar el hospedaje");
        }
    }

    //Funciones Laboratorio : listar hospedajes vigentes
    public ResultSet listarHospedajeVigentes() throws Exception {
        strSQL = "select * from f_listarHosVigentes()";
        try {

            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar hospedjaes");

        }

    }

    public ResultSet buscasHospedajeFn(Integer num) throws Exception {
        strSQL = "select * from f_buscarHos(" + num + ")";
        try {

            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al buscar hospedjaes");

        }

    }

    //Transaccion finalizas horspedaje
    public void finalizarHospedaje(Integer num) throws Exception {
        ArrayList consultas = new ArrayList();
        consultas.add((String) "update hospedaje set estadohos=false,estadopagohos=true where numhospedaje=" + num + "");
        consultas.add((String) "update habitacion set  estado='D' where codhabitacion = (select codhabitacion from hospedaje  where numhospedaje=" + num + ")");
        try {
            objConexion.ejecutartBDTransacciones(consultas);
        } catch (Exception e) {
            throw new Exception("Error  al finalizar hospedaje");

        }

    }
    
    //Funncio listar por fecha y estado
     public ResultSet listarHospedajesPorFechaEst(String fechaI, Boolean est) throws Exception {
        strSQL = "select * from f_listarHosPorFechaEst('"+fechaI+"',"+est+")";
        try {

            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar hospedjaes");

        }

    }
    
}
