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
public class DStatus_ruta {

    private final Conexion m_Conexion;
    private final Connection m_con;

    // Atributos
    private int id;
    private String estadoRuta;

    private int idRutas;
    private int idCotizacion;

    private String nombreRuta;
    private String cod;
    private String cliente;

    public DStatus_ruta() {
        this.m_Conexion = Conexion.getInstancia();
        this.m_con = m_Conexion.getConexion();
    }

    public ArrayList<DStatus_ruta> listStatusRuta() {
        Statement st;
        ArrayList<DStatus_ruta> l_Status_Ruta = new ArrayList<>();
        try {

            st = m_con.createStatement();
            String s_sql = "select status_ruta.id,status_ruta.idRutas,rutas.nombreRuta,status_ruta.idCotizacion,cotizaciones.cod,cotizaciones.cliente,status_ruta.estadoRuta"
                    + " from status_ruta,rutas,cotizaciones where status_ruta.estado=true and status_ruta.idRutas=rutas.id and status_ruta.idCotizacion=cotizaciones.id;";
            ResultSet r_res = st.executeQuery(s_sql);

            while (r_res.next()) {
                DStatus_ruta i_status_ruta = new DStatus_ruta();
                i_status_ruta.setId(r_res.getInt(1));
                i_status_ruta.setIdRutas(r_res.getInt(2));
                i_status_ruta.setNombreRuta(r_res.getString(3));
                i_status_ruta.setIdCotizacion(r_res.getInt(4));
                i_status_ruta.setCod(r_res.getString(5));
                i_status_ruta.setCliente(r_res.getString(6));
                i_status_ruta.setEstadoRuta(r_res.getString(7));

                l_Status_Ruta.add(i_status_ruta);
            }

            return l_Status_Ruta;
        } catch (SQLException ex) {

            Logger.getLogger(DStatus_ruta.class.getName()).log(Level.SEVERE, null, ex);
            return l_Status_Ruta;
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
            Logger.getLogger(DStatus_ruta.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean regStatusRuta() {
        try {
            Statement st = m_con.createStatement();

            boolean rr = executeQuery("select id from rutas where id=" + getIdRutas());

            boolean rs = executeQuery("select id from cotizaciones where id=" + getIdCotizacion());

            if (rr && rs) {
                String s_sql = "insert into status_ruta(idrutas,idcotizacion,estadoruta,estado)\n"
                        + "values  (" + this.getIdRutas() + "," + this.getIdCotizacion() + ",'" + this.getEstadoRuta() + "',true)";

                if (st.executeUpdate(s_sql) == 1) {
                    System.out.println("insertado exitoso");
                    return true;
                }
            }

            System.out.println("no se pudo insertar en el modelo");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DStatus_ruta.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean editStatusRuta() {
        Statement st;
        try {
            st = m_con.createStatement();

            boolean rr = executeQuery("select id from rutas where id=" + getIdRutas());

            boolean rs = executeQuery("select id from cotizaciones where id=" + getIdCotizacion());

            if (rr && rs) {
                String s_sql = "UPDATE status_ruta\n"
                        + "SET idRutas=" + getIdRutas()
                        + ", idCotizacion=" + getIdCotizacion()
                        + ", estadoRuta='" + getEstadoRuta() + "'\n"
                        + "WHERE id=" + getId();

                if (st.executeUpdate(s_sql) == 1) {
                    System.out.println("editado exitoso");
                    return true;
                }
            }

            System.out.println("no se pudo editar en el modelo");
            return false;
        } catch (SQLException ex) {

            Logger.getLogger(DStatus_ruta.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean delStatusRuta() {
        Statement st;

        try {
            st = m_con.createStatement();
            String s_sql = "UPDATE status_ruta\n"
                    + "SET estado=false\n"
                    + "WHERE id=" + getId();

            if (st.executeUpdate(s_sql) == 1) {
                System.out.println("eliminado exitoso");
                return true;
            }

            System.out.println("no se pudo dar de baja a la promocion en el modelo");
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(DStatus_ruta.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public String getEstadoRuta() {
        return estadoRuta;
    }

    public void setEstadoRuta(String estadoRuta) {
        this.estadoRuta = estadoRuta;
    }

    public int getIdRutas() {
        return idRutas;
    }

    public void setIdRutas(int idRutas) {
        this.idRutas = idRutas;
    }

    public int getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(int idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getNombreRuta() {
        return nombreRuta;
    }

    public void setNombreRuta(String nombreRuta) {
        this.nombreRuta = nombreRuta;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
/*
    public static void main(String[] args) {
        DStatus_ruta cli = new DStatus_ruta();

//        cli.setEstadoRuta("Camino");
//        cli.setIdRutas(2);
//        cli.setIdCotizacion(7);
//        System.out.println(cli.regStatusRuta());

//        cli.setId(7);
//        cli.setIdRutas(3);
//        cli.setIdCotizacion(6);
//        cli.setEstadoRuta("transito");
//        System.out.println(cli.editStatusRuta());

        cli.setId(7);
        System.out.println(cli.delStatusRuta());

        System.out.println(cli.listStatusRuta());
        for (int i = 0; i < cli.listStatusRuta().size(); i++) {
            System.out.println(cli.listStatusRuta().get(i).getId());
            System.out.println(cli.listStatusRuta().get(i).getNombreRuta());
        }

    }*/

}
