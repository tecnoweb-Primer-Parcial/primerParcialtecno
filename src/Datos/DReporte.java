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
public class DReporte {
    
    //variables para la conexion a la base de datos
    private Conexion m_Conexion;
    private Connection m_con;
    private Connection con;
    
    private int idRol;
    private String nombreRol;
    private String descripcion;
    
    private int idEmployed;
    private String nombre;
    private String emailEmployed;
    private String password;
    private int ci;
    private int telf;
    private String direccion;
    private String genero;
    private String fechaNac;
    private boolean estado;
    private int idrol;
    
    private int idContact;
    private String nombreContact;
    private String iniciales;
    private int cel;
    private String emailContact;
    private String emailConf;
    private String observaciones;
    private int idFEmploy;
    private int idFCom;
    
    private int idCompany;
    private String nombreCom;
    private String nit;
    private String razonSocial;
    private String sitioweb;
    private String dirCom;
    private int telfCom;
    private int faxCom;
    private String ubicacion;
    private String sector;
    private String tipo;
    private boolean estadoCom;
    
    private int idCot;
    private String codigo;
    private String cliente;
    private String fechaCot;
    private String ejecutivo;
    private int tarifa;
    private int idFService;
    private int idStatus;
    private int idFCompanyCot;
    private String fechaInit;
    private String fechaFin;
    private String nombreS;
    
    private int ventas;

    public DReporte() {
        this.m_Conexion = Conexion.getInstancia();
        this.m_con = m_Conexion.getConexion();
    }
    
    public ArrayList<DReporte> listReportes(){
        Statement st;
        ArrayList<DReporte> listReporte = new ArrayList<>();
        try {
            st = m_con.createStatement();
            String s_sql = "select employes.nombre,employes.telf,count(cotizaciones.id) \n"+
                    "from rols,employes,contactprincipal,company,cotizaciones \n"+
                    "where rols.id=employes.idrol and employes.id=contactprincipal.idemployed and company.id=contactprincipal.idcompany and company.id=cotizaciones.idcom and rols.nombre='empleado' \n"+
                    "group by employes.nombre,employes.telf";
            ResultSet r_res = st.executeQuery(s_sql);
            while(r_res.next()){
                DReporte i_reporte = new DReporte();
                i_reporte.setNombre(r_res.getString(1));
                i_reporte.setTelf(r_res.getInt(2));
                i_reporte.setVentas(r_res.getInt(3));
                listReporte.add(i_reporte);
            }
            return listReporte;
        } catch (SQLException e) {
            Logger.getLogger(DReporte.class.getName()).log(Level.SEVERE, null, e);
            return listReporte;
        }
    }
    
    public ArrayList<DReporte> listResportDate(){
        Statement st;
        ArrayList<DReporte> listReportDate = new ArrayList<>();
        try {
            st = m_con.createStatement();
            String s_sql = "select cotizaciones.cod,cotizaciones.cliente,cotizaciones.ejecutivo,cotizaciones.tarifa,services.nombre,company.nombre \n"+
                    "from cotizaciones, company, services \n"+
                    "where cotizaciones.idservice=services.id and cotizaciones.idcom=company.id and cotizaciones.fechacot between '"+this.getFechaInit()+"' and '"+this.getFechaFin()+"'";
            ResultSet r_res = st.executeQuery(s_sql);
            while(r_res.next()){
                DReporte i_reportDate = new DReporte();
                i_reportDate.setCodigo(r_res.getString(1));
                i_reportDate.setCliente(r_res.getString(2));
                i_reportDate.setEjecutivo(r_res.getString(3));
                i_reportDate.setTarifa(r_res.getInt(4));
                i_reportDate.setNombreS(r_res.getString(5));
                i_reportDate.setNombreCom(r_res.getString(6));
                listReportDate.add(i_reportDate);
            }
            return listReportDate;
        } catch (SQLException e) {
            Logger.getLogger(DReporte.class.getName()).log(Level.SEVERE, null, e);
            return listReportDate;
        }
    }
    
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdEmployed() {
        return idEmployed;
    }

    public void setIdEmployed(int idEmployed) {
        this.idEmployed = idEmployed;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmailEmployed() {
        return emailEmployed;
    }

    public void setEmailEmployed(String emailEmployed) {
        this.emailEmployed = emailEmployed;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public int getTelf() {
        return telf;
    }

    public void setTelf(int telf) {
        this.telf = telf;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
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

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
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

    public int getIdFEmploy() {
        return idFEmploy;
    }

    public void setIdFEmploy(int idFEmploy) {
        this.idFEmploy = idFEmploy;
    }

    public int getIdFCom() {
        return idFCom;
    }

    public void setIdFCom(int idFCom) {
        this.idFCom = idFCom;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public String getNombreCom() {
        return nombreCom;
    }

    public void setNombreCom(String nombreCom) {
        this.nombreCom = nombreCom;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getSitioweb() {
        return sitioweb;
    }

    public void setSitioweb(String sitioweb) {
        this.sitioweb = sitioweb;
    }

    public String getDirCom() {
        return dirCom;
    }

    public void setDirCom(String dirCom) {
        this.dirCom = dirCom;
    }

    public int getTelfCom() {
        return telfCom;
    }

    public void setTelfCom(int telfCom) {
        this.telfCom = telfCom;
    }

    public int getFaxCom() {
        return faxCom;
    }

    public void setFaxCom(int faxCom) {
        this.faxCom = faxCom;
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

    public boolean isEstadoCom() {
        return estadoCom;
    }

    public void setEstadoCom(boolean estadoCom) {
        this.estadoCom = estadoCom;
    }

    public int getIdCot() {
        return idCot;
    }

    public void setIdCot(int idCot) {
        this.idCot = idCot;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public int getIdFService() {
        return idFService;
    }

    public void setIdFService(int idFService) {
        this.idFService = idFService;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public int getIdFCompanyCot() {
        return idFCompanyCot;
    }

    public void setIdFCompanyCot(int idFCompanyCot) {
        this.idFCompanyCot = idFCompanyCot;
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

    public int getVentas() {
        return ventas;
    }

    public void setVentas(int ventas) {
        this.ventas = ventas;
    }

    public String getFechaInit() {
        return fechaInit;
    }

    public void setFechaInit(String fechaInit) {
        this.fechaInit = fechaInit;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getNombreS() {
        return nombreS;
    }

    public void setNombreS(String nombreS) {
        this.nombreS = nombreS;
    }

    
      /* public static void main(String[] args) {
       // TODO code application logic here
       DReporte cli=new DReporte();
       cli.setFechaInit("2021-07-23");
       cli.setFechaFin("2021-07-24");

       System.out.println(cli.listResportDate());
       for (int i = 0; i < cli.listResportDate().size(); i++) {
           System.out.println(cli.listResportDate().get(i).getCliente());
       }
    
    }*/
}
