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
public class clsEmpleado {

    clsJDBCConexion objConexion = new clsJDBCConexion();
    String strSQL;
//    Statement sent;
    ResultSet rs = null;

    
     public ResultSet listarEmpleado() throws Exception {
        strSQL = "select * from empleado";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar empleado");

        }

    }
     
     
       public ResultSet buscarEmpleado(String dni) throws Exception {
        strSQL = "select * from empleado where dniempleado='"+dni+"'";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al buscar  empleado");

        }

    }
    public Boolean verificarDNI(String dni) throws Exception {
        strSQL = "select * from empleado where dniempleado = '" + dni + "'";
        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            throw new Exception("Error al verificar dni de empleado!");

        }
        return true;
    }

    public void registrarEmpleado(String dni, String nom, String ape, String dir, String tel, String cor, String fecha, Boolean sexo, Boolean vig) throws Exception {
        strSQL = "INSERT INTO empleado(dniempleado, nombre, apellidos, direccion, telefono, correo, fechanac, sexo, vigencia)"
                + " VALUES ('" + dni + "', '" + nom + "', '" + ape + "', '" + dir + "', '" + tel + "','" + cor + "', '" + fecha + "', " + sexo + ", " + vig + ");";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al registrar  empleado");

        }

    }

    public void modificarEmpleado(String dni, String nom, String ape, String dir, String tel, String cor, String fecha, Boolean sexo, Boolean vig) throws Exception {
        strSQL = "UPDATE empleado SET nombre='" + nom + "', apellidos='" + ape + "', direccion='" + dir + "', telefono='" + tel + "', correo='" + cor + "', fechanac='" + fecha + "', sexo=" + sexo + ", vigencia= " + vig + " WHERE dniempleado='" + dni + "'";
        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al modificar  empleado");

        }

    }

    public void eliminarEmpleado(String dni) throws Exception {
        strSQL = "DELETE FROM empleado WHERE dniempleado='" + dni + "'";
        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al eliminar  empleado");

        }

    }

    public void darBajarEmpleado(String dni) throws Exception {
        strSQL = "UPDATE empleado set vigencia=false WHERE dniempleado='" + dni + "'";
        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al dar de baja  empleado");

        }

    }
    
    public ResultSet listarEmpleadosVigentes() throws Exception {
        strSQL = "select * from empleado where vigencia=true order by nombre";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar empleados");

        }

    }
    
   

}