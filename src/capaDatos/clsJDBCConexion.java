package capaDatos;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jordan Oxalc Vásquez Fernández , Fecha : 31/08/2021
 */
public class clsJDBCConexion {

    private String driver, url, user, password;
    String strSQL1, strSQL2;

    private Connection con;
    private Statement sent = null;

    //Constructor de clases
    public clsJDBCConexion() {
        this.driver = "org.postgresql.Driver";
        this.url = "jdbc:postgresql://localhost:5432/BDHotel";
        this.user = "postgres";
        this.password = "12345";
        this.con = null;
    }

    //Sobrecargar el constructor
    public clsJDBCConexion(String strBD) {
        this.driver = "org.postgresql.Driver";
        this.url = "jdbc:postgresql://localhost:5432/" + strBD;
        this.user = "postgres";
        this.password = "12345";
        this.con = null;
    }

    //Conexión a BD
    public void conectarBD() throws Exception {
        try {
            Class.forName(this.driver);
            con = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            throw new Exception("Error al conectar a la BD...");
        }

    }

    //Desconexión BD
    public void desconectarBD() throws Exception {
        try {
            con.close();
        } catch (SQLException e) {
            throw new Exception("Error al desconectar a la BD...");
        }

    }

    //Consulta del estado de la conexión   
    public boolean getEstadoConexion() throws SQLException {
        return con.isClosed();
    }

    //Ejecutar consultas en la Base De datos : SELECT
    public ResultSet consultarBD(String strSQL) throws Exception {
        ResultSet rs = null;//almacenar un conjunto de registros 
        try {
            conectarBD();
            sent = con.createStatement();
            rs = sent.executeQuery(strSQL);
            return rs;
        } catch (Exception e) {
            throw new Exception("Error al consultar base de datos... ");
        } finally {
            if (con != null) {
                desconectarBD();
            }
        }
    }

    //Ejecutar INSERT,UPDATE Y DELETE
    public void ejecutarBD(String strSQL) throws Exception {
        try {
            conectarBD();
            sent = con.createStatement();
            sent.executeUpdate(strSQL);
        } catch (Exception e) {
            throw new Exception("Error al ejecutar consulta en la base de datos... ");
        } finally {
            if (con != null) {
                desconectarBD();
            }

        }

    }

        public void ejecutartBDTransacciones(ArrayList arregloConsulta) throws Exception{
        
        try {
            conectarBD();
            con.setAutoCommit(false);//Iniciar la transaccion
            sent = con.createStatement();
            for (Object consulta:arregloConsulta) {
                sent.executeUpdate((String)consulta);
                
            }
            //todas las sentencias de la transaccion
            con.commit();
            con.setAutoCommit(true); //Finaliza o desactiva el manejo de transaccion

        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al ejecutar la transaccion... ");

        }finally {
            if (con != null) {
                desconectarBD();
            }

        }
    
    }
    //Setencias independientes
    public void ejecutarSenteciasBD() throws Exception {
        strSQL1 = "delete from habitacion where codtipohabitacion=4";
        strSQL2 = "delete from tipo_habitacion where codtipohabitacion=4";

        try {
            ejecutarBD(strSQL1);
            ejecutarBD(strSQL2);

        } catch (Exception e) {
            throw new Exception("Error al ejecutar una de las consultas... ");

        }

    }
    //Sentencias
    public void ejecutarTransaccion() throws Exception {
        strSQL1 = "delete from habitacion where codtipohabitacion=4";
        strSQL2 = "delete from tipo_habitacion where codtipohabitacion=4";

        try {
            conectarBD();
            con.setAutoCommit(false);
            sent = con.createStatement();
            sent.executeUpdate(strSQL1);
            sent = con.createStatement();
            sent.executeUpdate(strSQL2);
            //todas las sentencias de la transaccion
            con.commit();
            con.setAutoCommit(true); //Finaliza o desactiva el manejo de transaccion

        } catch (Exception e) {
            con.rollback();
            throw new Exception("Error al ejecutar la transaccion... ");

        }finally {
            if (con != null) {
                desconectarBD();
            }

        }

    }

    public Connection getCon(){
        return con;
    }
}
