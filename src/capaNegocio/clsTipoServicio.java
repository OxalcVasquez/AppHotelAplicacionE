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
 * @author Jordan Oxalc Vásquez Fernández 9-22
 */
public class clsTipoServicio {

    clsJDBCConexion objConexion = new clsJDBCConexion();
    String strSQL;
//    Statement sent;
    ResultSet rs = null;

    public Integer generarCodigoTS() throws Exception {
        strSQL = "select coalesce(max(codtiposer),0)+1 as codigo from tipo_servicio";

        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código de Tipo Servicio!");
        }
        return 0;
    }

    public void registrarTS(Integer cod, String nom, Boolean vig, Double costobase) throws Exception {
        strSQL = "insert into tipo_servicio(codtiposer,nombre,vigencia,costobase) values (" + cod + ",'" + nom + "'," + vig + "," + costobase + ")";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al registrar Tipo Servicio");

        }

    }

    public void modificarTS(Integer cod, String nom, Boolean vig, Double costobase) throws Exception {
        strSQL = "update tipo_servicio set nombre='" + nom + "' ,vigencia=" + vig + " ,costobase=" + costobase + " where codtiposer="+ cod+"";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al modificar Tipo Servicio");

        }

    }

    public void eliminarTS(Integer cod) throws Exception {
        strSQL = "delete from tipo_servicio where codtiposer=" + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al eliminar Tipo Servicio");

        }

    }

    public void darBajarTS(Integer cod) throws Exception {
        strSQL = "update tipo_servicio set vigencia=false where codtiposer=" + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al dar de baja Tipo de Servicio");

        }

    }

    public ResultSet listarTS() throws Exception {
        strSQL = "select * from tipo_servicio";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar Tipo de Habitacion");

        }

    }
    
    
       public ResultSet buscarTS(Integer cod) throws Exception {
        strSQL = "select * from tipo_servicio where codtiposer="+cod;

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al buscar Tipo Servicio");

        }

    }

}
