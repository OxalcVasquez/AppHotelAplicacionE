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
public class clsHuesped {

    clsJDBCConexion objConexion = new clsJDBCConexion();
    String strSQL;
//    Statement sent;
    ResultSet rs = null;
    CallableStatement cs = null;
    Connection con;

    public Integer generarCodigoHuesped() throws Exception {
        strSQL = "select coalesce(max(codhuesped),0)+1 as codigo from huesped";

        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getInt("codigo");
            }
        } catch (Exception e) {
            throw new Exception("Error al generar código del Huesped!");
        }
        return 0;
    }

    public Boolean verficarDocumento(String ndoc, char td) throws Exception {
        strSQL = "SELECT * FROM huesped WHERE numdocumento='" + ndoc + "' and tipodocumento='" + td + "'";

        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return false;
            }
        } catch (Exception e) {
            throw new Exception("Error  al verificar doc Huesped");

        }
        return true;
    }

    public void registrarHuesped(Integer cod, String numDoc, char td, String nom, String ape, String fecha, Boolean sexo, String dir, String ciu, String pa, String telf, String correo, Boolean est) throws Exception {
        strSQL = "INSERT INTO huesped(codhuesped, numdocumento, tipodocumento, nombres, apellidos, fechanac, sexo, direccion, ciudad, pais, telefono, correo, estado) "
                + " VALUES (" + cod + ", '" + numDoc + "', '" + td + "', '" + nom + "', '" + ape + "', '" + fecha + "', " + sexo + ", '" + dir + "', '" + ciu + "', '" + pa + "', '" + telf + "', '" + correo + "', " + est + ")";
        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al registrar huesped");

        }

    }

    public void modificarHuesped(Integer cod, String numDoc, char td, String nom, String ape, String fecha, Boolean sexo, String dir, String ciu, String pa, String telf, String correo, Boolean est) throws Exception {
        strSQL = "UPDATE huesped SET  numdocumento='" + numDoc + "', tipodocumento='" + td + "', nombres='" + nom + "', apellidos='" + ape + "', fechanac='" + fecha + "', sexo=" + sexo + ", direccion='" + dir + "', ciudad='" + ciu + "', pais='" + pa + "', telefono='" + telf + "', correo='" + correo + "', estado= " + est + " "
                + " WHERE codhuesped =" + cod;
        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al modificar Tipo de Habitacion");

        }

    }

    public void eliminarHuesped(Integer cod) throws Exception {
        strSQL = "delete from huesped where codhuesped=" + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al eliminar huesped");

        }

    }

    public void darBajarHuesped(Integer cod) throws Exception {
        strSQL = "update huesped set estado=false where codhuesped=" + cod + "";

        try {
            objConexion.ejecutarBD(strSQL);
        } catch (Exception e) {
            throw new Exception("Error  al dar de baja Huesped");

        }

    }

    public ResultSet listarHuesped() throws Exception {
        strSQL = "select * from huesped";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar Huesped");

        }

    }

    public ResultSet listarHuepedVigentes() throws Exception {
        strSQL = "select * from huesped where estado=true order by nombres";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al listar de husped");

        }

    }

    public ResultSet buscarHuesped(String rnodoc, char td) throws Exception {
        strSQL = "SELECT * FROM huesped WHERE numdocumento='" + rnodoc + "' and tipodocumento='" + td + "'";

        try {
            rs = objConexion.consultarBD(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error  al buscar Huesped");

        }

    }

    public String eliminarHuespedDNI(String dni) throws Exception {
        strSQL = "{call f_eliminar_huesped_dni(?)}";
        try {
            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            cs.setString(1, dni);
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.execute();
            return cs.getString(1);

        } catch (Exception e) {
            throw new Exception("Error  al eliminar");

        } finally {
            objConexion.desconectarBD();
            cs.close();
        }

    }

    public ResultSet listarHuespedAlfabeticamente() throws Exception {
        strSQL = "select * from f_listarHuespedesAlfabeticamente()";
        try {
            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al ejecutar la función!");
        } finally {
            objConexion.desconectarBD();
        }
    }

    public ResultSet listarHespedEstado(Boolean est) throws Exception {
        CallableStatement cs = null;
        strSQL = "select * from f_listarEstado(" + est + ")";
        try {
            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al listar huesped por estado");
        } finally {
            objConexion.desconectarBD();
            cs.close();
        }
    }

    public String eliminarH(String doc) throws Exception {
        strSQL = "select f_EliminarHuesped('" + doc + "')";
        try {
            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            rs = cs.executeQuery();
            if (rs.next()) {
                return rs.getString("f_EliminarHuesped");
            }
        } catch (Exception e) {
            throw new Exception("Error al ejecutar funcion");
        } finally {
            objConexion.desconectarBD();
            cs.close();
        }
        return "";

    }

    //Regisrrar huesped funcion

    
    public String pa_registrarHuesped(Integer cod, String numDoc, String nom, String ape, char td,  Date fecha, Boolean sexo, String dir, String ciu, String pa, String telf, String correo, Boolean est) throws Exception {
//        strSQL = "INSERT INTO huesped(codhuesped, numdocumento, tipodocumento, nombres, apellidos, fechanac, sexo, direccion, ciudad, pais, telefono, correo, estado) "
//                + " VALUES (" + cod + ", '" + numDoc + "', '" + td + "', '" + nom + "', '" + ape + "', '" + fecha + "', " + sexo + ", '" + dir + "', '" + ciu + "', '" + pa + "', '" + telf + "', '" + correo + "', " + est + ")";
//               strSQL = "select pa_registrarHuesped(" + cod + ", '" + numDoc + "', '" + td + "', '" + nom + "', '" + ape + "', '" + fecha + "', " + sexo + ", '" + dir + "', '" + ciu + "', '" + pa + "', '" + telf + "', '" + correo + "', " + est + ");";

        strSQL = "{?= call pa_registrarHuesped1(?, ?, ?,?, ?,?, ?, ?, ?, ?, ?, ?, ?)}";

        try {
            objConexion.conectarBD();
            con = objConexion.getCon();
            cs = con.prepareCall(strSQL);
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.setInt(2, cod);
            cs.setString(3, numDoc);
            cs.setString(4, String.valueOf(td));
            cs.setString(5, nom);
            cs.setString(6, ape);
            cs.setDate(7, (java.sql.Date) fecha);
            cs.setBoolean(8, sexo);
            cs.setString(9, dir);
            cs.setString(10, ciu);
            cs.setString(11, pa);
            cs.setString(12, telf);
            cs.setString(13, correo);
            cs.setBoolean(14, est);
            cs.executeUpdate();
            return cs.getString(1);
        } catch (Exception e) {
            throw new Exception("Error  al registrar huesped");

        }

    }
    

    public String pa_registrarHuesped1(Integer cod, String numDoc, char td, String nom, String ape, String fecha, Boolean sexo, String dir, String ciu, String pa, String telf, String correo, Boolean est) throws Exception {
//        strSQL = "INSERT INTO huesped(codhuesped, numdocumento, tipodocumento, nombres, apellidos, fechanac, sexo, direccion, ciudad, pais, telefono, correo, estado) "
//                + " VALUES (" + cod + ", '" + numDoc + "', '" + td + "', '" + nom + "', '" + ape + "', '" + fecha + "', " + sexo + ", '" + dir + "', '" + ciu + "', '" + pa + "', '" + telf + "', '" + correo + "', " + est + ")";
        strSQL = "select pa_registrarHuesped(" + cod + ", '" + numDoc + "', '" + td + "', '" + nom + "', '" + ape + "', '" + fecha + "', " + sexo + ", '" + dir + "', '" + ciu + "', '" + pa + "', '" + telf + "', '" + correo + "', " + est + ");";

//        strSQL = "{? = call pa_registrarHuesped(?, ?, ?,?, ?,?, ?, ?, ?, ?, ?, ?, ?)};";
        try {
            rs = objConexion.consultarBD(strSQL);
            if (rs.next()) {
                return rs.getString("pa_registrarHuesped");

            }
        } catch (Exception e) {
            throw new Exception("Error  al registrar huesped");

        }
        return " ";
    }

}
