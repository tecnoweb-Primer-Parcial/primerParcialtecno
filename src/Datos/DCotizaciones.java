/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

/**
 * 
 *
 * @author Chon
 */
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DCotizaciones {
    private Conexion m_Conexion;
    private Connection m_con;
    private Connection con;
    
      // Atributos
    private int id;
    private String cod;
    private String cliente;
    private String fechaCot;
    private String ejecutivo;
    private int tarifa;
    private int idservice;
    private int idStatus;
    private int idcom;

    public int getIdcom() {
        return idcom;
    }

    public void setIdcom(int idcom) {
        this.idcom = idcom;
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

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFechaCot() {
        return fechaCot;
    }

    public void setFechaCot(String fechaCot) {
        this.fechaCot = fechaCot;
    }

    public String getEjecutivo() {
        return ejecutivo;
    }

    public void setEjecutivo(String ejecutivo) {
        this.ejecutivo = ejecutivo;
    }

    public int getTarifa() {
        return tarifa;
    }

    public void setTarifa(int tarifa) {
        this.tarifa = tarifa;
    }

    public int getIdservice() {
        return idservice;
    }

    public void setIdservice(int idservice) {
        this.idservice = idservice;
    }

    public int getIdStatus() {
        return idStatus;
    }

  
    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }
    
    public DCotizaciones() {
      this.m_Conexion = Conexion.getInstancia();
      this.m_con = m_Conexion.getConexion();
    }
     public ArrayList<DCotizaciones> listCotizaciones(){
        Statement st;
        ArrayList<DCotizaciones> l_cotizaciones = new ArrayList<>();
        try {
                st = m_con.createStatement();
                String s_sql = "";
                s_sql = "select cotizaciones.cod,cotizaciones.cliente,cotizaciones.fechaCot,cotizaciones.ejecutivo, \n"
                        + "cotizaciones.tarifa,cotizaciones.idservice \n"+
                        "from cotizaciones, services, status \n"+
                        "where cotizaciones.idservice=services.id and cotizaciones.isstatus=status.id";
                ResultSet r_res = st.executeQuery(s_sql);
                
                while(r_res.next()){
                    DCotizaciones i_cotizaciones = new DCotizaciones();
                    i_cotizaciones.setCod(r_res.getString(1));
                    i_cotizaciones.setCliente(r_res.getString(2));
                    i_cotizaciones.setFechaCot(r_res.getString(3));
                    i_cotizaciones.setEjecutivo(r_res.getString(4));
                    i_cotizaciones.setTarifa(r_res.getInt(5));
                    i_cotizaciones.setIdservice(r_res.getInt(6));
                    l_cotizaciones.add(i_cotizaciones);
                }
                return l_cotizaciones;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE, null, e);
            return l_cotizaciones;
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
            String s_sql="insert into producto (cod,cliente,fechaCot,ejecutivo,tarifa,idservice,isStatus,idcom)\n"
                    + " values ('" + getCod()+"',"+ getCliente()+","+ getFechaCot()+"," + "'" +getEjecutivo() + "'" +getTarifa()+"','"+ getIdservice()+"','"+getIdStatus()+"','"+ "'" +getIdcom()+ "'";
            
            
//            String s_sql="insert into producto (descripcion,precio,stock,medida,marca,modelo,anio,estado,fecha,hora,deleted_at,created_at,updated_at)\n" +
//                        "values\n" +
//                        "('Huntas',800,5,'120 gramos','YOITOKY','COROLLA','1995','A',now(),now(),now(),now(),now())";
//            
            int n_res= st.executeUpdate(s_sql);
            
            rs = stmt.executeQuery("select max(id) from cotizaciones ");
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
            
            Logger.getLogger(DCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
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
            rs = stmt.executeQuery("select id from cotizaciones where id="+getId()+"");
            while (rs.next()) {               // Posicionar el cursor                
                    id = rs.getString(1);       // Recuperar el valor de columna
                    System.out.println("Valor de columna de identidad = " + id);
                                  // Imprimir el valor de columna
            }
            rs.close();                       // Cerrar el ResultSet                
            stmt.close();   
            
                
           //int idp = Integer.parseInt(id); 
            //System.out.println("Valor de columna de identidad = " + idp);
           String s_sql="UPDATE cotizaciones \n" +
                         "SET  cod='"+getCod()+"', cliente='"+getCliente()+"', \n" +
                         "fechacot='"+getFechaCot()+"',ejecutivo='"+getEjecutivo()+"',\n" 
                   +"tarifa="+getTarifa()+" \n"+
                         "WHERE id="+getId(); 
//            String s_sql="UPDATE users \n" +
//                         "SET  nombre='roger', \n" +
//                         "usuario='roger',email='rogetp7845@hotmail.com', updated_at=now() \n"+
//                         "WHERE id="+idp;  
           int n_res1= st.executeUpdate(s_sql);
            
                     
            if (n_res1==1){
                System.out.println("editado exitoso");
                return true;
            }
            
            System.out.println("no se pudo editar la cotización en el modelo");
            return false;
        } catch (SQLException ex) {
            
            Logger.getLogger(DCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
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
            String s_sql="UPDATE cotizaciones\n" +
                         "SET estado='false'\n" +
                         "WHERE id="+getId(); 
            /*String s_sql="UPDATE cliente\n" +
                         "SET del_estado='A' \n" +
                         "WHERE idCliente=1";*/
            int n_res= st.executeUpdate(s_sql);
            
            rs = stmt.executeQuery("select id from cotizaciones where id="+getId());
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
            Logger.getLogger(DCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    public static void main(String[] args) {
       // TODO code application logic here
       DCotizaciones cli=new DCotizaciones();
       
       //registrar una cotizacion
       /*cli.setCod("OMX-242");
       cli.setCliente("Sofia");
       cli.setFechaCot("2021-07-25");
       cli.setEjecutivo("Kelly Escobar");
       cli.setTarifa(2000);
       cli.setIdservice(2);
       cli.setIdStatus(2);
       cli.setIdcom(3);
        System.out.println(cli.regCotizaciones());*/
       
       //actualizar una cotizacion
       cli.setCod("OMX-240");
       cli.setCliente("sr maria");
       cli.setFechaCot("2021-07-24");
       cli.setEjecutivo("Kelly escobar");
       cli.setTarifa(2500);
       cli.setId(5);
        System.out.println(cli.editCotizaciones());

       System.out.println(cli.listCotizaciones());
       for (int i = 0; i < cli.listCotizaciones().size(); i++) {
           System.out.println(cli.listCotizaciones().get(i).getEjecutivo());
       }
    
    }
}
