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

    private clsJDBCConexion objConexion = new clsJDBCConexion();
    private String strSQL = "";
    private ResultSet rs = null;
    CallableStatement cs = null;
    Connection con;

    //Método de Inicio de Sesión
    public String inicioSesion(String usu, String con) throws Exception {
        strSQL = "select nombresCompleto from usuario where nomusu='" + usu + "' and con='" + con + "'";
        try {
            rs = objConexion.consultarBD(strSQL);
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
            rs = objConexion.consultarBD(strSQL);
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
            rs = objConexion.consultarBD(strSQL);
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
            rs = objConexion.consultarBD(strSQL);
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
            rs = objConexion.consultarBD(strSQL);
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
            rs = objConexion.consultarBD(strSQL);
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
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }

    }

    public Boolean modificar(String ncon, String usu) throws Exception {
        strSQL = "{? = call f_cambiar_contrasena(?,?)}";
        try {
            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            cs.setString(2, ncon);
            cs.setString(3, usu);
            cs.registerOutParameter(1, Types.BOOLEAN);
            cs.execute();
            return cs.getBoolean(1);

        } catch (Exception e) {
            throw new Exception("Error al cambiar");

        } finally {
            objConexion.desconectarBD();
            cs.close();
        }

    }

    private String cambiarContra(String contra, String user) throws Exception{
         strSQL = "{call f_cambiarContraseña(?,?)}";
        try {
            objConexion.conectarBD(); //ConectaBd
            con = objConexion.getCon(); //Jala Conexión de CapaDatos
            cs = con.prepareCall(strSQL);//Prepara la función
            cs.setString(1,contra);
            cs.setString(2,user);
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.executeUpdate();
            String respuesta=cs.getString(1);
            return respuesta;
        } catch (Exception e) {
            throw new Exception("Error al ejecutar la función\n" + e);
        } finally { //Siempre que crea una conexión hay que liberar memoria.
            objConexion.desconectarBD(); //con.close()
            cs.close();
            //rs.close(); si returnas resulset no se cierra el ResultSet
        }
        
    }

    
}
