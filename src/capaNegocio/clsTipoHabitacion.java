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
public class clsTipoHabitacion {

    clsJDBCConexion objConexion = new clsJDBCConexion();
    String strSQL;
//    Statement sent;
    ResultSet rs = null;

    public Integer generarCodigoTH() throws Exception {
        strSQL = "select coalesce(max(codTipoHabitacion),0)+1 as codigo from tipo_habitacion";

        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de Tipo Habitación!");
        }
        return 0;
    }

    public void registrarTH(Integer cod, String nom, String des, Boolean vig, Double costo) throws Exception {
        strSQL = "insert into tipo_habitacion(codtipohabitacion,nombre,descripcion,vigencia,costo) values (" + cod + ",'" + nom + "','" + des + "'," + vig + "," + costo + ")";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al registrar Tipo de Habitacion");

        }

    }

    public void modificarTH(Integer cod, String nom, String des, Boolean vig, Double costo) throws Exception {
        strSQL = "update tipo_habitacion set nombre='" + nom + "' ,descripcion='" + des + "' ,vigencia=" + vig + " ,costo=" + costo + " where codtipohabitacion=" + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al modificar Tipo de Habitacion");

        }

    }

    public void eliminarTH(Integer cod) throws Exception {
        strSQL = "delete from tipo_habitacion where codtipohabitacion=" + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al eliminar Tipo de Habitacion");

        }

    }

    public void darBajarTH(Integer cod) throws Exception {
        strSQL = "update tipo_habitacion set vigencia=false where codtipohabitacion=" + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al dar de baja Tipo de Habitacion");

        }

    }

    public ResultSet listarTH() throws Exception {
        strSQL = "select * from tipo_habitacion";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar Tipo de Habitacion");

        }

    }

    public ResultSet listarTHVigentes() throws Exception {
        strSQL = "select * from tipo_habitacion where vigencia=true order by nombre";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar Tipo de Habitacion");

        }

    }

    public ResultSet buscarTH(Integer cod) throws Exception {
        strSQL = "select * from tipo_habitacion where codtipohabitacion=" + cod;

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al buscar Tipo de Habitacion");

        }

    }

    public Integer obtenerCodigoTH(String nombre) throws Exception {
        strSQL = "select codtipohabitacion from tipo_habitacion where nombre='" + nombre + "'";
        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getInt("codtipohabitacion");
            }

        } catch (Exception e) {
            throw new Exception("Error  al obtener codigo Tipo de Habitacion");

        }
        return 0;
    }
    
    

}
