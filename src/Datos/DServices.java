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
   
    public ArrayList<DServices> listServices(){
        Statement st;
        ArrayList<DServices> l_services = new ArrayList<>();
        try {
                st = m_con.createStatement();
                String s_sql = "";
                s_sql = "SELECT id, nombre, codigo, descripcion, estado\n" +
                        "FROM public.services\n";
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
     public boolean regServices() {
        try {
            Statement st1 = m_con.createStatement();
            String s_sql="INSERT INTO public.services(\n" +
                         "nombre, codigo, descripcion, estado)\n"
                        +" values ('" + getName()+"','"+ getCodigo()+"','"+ getDescription()+"',true)";
            
            if(st1.executeUpdate(s_sql)==1){
                System.out.println("Servicio registrado exitosamente!");
                return true;
            }
            System.out.println("No se pudo registrar el Servicio!");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DServices.class.getName()).log(Level.SEVERE,null,e);
            return false;
        }
    }
     public boolean editServices() {
        Statement st1;
        Statement stmt1;
        ResultSet rs1;
        try {
            st1 = m_con.createStatement();
            stmt1 = m_con.createStatement();
            String id1 = "";
            rs1 = stmt1.executeQuery("select id from services where services.id="+getId());
            while(rs1.next()){
                id1 = rs1.getString(1);
                System.out.println("valor de la columna Services "+id1);
            }
            rs1.close();
            stmt1.close();
            String s_sqll;
           String s_sql1 = "update services \n"+
                    "set nombre='"+getName()+"',codigo='"+getCodigo()+"',descripcion='"+getDescription()+"' where services.id="+getId();               
            
            if(st1.executeUpdate(s_sql1)==1){
                System.out.println("Se actualizó con exito");
                return true;
            }
            System.out.println("No se pudo actualizar");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DServices.class.getName()).log(Level.SEVERE,null,e);
            return false;
        } 
    } 
     public boolean delServices (){
        Statement st;
        try {
            st = m_con.createStatement();
            String s_sql = "UPDATE services \n"+
                           "set estado=false \n"+
                           "where id="+getId();
            if(st.executeUpdate(s_sql)==1){
                System.out.println("Servicio eliminado");
                return true;
            }
            System.out.println("No se pudo eliminar el Servicio");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DServices.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
     public static void main(String[] args) {
         
         DServices cli=new DServices();
         // SHIFT + CONTROL + C == EDITAR VARIAS LINEAS SELECCIONADAS
         //LISTAR SERVICIOS
//         System.out.println(cli.listServices());
//       for (int i = 0; i < cli.listServices().size(); i++) {
//           System.out.println(cli.listServices().get(i).getName());
//       }

         //REGISTRAR SERVICIOS
//         cli.setName("MudanzasMundial");
//         cli.setCodigo("MUND");
//         cli.setDescription("Mudanzas mundiales con costo adicional");
//         System.out.println(cli.regServices());
         //ACTUALIZAR SERVICIOS
         cli.setName("MU LOCALES");
         cli.setCodigo("MXM");
         cli.setDescription("SIUOQSANAGSM");
         cli.setId(1);
         System.out.println(cli.editServices());
         //ELIMINAR SERVICIOS
//           cli.setId(3);
//           System.out.println(cli.delServices());
         
    }
}
