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
 * @author Chon
 */
public class DServices {
    private Conexion m_Conexion;
    private Connection m_con;
    private Connection con;
    // Atributos
    private int id;
    private String name;
    private String codigo;
    private String description;
    private boolean status;
    
    public DServices() {
      this.m_Conexion = Conexion.getInstancia();
      this.m_con = m_Conexion.getConexion();
    }
    
    public Conexion getM_Conexion() {
        return m_Conexion;
    }

    public void setM_Conexion(Conexion m_Conexion) {
        this.m_Conexion = m_Conexion;
    }

    public Connection getM_con() {
        return m_con;
    }

    public void setM_con(Connection m_con) {
        this.m_con = m_con;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
   
    public ArrayList<DServices> listServices(int index){
        Statement st;
        ArrayList<DServices> l_services = new ArrayList<>();
        try {
                st = m_con.createStatement();
                String s_sql = "";
                s_sql = "select services.nombre,services.codigo,services.descripcion,services.stado\n"+
                        "from services\n";
                ResultSet r_res = st.executeQuery(s_sql);
                
                while(r_res.next()){
                    DServices i_services = new DServices();
                    i_services.setId(r_res.getInt(1));
                    i_services.setName(r_res.getString(2));
                    i_services.setCodigo(r_res.getString(3));
                    i_services.setDescription(r_res.getString(4));
                    i_services.setStatus(r_res.getBoolean(5));
                    l_services.add(i_services);
                }
                return l_services;
        } catch (SQLException e) {
            Logger.getLogger(DServices.class.getName()).log(Level.SEVERE, null, e);
            return l_services;
        }
     }
     public boolean regCotizaciones() {
        Statement st;
        Statement stmt;
        Statement stmr;
        Statement stmd;
        ResultSet rs;
        ResultSet rr;
        
        String id;
        String idr = "";
       
        try {
            st=m_con.createStatement();
            stmt = m_con.createStatement();
            stmr = m_con.createStatement();
            stmd = m_con.createStatement();
            id="";      
            String s_sql="insert into services (nombre,codigo,descripcion,estado)\n"
                    + " values ('" + getName()+"',"+ getCodigo()+","+ getDescription()+","
                    + "'" +isStatus()+"','";
            
            
//            String s_sql="insert into producto (descripcion,precio,stock,medida,marca,modelo,anio,estado,fecha,hora,deleted_at,created_at,updated_at)\n" +
//                        "values\n" +
//                        "('Huntas',800,5,'120 gramos','YOITOKY','COROLLA','1995','A',now(),now(),now(),now(),now())";
//            
            int n_res= st.executeUpdate(s_sql);
            
            rs = stmt.executeQuery("select max(id) from services ");
            while (rs.next()) {               // Posicionar el cursor                
                    id = rs.getString(1);       // Recuperar el valor de columna
                    System.out.println("Valor de columna de identidad = " + id);
                                  // Imprimir el valor de columna
                    }
                    rs.close();                       // Cerrar el ResultSet                
                    stmt.close();   
            
                
           int idp = Integer.parseInt(id); 
           System.out.println("Valor de columna de identidad = " + idp);
           
           
            
            if ((n_res==1)){
                System.out.println("insertado exitoso");
                return true;
            }
            
            System.out.println("no se pudo insertar en el modelo");
            return false;
        } catch (SQLException ex) {
            
            Logger.getLogger(DServices.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
   
    }
     public boolean editCotizaciones() {
        Statement  st;
        Statement stmt;
        ResultSet rs;
        ResultSet rr;
        Statement stmr;
        Statement stmd;
        String id;
        String idr="";
        try {
            
            st=m_con.createStatement();
            stmt = m_con.createStatement();
            stmr = m_con.createStatement();
            stmd = m_con.createStatement();
            id="";      
         
           // rs = stmt.executeQuery("select idpersona from cliente where idcliente="+getIdcliente());
            rs = stmt.executeQuery("select id from services where id="+getId()+"");
            while (rs.next()) {               // Posicionar el cursor                
                    id = rs.getString(1);       // Recuperar el valor de columna
                    System.out.println("Valor de columna de identidad = " + id);
                                  // Imprimir el valor de columna
                    }
                    rs.close();                       // Cerrar el ResultSet                
                    stmt.close();   
            
                
           int idp = Integer.parseInt(id); 
            System.out.println("Valor de columna de identidad = " + idp);
           String s_sql="UPDATE services \n" +
                         "SET  nombre='"+getName()+"', codigo="+getCodigo()+", \n" +
                         "descripcion='"+getDescription()+"',estado='"+isStatus()+"',\n" 
                         +"WHERE id="+idp; 
//            String s_sql="UPDATE users \n" +
//                         "SET  nombre='roger', \n" +
//                         "usuario='roger',email='rogetp7845@hotmail.com', updated_at=now() \n"+
//                         "WHERE id="+idp;  
           int n_res1= st.executeUpdate(s_sql);
            
                     
            if ((n_res1==1)){
                System.out.println("editado exitoso");
                return true;
            }
            
            System.out.println("no se pudo editar el servicio en el modelo");
            return false;
        } catch (SQLException ex) {
            
            Logger.getLogger(DServices.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } 
    } 
     public boolean delCotizaciones (){
        Statement  st;
        Statement stmt;
        ResultSet rs;
        String id;
        try {
           st=m_con.createStatement();
            stmt = m_con.createStatement();
            id="";      
            String s_sql="UPDATE services\n" +
                         "SET estado='false'\n" +
                         "WHERE id="+getId(); 
            /*String s_sql="UPDATE cliente\n" +
                         "SET del_estado='A' \n" +
                         "WHERE idCliente=1";*/
            int n_res= st.executeUpdate(s_sql);
            
            rs = stmt.executeQuery("select id from services where id="+getId());
            while (rs.next()) {               // Posicionar el cursor                
                    id = rs.getString(1);       // Recuperar el valor de columna
                    System.out.println("Valor de columna de identidad = " + id);
                                  // Imprimir el valor de columna
                    }
                    rs.close();                       // Cerrar el ResultSet                
                    stmt.close();   
            
                
           int idp = Integer.parseInt(id); 
           System.out.println("Valor de columna de identidad = " + idp);
           
            if ((n_res==1)){
                System.out.println("eliminado exitoso");
                return true;
            }
            
            System.out.println("no se pudo dar de baja la cotización en el modelo");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DServices.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     public static void main(String[] args) {
         


    }
}
