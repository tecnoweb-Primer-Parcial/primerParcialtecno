/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hp
 */
public class Conexion {
     String URL_DB = "jdbc:postgresql://www.tecnoweb.org.bo:5432/";
    // local
    //String URL_DB = "jdbc:postgresql://localhost:5432/";
    String USER_DB = "grupo05sa";
    String NAME_DB = "db_grupo05sa";
    String PASS_DB = "grup005grup005";
    
    private Connection con = null;
    private static Conexion conexion;

    private Conexion()  {
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL_DB + NAME_DB, USER_DB, PASS_DB);
            if (con != null) {
                System.out.println("conexion exitosa a db_grupo05sa..!!!");
            } else {
                System.out.println("error en la conexion .!!!");
            }
        } catch (ClassNotFoundException ex) { //error de driver
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {  // error de conexion a bd
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static Conexion getInstancia() {
        if (conexion == null) {
            conexion = new Conexion();
        }
        return conexion;
    }

    public Connection getConexion() {
        return con;
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
         Conexion mConexion=Conexion.getInstancia();
    }
}
