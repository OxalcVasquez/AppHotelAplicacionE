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
 * @author Jordan Oxalc Vásquez Fernández
 */
public class clsHospedaje {

    clsJDBCConexion objConectar = new clsJDBCConexion();
    String strSQL = "";
    ResultSet rs = null;

    public Integer generarNumHospedaje() throws Exception {
        strSQL = "select COALESCE(max(numhospedaje),0)+1 as numero from hospedaje";
        try {
            rs = objConectar.consultarBD(strSQL);
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
            objConectar.ejecutarBD(strSQL);

        } catch (Exception e) {
            throw new Exception("Error al  el registrar el hospedaje");
        }
    }

    public ResultSet listarHospedaje() throws Exception {
        strSQL = "select H.*, HU.nombres || ' ' || HU.apellidos as huesped, E.nombre || ' ' || E.apellidos as empleado, HA.numero as numHab "
                + " from hospedaje H inner join huesped HU on H.codhuesped=HU.codhuesped "
                + " inner join empleado E on H.dniempleado=E.dniempleado inner join habitacion HA on H.codhabitacion=HA.codhabitacion";

        try {
            rs = objConectar.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar hospedjaes");

        }

    }
}
