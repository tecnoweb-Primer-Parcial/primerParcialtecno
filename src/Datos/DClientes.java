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
public class DClientes {
    //variables para la conexion a la base de datos
    private Conexion m_Conexion;
    private Connection m_con;
    private Connection con;
    
    //atributos de la tabla compania
    private int idComp;
    private String nombreC;
    private String nitC;
    private String razonsocial;
    private String sitioweb;
    private String direccion;
    private int telf;
    private int fax;
    private String ubicacion;
    private String sector;
    private String tipo;
    private boolean estado;
    
    //atributos de la tabla contacto principal
    private int idContact;
    private String nombreContact;
    private String iniciales;
    private int cel;
    private String email;
    private String emailConf;
    private String observaciones;
    private int idEmployed;
    private int idCompany;
    
    public DClientes(){
        this.m_Conexion = Conexion.getInstancia();
        this.m_con = m_Conexion.getConexion();
    }
    
    public ArrayList<DClientes> listClients(){
        Statement st;
        ArrayList<DClientes> listClient = new ArrayList<>();
        try {
            st = m_con.createStatement();
            String s_sql = "select company.nombre,company.nit,company.razonsocial,company.direccion,company.telf,company.ubicacion,contactprincipal.nombre,contactprincipal.cel,contactprincipal.email \n"+
                    "from company,contactprincipal \n"+
                    "where company.id=contactprincipal.idcompany";
            ResultSet r_res = st.executeQuery(s_sql);
            while(r_res.next()){
                DClientes i_clientes = new DClientes();
                i_clientes.setNombreC(r_res.getString(1));
                i_clientes.setNitC(r_res.getString(2));
                i_clientes.setRazonsocial(r_res.getString(3));
                i_clientes.setDireccion(r_res.getString(4));
                i_clientes.setTelf(r_res.getInt(5));
                i_clientes.setUbicacion(r_res.getString(6));
                i_clientes.setNombreContact(r_res.getString(7));
                i_clientes.setCel(r_res.getInt(8));
                i_clientes.setEmail(r_res.getString(9));
                listClient.add(i_clientes);
            }
            return listClient;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE, null, e);
            return listClient;
        }
    }
    
    public boolean regClient(){
        try {
            Statement st1 = m_con.createStatement();
            String s_sql1 = "insert into contactPrincipal(nombre,iniciales,cel,email,emailConf,observaciones,idemployed,idCompany,estado) \n"+
                    "values ('"+this.getNombreContact()+"','"+this.getIniciales()+"',"+this.getCel()+",'"+this.getEmail()+"','"+this.getEmailConf()+"','"+this.getObservaciones()+"',"+this.getIdEmployed()+","+this.getIdCompany()+",true)";
            if(st1.executeUpdate(s_sql1)==1){
                System.out.println("Cliente registrado exitosamente!");
                return true;
            }
            System.out.println("No se pudo registrar al cliente!");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE,null,e);
            return false;
        }
    }
    
    public boolean updateClient(){
        Statement st1;
        Statement stmt1;
        ResultSet rs1;
        try {
            st1 = m_con.createStatement();
            stmt1 = m_con.createStatement();
            String id1 = "";
            rs1 = stmt1.executeQuery("select id from contactprincipal where contactprincipal.id="+getIdContact());
            while(rs1.next()){
                id1 = rs1.getString(1);
                System.out.println("valor de la columan contacto principal "+id1);
            }
            rs1.close();
            stmt1.close();
            String s_sql1 = "update contactprincipal \n"+
                    "set nombre='"+getNombreContact()+"',iniciales='"+getIniciales()+"',cel="+getCel()+",email='"+getEmail()+"',emailconf='"+getEmailConf()+"',observaciones='"+getObservaciones()+"' where contactprincipal.id="+getIdContact();
            if(st1.executeUpdate(s_sql1)==1){
                System.out.println("Se edito con exito");
                return true;
            }
            System.out.println("No se pudo actualizar");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE,null,e);
            return false;
        }
    }
    
    public boolean delClient(){
        Statement st;
        try {
            st = m_con.createStatement();
            String s_sql = "update contactprincipal \n"+
                    "set estado=false \n"+
                    "where id="+getIdContact();
            if(st.executeUpdate(s_sql)==1){
                System.out.println("Cliente eliminado");
                return true;
            }
            System.out.println("No se pudo eliminar el cliente");
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

    public int getIdComp() {
        return idComp;
    }

    public void setIdComp(int idComp) {
        this.idComp = idComp;
    }

    public String getNombreC() {
        return nombreC;
    }

    public void setNombreC(String nombreC) {
        this.nombreC = nombreC;
    }

    public String getNitC() {
        return nitC;
    }

    public void setNitC(String nitC) {
        this.nitC = nitC;
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

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public String getNombreContact() {
        return nombreContact;
    }

    public void setNombreContact(String nombreContact) {
        this.nombreContact = nombreContact;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public int getCel() {
        return cel;
    }

    public void setCel(int cel) {
        this.cel = cel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailConf() {
        return emailConf;
    }

    public void setEmailConf(String emailConf) {
        this.emailConf = emailConf;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdEmployed() {
        return idEmployed;
    }

    public void setIdEmployed(int idEmployed) {
        this.idEmployed = idEmployed;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }
    
    public static void main(String[] args) {
       // TODO code application logic here
       DClientes cli=new DClientes();
    
       //Registrar empleado
       //datos contacto principal
       /*cli.setNombreContact("cristhian vargas");
       cli.setIniciales("cvq");
       cli.setCel(78588196);
       cli.setEmail("dcristhian@gmail.com");
       cli.setEmailConf("cristian@gmail.com");
       cli.setObservaciones("ninguna");
       cli.setIdEmployed(2);
       cli.setIdCompany(4);
       
        System.out.println(cli.regClient());*/
       
       //editar cliente
       /*cli.setNombreContact("maria juana ortiz");
       cli.setIniciales("MJO");
       cli.setCel(65328947);
       cli.setEmail("buenoi@gmail.com");
       cli.setEmailConf("buenito@gmail.com");
       cli.setObservaciones("sin novedades");
       cli.setIdComp(6);
       cli.setIdContact(4);
       System.out.println(cli.updateClient());*/
       
       //eliminar un cliente
       //cli.setIdContact(3);
       //System.out.println(cli.delClient());

       System.out.println(cli.listClients());
       for (int i = 0; i < cli.listClients().size(); i++) {
           System.out.println(cli.listClients().get(i).getNombreC());
       }
    
    }
}
