/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Oni
 */
public class DRutas {
    private final Conexion m_Conexion;
    private final Connection m_con;
    
    // Atributos
    
    private int id;
    private String nombreRuta;
    
    public DRutas() {
      this.m_Conexion = Conexion.getInstancia();
      this.m_con = m_Conexion.getConexion();
    }
    public ArrayList<DRutas> listRutas() {
        Statement st;
        ArrayList<DRutas> l_Ingreso=new ArrayList<>();
        try {
            
            st=m_con.createStatement();
            String s_sql="select id,nombreRuta " +
                "    from rutas where estado=true " ;
            ResultSet r_res=st.executeQuery(s_sql);
            
            while (r_res.next()) {                
                DRutas i_ruta=new DRutas();                
                    i_ruta.setId(r_res.getInt(1));                 
                    i_ruta.setNombreRuta(r_res.getString(2));
                l_Ingreso.add(i_ruta);
            } 
            
            return l_Ingreso;
        } catch (SQLException ex) {
            
           Logger.getLogger(DRutas.class.getName()).log(Level.SEVERE, null, ex);
            return l_Ingreso;
        }
    }
    
    public boolean regRuta (){
        try {
            Statement st=m_con.createStatement();
            
            String s_sql="insert into rutas(nombreRuta,estado)\n" +
                "values  ('"+this.getNombreRuta()+"',true)";
                  
            
            if (st.executeUpdate(s_sql)==1){
                System.out.println("insertado exitoso");
                return true;
            }
            
            System.out.println("no se pudo insertar en el modelo");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DRutas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
   
    }
    
    public boolean editRutas(){
        Statement st;
        Statement stmt;
        ResultSet rs;
        try {
            st=m_con.createStatement();
            stmt = m_con.createStatement();
            String id="";
         
            rs = stmt.executeQuery("select id from rutas where id="+getId());
            while (rs.next()) {               // Posicionar el cursor                
                    id = rs.getString(1);       // Recuperar el valor de columna
                    System.out.println("Valor de columna de identidad = " + id);
                                  // Imprimir el valor de columna
                    }
                    rs.close();                       // Cerrar el ResultSet                
                    stmt.close();   
                    
           String s_sql="UPDATE rutas\n" +
                "SET nombreruta='"+getNombreRuta()+"'\n"+
                   "WHERE id="+getId();
            
            if (st.executeUpdate(s_sql)==1){
                System.out.println("editado exitoso");
                return true;
            }
            
            System.out.println("no se pudo editar en el modelo");
            return false;
        } catch (SQLException ex) {
            
            Logger.getLogger(DRutas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
   
    }  
    
    public boolean delRutas(){
        Statement  st;
       
        try {
            st=m_con.createStatement();
            String s_sql="UPDATE rutas\n" +
                         "SET estado=false\n" +
                         "WHERE id="+getId(); 
            
            if (st.executeUpdate(s_sql)==1) {
                System.out.println("eliminado exitoso");
                return true;
            }
            
            System.out.println("no se pudo dar de baja a la promocion en el modelo");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DRutas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
    }
    /*
    public static void main(String[] args) {
        DRutas cli=new DRutas();
        
//        cli.setNombreRuta("SC");
//        System.out.println(cli.regRuta());
//        
//        cli.setId(6);
//        cli.setNombreRuta("CB");
//        System.out.println(cli.editRutas());
        cli.setId(7);
        System.out.println(cli.delRutas());
        cli.setId(8);
        System.out.println(cli.delRutas());
        cli.setId(9);
        System.out.println(cli.delRutas());
       
        
       System.out.println(cli.listRutas());
        for (int i = 0; i < cli.listRutas().size(); i++) {
           System.out.println(cli.listRutas().get(i).getId());
           System.out.println(cli.listRutas().get(i).getNombreRuta());
       }
    
    }*/
    
    
}
