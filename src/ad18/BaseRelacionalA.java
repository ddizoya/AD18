/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ad18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jdbc.driver.OracleDriver;

/**
 *
 * @author oracle
 */
public class BaseRelacionalA {

    /**
     * String driver = "jdbc:oracle:thin:"; String host = "//localhost:"; //
     * tambien puede ser una ip como "192.168.1.14" int porto = 1521; String sid
     * = ":orcl";
     *
     * String usuario = "hr"; String password = "hr";
     *
     * String url = driver + host + porto + sid;
     */
    String driver = "jdbc:oracle:thin:";
    String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
    int porto = 1521;
    String tabla = "produtos";
    String sid = "orcl";
    String usuario = "hr";
    String password = "hr";
    String url = "jdbc:oracle:thin:@localhost:1521:orcl";

    Statement st = null;
    ResultSet rs = null;

    public BaseRelacionalA() {
        try {
            DriverManager.deregisterDriver(new OracleDriver());
            System.err.println("*Se ha registrado el Driver de Oracle. ");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Connection conectarse() throws SQLException {
        Connection conn = DriverManager.getConnection(url, usuario, password);
        return conn;

    }

    public void inxerir(String cod, String desc, int prezo) throws SQLException {
        System.out.println(">>Metiendo nueva fila...");
        String consulta = "insert into " + tabla + " values ('" + cod + "','" + desc + "'," + prezo + ")";
        st = conectarse().createStatement();
        st.executeUpdate(consulta);

    }

    public void listado() throws SQLException {
        System.out.println(">>Se procede a consultar la tabla " + tabla);
        String consulta = "Select * from " + tabla;
        st = conectarse().createStatement();
        rs = st.executeQuery(consulta);

        System.out.println("Listado de elementos en " + tabla +":");
        while (rs.next()) {
            String cod = rs.getString("cod");
            String des = rs.getString("descricion");
            int prezo = rs.getInt("prezo");
            System.out.println(cod + " " + des + " " + prezo);

        }
    }

    public void actualicePre(String cod, int prezo) throws SQLException {
        String consulta = "update " + tabla  + " set prezo=" + prezo + " where cod = '" + cod +"'";
        st = conectarse().createStatement();
        st.execute(consulta);
    }
    
    public void borrarTodo() throws SQLException{
        String consulta = "delete from " + tabla;
        st = conectarse().createStatement();
        st.execute(consulta);
        System.out.println(">>El contenido de la tabla ha sido borrado. ");
    }

    public static void main(String[] args) {
        try {
            BaseRelacionalA obj = new BaseRelacionalA();
            //obj.inxerir("p1", "parafusos", 3);
            //obj.inxerir("p2", "cravos", 4);
            //obj.inxerir("p3", "tachas", 6);

            obj.listado();
            
            obj.actualicePre("p1", 99);
            
            obj.listado();
            
            obj.borrarTodo();
            
            obj.listado();

        } catch (SQLException ex) {
            Logger.getLogger(BaseRelacionalA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
