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
public class clsTipoHabitacion {

    clsJDBCConexion objConexion = new clsJDBCConexion();
    String strSQL;
//    Statement sent;
    ResultSet rs = null;
    Connection con;
    CallableStatement cs = null;

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

    public void modificarTH(Integer cod, String nom, String des, Double costo) throws Exception {
        strSQL = "update tipo_habitacion set nombre='" + nom + "' ,descripcion='" + des + "',costo=" + costo + " where codtipohabitacion=" + cod + "";

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

    //
    public Boolean validarParaEliminar(Integer codTH) throws Exception {
        strSQL = "select * from hospedaje where codhabitacion  in (select codhabitacion from habitacion where codtipohabitacion=" + codTH + ")";
        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return false;

            }

        } catch (Exception e) {
            throw new Exception("Error  al verificar");

        }
        return true;
    }

    public Boolean validarDarBaja(Integer codTH) throws Exception {
        strSQL = "select count(*)=(select count(*) from habitacion where codtipohabitacion= and estado=" + codTH + " )as verificacion from habitacion where codtipohabitacion=" + codTH + "";
        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getBoolean("verificacion");

            }

        } catch (Exception e) {
            throw new Exception("Error  al verificar");

        }
        return false;
    }

    //Eliminar un tipo de habitacion y sus habitaciones 
    public void eliminarTH_Transaccion(Integer cod) throws Exception {
        ArrayList consultas = new ArrayList();
        consultas.add((String) "delete from habitacion where codtipohabitacion=" + cod + "");
        consultas.add((String) "delete from tipo_habitacion where codtipohabitacion=" + cod + "");

        try {
            objConexion.ejecutartBDTransacciones(consultas);
        } catch (Exception e) {
            throw new Exception("Error  al eliminar Tipo de Habitacion");

        }

    }

    public void darBajaTH_Transaccion(Integer cod) throws Exception {
        ArrayList consultas = new ArrayList();
        consultas.add((String) "update habitacion set  estado='M' where codtipohabitacion=" + cod + "");
        consultas.add((String) "update tipo_habitacion set vigencia=false where codtipohabitacion=" + cod + "");

        try {
            objConexion.ejecutartBDTransacciones(consultas);
        } catch (Exception e) {
            throw new Exception("Error  al dar dee baja Tipo de Habitacion");

        }

    }

    public void darAltaTH_Transaccion(Integer cod) throws Exception {
        ArrayList consultas = new ArrayList();
        consultas.add((String) "update habitacion set  estado='D' where codtipohabitacion=" + cod + "");
        consultas.add((String) "update tipo_habitacion set vigencia=true where codtipohabitacion=" + cod + "");

        try {
            objConexion.ejecutartBDTransacciones(consultas);
        } catch (Exception e) {
            throw new Exception("Error  al dar de alta Tipo de Habitacion");

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

    //Llamada a funciones de postgres
    public int totalTipoHab() throws Exception {
        strSQL = "select f_totalTipoHab()";
        try {
            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            if (rs.next()) {
                return rs.getInt("f_totalTipoHab");
            }
        } catch (Exception e) {
            throw new Exception("Error al ejecutra rla funcion");

        } finally {
            objConexion.desconectarBD();
            rs.close();
            cs.close();
        }
        return 0;
    }

    public ResultSet listarTipoHab() throws Exception {
        strSQL = "select * from f_listarTipoHab();";
        try {
            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            return rs;

        } catch (Exception e) {
            throw new Exception("Error al ejecutar rla funcion");

        } finally {
            objConexion.desconectarBD();
            cs.close();
        }
    }
    
    
    

}
