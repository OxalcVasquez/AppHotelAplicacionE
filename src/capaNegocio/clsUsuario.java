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
 * @author Jordan Oxalc Vásquez Fernández, Fecha : 02/09/2021
 */
public class clsUsuario {

    private clsJDBCConexion objConectar = new clsJDBCConexion();
    private String strSQL = "";
    private ResultSet rs = null;

    //Método de Inicio de Sesión
    public String inicioSesion(String usu, String con) throws Exception {
        strSQL = "select nombresCompleto from usuario where nomusu='" + usu + "' and con='" + con + "'";
        try {
            rs = objConectar.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getString("nombresCompleto");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return "";
    }
    
     public String usuInicioSesion(String usu) throws Exception {
        strSQL = "select nombresCompleto from usuario where nomusu='" + usu + "'";
        try {
            rs = objConectar.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getString("nombresCompleto");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return "";
    }

    //PreguntaSecreta
    public String preguntaSecreta(String usu) throws Exception {
        strSQL = "select pregunta from usuario where nomusu='" + usu + "'";
        try {
            rs = objConectar.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getString("pregunta");
            }

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return "";
    }

    //Validación de vigencia del usuario
    public Boolean validarVigencia(String usu) throws Exception {
        strSQL = "select estado from usuario where nomusu='" + usu + "'";
        try {
            rs = objConectar.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getBoolean("estado");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
        return false;
    }

    public int validarVigenciaExistencia(String usu) throws Exception {
        strSQL = "select estado from usuario where nomusu='" + usu + "'";
        try {
            rs = objConectar.consultarBD(strSQL);
            if (rs.next()) {
                if (rs.getBoolean("estado")) {
                    return 0; //Vigente
                }
            } else {
                return 2; //No vigente
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }
        return 1; // No existente
    }

    //Validar pregunta
    public Boolean validarPregunta(String usu, String rpta) throws Exception {
        strSQL = "select * from usuario where nomusu='" + usu + "' and upper(respuesta)=upper('" + rpta + "')";
        try {
            rs = objConectar.consultarBD(strSQL);
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }

    }

    //Cambiar contraseña
    public void modificarContrasena(String usu, String con) throws Exception {
        strSQL = "update usuario set con='" + con + "' where nomusu = '" + usu + "'";
        try {
            objConectar.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }

    }

}
