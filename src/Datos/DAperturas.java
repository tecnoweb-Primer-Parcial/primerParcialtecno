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
public class DAperturas {
    
    private final Conexion m_Conexion;
    private final Connection m_con;
    
    private int id;
    private String codigo;
    private String fechaIni;
    private String fechaFin;
    private String comentario;
    
    private int idCotizacion;
    
    private String codCotizacion;
    private String cliente;
    
     public DAperturas() {
        this.m_Conexion = Conexion.getInstancia();
        this.m_con = m_Conexion.getConexion();
    }

    public ArrayList<DAperturas> listAperturas() {
        Statement st;
        ArrayList<DAperturas> l_Apertura = new ArrayList<>();
        try {

            st = m_con.createStatement();
            String s_sql = "select aperturas.id,aperturas.codigo,aperturas.fechaIni,aperturas.fechaFin,aperturas.comentario,aperturas.idCotizacion, cotizaciones.cod,cotizaciones.cliente "
                    + " from aperturas,cotizaciones where aperturas.estado=true and aperturas.idCotizacion=cotizaciones.id;";
            ResultSet r_res = st.executeQuery(s_sql);

            while (r_res.next()) {
                DAperturas i_apertura = new DAperturas();
                i_apertura.setId(r_res.getInt(1));
                i_apertura.setCodigo(r_res.getString(2));
                i_apertura.setFechaIni(r_res.getString(3));
                i_apertura.setFechaFin(r_res.getString(4));
                i_apertura.setComentario(r_res.getString(5));
                i_apertura.setIdCotizacion(r_res.getInt(6));
                i_apertura.setCodCotizacion(r_res.getString(7));
                i_apertura.setCliente(r_res.getString(8));

                l_Apertura.add(i_apertura);
            }

            return l_Apertura;
        } catch (SQLException ex) {

            Logger.getLogger(DAperturas.class.getName()).log(Level.SEVERE, null, ex);
            return l_Apertura;
        }
    }

    protected boolean executeQuery(String query) {
        try {
            Statement conex = m_con.createStatement();
            ResultSet res = conex.executeQuery(query);
            while (res.next()) {               // Posicionar el cursor                
                String id = res.getString(1);       // Recuperar el valor de columna
                System.out.println("Valor de columna de identidad = " + id);
                if ("".equals(id)) {
                    return false;
                }
            }
            conex.close();
            res.close();
            return true;
        } catch (Exception e) {
            Logger.getLogger(DAperturas.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean regAperturas() {
        try {
            Statement st = m_con.createStatement();

            boolean rs = executeQuery("select id from cotizaciones where id=" + getIdCotizacion());

            if (rs) {
                String s_sql = "insert into aperturas(codigo,fechaIni,fechafin,comentario,estado,idcotizacion)\n"
                        + "values  ('" + this.getCodigo() + "','" + this.getFechaIni() + "','" + this.getFechaFin() 
                        + "','"+this.getComentario()+"',true,"+this.getIdCotizacion()+")";

                if (st.executeUpdate(s_sql) == 1) {
                    System.out.println("insertado exitoso");
                    return true;
                }
            }

            System.out.println("no se pudo insertar en el modelo");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DAperturas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean editAperturas() {
        Statement st;
        try {
            st = m_con.createStatement();
            boolean rs = executeQuery("select id from cotizaciones where id=" + getIdCotizacion());

            if (rs) {
                String s_sql = "UPDATE aperturas\n"
                        + "SET codigo='" + getCodigo()
                        + "', fechaini='" + getFechaIni()
                        + "', fechafin='" + getFechaFin()
                        + "', comentario='" + getComentario()
                        + "', idcotizacion=" + getIdCotizacion() + "\n"
                        + "WHERE id=" + getId();

                if (st.executeUpdate(s_sql) == 1) {
                    System.out.println("editado exitoso");
                    return true;
                }
            }

            System.out.println("no se pudo editar en el modelo");
            return false;
        } catch (SQLException ex) {

            Logger.getLogger(DAperturas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean delAperturas() {
        Statement st;

        try {
            st = m_con.createStatement();
            String s_sql = "UPDATE aperturas\n"
                    + "SET estado=false\n"
                    + "WHERE id=" + getId();

            if (st.executeUpdate(s_sql) == 1) {
                System.out.println("eliminado exitoso");
                return true;
            }

            System.out.println("no se pudo dar de baja a la promocion en el modelo");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DAperturas.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(int idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getCodCotizacion() {
        return codCotizacion;
    }

    public void setCodCotizacion(String codCotizacion) {
        this.codCotizacion = codCotizacion;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    /*
    public static void main(String[] args) {
        DAperturas cli = new DAperturas();

//        cli.setCodigo("SC");
//        cli.setFechaIni("2021-07-23");
//        cli.setFechaFin("2021-11-23");
//        cli.setComentario("Verificar");
//        cli.setIdCotizacion(7);
//        System.out.println(cli.regAperturas());

//        cli.setId(5);
//        cli.setCodigo("CD-2");
//        cli.setFechaIni("2021-08-23");
//        cli.setFechaFin("2021-11-23");
//        cli.setComentario("Rediseño");
//        cli.setIdCotizacion(8);
//        System.out.println(cli.editAperturas());

        cli.setId(6);
        System.out.println(cli.delAperturas());

        System.out.println(cli.listAperturas());
        for (int i = 0; i < cli.listAperturas().size(); i++) {
            System.out.println(cli.listAperturas().get(i).getId());
            System.out.println(cli.listAperturas().get(i).getCodigo());
        }

    }*/
      
}
