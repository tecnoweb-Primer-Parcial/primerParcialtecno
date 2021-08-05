/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;
import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Hp
 */
public class DEmpresa {
    //variables para la conexion a la base de datos
    private Conexion m_Conexion;
    private Connection m_con;
    private Connection con;
    
    //atributos de la tabla compania
    private int id;
    private String nombre;
    private String nit;
    private String razonsocial;
    private String sitioweb;
    private String direccion;
    private int telf;
    private int fax;
    private String ubicacion;
    private String sector;
    private String tipo;
    private boolean estado;
    
    public DEmpresa(){
        this.m_Conexion = Conexion.getInstancia();
        this.m_con = m_Conexion.getConexion();
    }
    
    public ArrayList<DEmpresa> listEmpresa(){
        Statement st;
        ArrayList<DEmpresa> listEmpresas = new ArrayList<>();
        try {
            st = m_con.createStatement();
            String s_sql = "select company.nombre,company.nit,company.razonsocial,company.direccion,company.telf,company.ubicacion,company.sector,company.tipo \n"+
                    "from company";
            ResultSet r_res = st.executeQuery(s_sql);
            while(r_res.next()){
                DEmpresa i_empresa = new DEmpresa();
                i_empresa.setNombre(r_res.getString(1));
                i_empresa.setNit(r_res.getString(2));
                i_empresa.setRazonsocial(r_res.getString(3));
                i_empresa.setDireccion(r_res.getString(4));
                i_empresa.setTelf(r_res.getInt(5));
                i_empresa.setUbicacion(r_res.getString(6));
                i_empresa.setSector(r_res.getString(7));
                i_empresa.setTipo(r_res.getString(8));
                listEmpresas.add(i_empresa);
            }
            return listEmpresas;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE, null, e);
            return listEmpresas;
        }
    }
    
    public boolean regEmpresa(){
        try {
            Statement st = m_con.createStatement();
            String s_sql = "insert into company(nombre,nit,razonSocial,sitiWeb,direccion,telf,fax,ubicacion,sector,tipo,estado) \n"+
                    "values('"+this.getNombre()+"','"+this.getNit()+"','"+this.getRazonsocial()+"','"+this.getSitioweb()+"','"+this.getDireccion()+"',"+this.getTelf()+","+this.getFax()+",'"+this.getUbicacion()+"','"+this.getSector()+"','"+this.getTipo()+"',true)";
            if(st.executeUpdate(s_sql)==1){
                System.out.println("Empresa registrada correctamente!");
                return true;
            }
            System.out.println("No se pudo registrar la empresa!");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE,null,e);
            return false;
        }
    }
    
    public boolean updateEmpresa(){
        Statement st;
        Statement stmt;
        ResultSet rs;
        try {
            st = m_con.createStatement();
            stmt = m_con.createStatement();
            String id = "";
            rs = stmt.executeQuery("select id from company where id="+getId());
            while(rs.next()){
                id = rs.getString(1);
                System.out.println("El valor de la empresa es "+id);
            }
            rs.close();
            stmt.close();
            String s_sql = "update company \n"+
                    "set nombre='"+getNombre()+"',nit='"+getNit()+"',razonsocial='"+getRazonsocial()+"',sitiweb='"+getSitioweb()+"',direccion='"+getDireccion()+"',telf="+getTelf()+",fax="+getFax()+",ubicacion='"+getUbicacion()+"',sector='"+getSector()+"',tipo='"+getTipo()+"' \n"+
                    "where company.id="+getId();
            if(st.executeUpdate(s_sql)==1){
                System.out.println("Se edito correctamente!");
                return true;
            }
            System.out.println("No se pudo actualizar!");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE,null,e);
            return false;
        }
    }
    
    public boolean delEmpresa(){
        Statement st;
        try {
            st = m_con.createStatement();
            String s_sql = "update company \n"+
                    "set estado=false \n"+
                    "where id="+getId();
            if(st.executeUpdate(s_sql)==1){
                System.out.println("Empresa eliminada!");
                return true;
            }
            System.out.println("No se pudo eliminar la empresa!");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DRutas.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getSitioweb() {
        return sitioweb;
    }

    public void setSitioweb(String sitioweb) {
        this.sitioweb = sitioweb;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelf() {
        return telf;
    }

    public void setTelf(int telf) {
        this.telf = telf;
    }

    public int getFax() {
        return fax;
    }

    public void setFax(int fax) {
        this.fax = fax;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

       /*public static void main(String[] args) {
       // TODO code application logic here
       DEmpresa cli=new DEmpresa();
       
       //registrar una empresa
       cli.setNombre("Boliviana av");
       cli.setNit("4512362389");
       cli.setRazonsocial("SRL");
       cli.setSitioweb("googleww.com");
       cli.setDireccion("B la cuichilla #22");
       cli.setTelf(3527889);
       cli.setFax(3936897);
       cli.setUbicacion("4to anillo sn");
       cli.setSector("frial");
       cli.setTipo("privado");
       //System.out.println(cli.regEmpresa());
       
       //actualizar una empresa
       //cli.setId(8);
       //System.out.println(cli.updateEmpresa());
       
       //eliminar una empresa
       cli.setId(1);
           System.out.println(cli.delEmpresa());

       System.out.println(cli.listEmpresa());
       for (int i = 0; i < cli.listEmpresa().size(); i++) {
           System.out.println(cli.listEmpresa().get(i).getNombre());
       }
    
    }*/
}
