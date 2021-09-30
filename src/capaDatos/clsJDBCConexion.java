package capaDatos;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Jordan Oxalc Vásquez Fernández , Fecha : 31/08/2021
 */
public class clsJDBCConexion {

    private String driver, url, user, password;
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
}
