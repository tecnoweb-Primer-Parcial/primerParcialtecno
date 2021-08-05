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
public class DEmpleados {

    //variables para la conexion a la base de datos
    private Conexion m_Conexion;
    private Connection m_con;
    private Connection con;

    //atributos de la tabla usuario
    private int id;
    private String nombre;
    private String email;
    private String password;
    private int ci;
    private int telf;
    private String direccion;
    private String genero;
    private String fechanacimiento;
    private boolean estado;
    private int idRol;
    private int cotizaciones;

    public DEmpleados() {
        this.m_Conexion = Conexion.getInstancia();
        this.m_con = m_Conexion.getConexion();
    }

    public ArrayList<DEmpleados> listEmpleados() {
        Statement st;
        ArrayList<DEmpleados> l_empleados = new ArrayList<>();
        try {
            st = m_con.createStatement();
            String s_sql = "";
            s_sql = "select employes.nombre,employes.email,employes.ci,employes.telf,employes.direccion \n"
                    + "from employes \n"
                    + "where employes.estado=true";
            ResultSet r_res = st.executeQuery(s_sql);

            while (r_res.next()) {
                DEmpleados i_empleado = new DEmpleados();
                i_empleado.setNombre(r_res.getString(1));
                i_empleado.setEmail(r_res.getString(2));
                i_empleado.setCi(r_res.getInt(3));
                i_empleado.setTelf(r_res.getInt(4));
                i_empleado.setDireccion(r_res.getString(5));
                l_empleados.add(i_empleado);
            }
            return l_empleados;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE, null, e);
            return l_empleados;
        }

    }

    public boolean regEmpleado() {
        try {
            Statement st = m_con.createStatement();
            String s_sql = "insert into employes(nombre,email,password,ci,telf,direccion,genero,fechaNacimiento,estado,created_at,updated_at,idrol) \n"
                    + "values ('" + this.getNombre() + "','" + this.getEmail() + "','" + this.getPassword() + "'," + this.getCi() + "," + this.getTelf() + ",'" + this.getDireccion() + "','" + this.getGenero() + "','" + this.getFechanacimiento() + "',true,now(),now()," + this.getIdRol() + ")";
            if (st.executeUpdate(s_sql) == 1) {
                System.out.println("empleado insertado correctamente");
                return true;
            }
            System.out.println("No se pudo insertar al empleado");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean updateEmpleado() {
        Statement st;
        Statement stmt;
        ResultSet rs;
        try {
            st = m_con.createStatement();
            stmt = m_con.createStatement();
            String id = "";
            rs = stmt.executeQuery("select id from employes where id=" + getId());
            while (rs.next()) {
                id = rs.getString(1); //recuperar el valor de la columna
                System.out.println("valor de la columna de identidad " + id);
            }
            rs.close();
            stmt.close();
            String s_sql = "update employes \n"
                    + "set nombre='" + getNombre() + "',email='" + getEmail() + "',ci=" + getCi() + ",telf='" + getTelf() + "',direccion='" + getDireccion() + "',genero='" + getGenero() + "',fechanacimiento='" + getFechanacimiento() + "' \n"
                    + "where employes.id=" + getId();
            if (st.executeUpdate(s_sql) == 1) {
                System.out.println("Se edito el empleado con exito");
                return true;
            }
            System.out.println("No se pudo actualizar");
            return false;
        } catch (SQLException e) {
            Logger.getLogger(DEmpleados.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean delEmpleado() {
        Statement st;
        try {
            st = m_con.createStatement();
            String s_sql = "update employes \n"
                    + "set estado=false \n"
                    + "where id=" + getId();
            if (st.executeUpdate(s_sql) == 1) {
                System.out.println("Empleado eliminado");
                return true;
            }
            System.out.println("No se pudo eliminar al empleado");
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(int cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        // TODO code application logic here
        DEmpleados cli = new DEmpleados();

        /*Registrar empleado
       cli.setNombre("Laura Vargas");
       cli.setEmail("laura@gmail.com");
       cli.setPassword("cristhian123");
       cli.setCi(7896589);
       cli.setTelf(78588196);
       cli.setDireccion("barrio la cuchilla");
       cli.setGenero("M");
       cli.setFechanacimiento("1995-02-21");
       cli.setEstado(true);
       cli.setIdRol(2);
       System.out.println(cli.regEmpleado());*/
        //Actualizar empleado
        /*cli.setNombre("Criss Vargas");
       cli.setEmail("crist@gmail.com");
       cli.setCi(7896589);
       cli.setTelf(78588196);
       cli.setDireccion("barrio la cuchilla");
       cli.setGenero("M");
       cli.setFechanacimiento("1995-02-21");*/
        //cli.setId(8);
        //System.out.println(cli.delEmpleado());
        //System.out.println(cli.updateEmpleado());
        System.out.println(cli.listEmpleados());
        for (int i = 0; i < cli.listEmpleados().size(); i++) {
            System.out.println(cli.listEmpleados().get(i).getNombre());
        }

    }

}
