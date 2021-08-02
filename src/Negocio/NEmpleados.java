/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DEmpleados;
import java.util.ArrayList;
import libs.Fshared;

/**
 *
 * @author Hp
 */
public class NEmpleados {

    private DEmpleados empleados;
    private Fshared shared;

    public NEmpleados() {
        empleados = new DEmpleados();
    }

    public String listEmpleqados(String[] prt_parametros) {
        ArrayList<DEmpleados> l_empleados = new ArrayList<>();
        if (validarParaList(prt_parametros)) {
            l_empleados = empleados.listEmpleados();
            if (l_empleados.size() > 0) {
                String s_res = "<h2>Lista de empleados</h2>";
                s_res = s_res + "<table><tr>"
                        + "<td>Nombre</td>"
                        + "<td>Email</td>"
                        + "<td>CI</td>"
                        + "<td>Telefono</td>"
                        + "<td>Direccion</td>"
                        + "</tr>";
                for (DEmpleados i_empleados : l_empleados) {
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td>" + i_empleados.getNombre() + "</td>";
                    s_res = s_res + "<td>" + i_empleados.getEmail() + "</td>";
                    s_res = s_res + "<td>" + i_empleados.getCi() + "</td>";
                    s_res = s_res + "<td>" + i_empleados.getTelf() + "</td>";
                    s_res = s_res + "<td>" + i_empleados.getDireccion() + "</td>";
                    s_res = s_res + "</tr>";
                }
                s_res = s_res + "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");

    }
    
    public String regEmpleado(String[] parametros){
        empleados.setNombre(parametros[0]);
        empleados.setEmail(parametros[1]);
        empleados.setPassword(parametros[2]);
        empleados.setCi(Integer.parseInt(parametros[3]));
        empleados.setTelf(Integer.parseInt(parametros[4]));
        empleados.setDireccion(parametros[5]);
        empleados.setGenero(parametros[6]);
        empleados.setFechanacimiento(parametros[7]);
        empleados.setIdRol(Integer.parseInt(parametros[8]));
        
        boolean b_res = empleados.regEmpleado();
        if(b_res){
            return successMessage("Registro de empleado correcto");
        }
        return errorMessage("Registro sin exito");
    }
    
    public String editEmpleado(String[] parametros){
        empleados.setId(Integer.parseInt(parametros[0]));
        empleados.setNombre(parametros[1]);
        empleados.setEmail(parametros[2]);
        empleados.setCi(Integer.parseInt(parametros[3]));
        empleados.setTelf(Integer.parseInt(parametros[4]));
        empleados.setDireccion(parametros[5]);
        empleados.setGenero(parametros[6]);
        empleados.setFechanacimiento(parametros[7]);
        
        boolean b_res = empleados.updateEmpleado();
        if(b_res){
            return successMessage("Se modifico correctamente");
        }
        return errorMessage("No se pudo actualizar al empleado");
    }
    
    public String delEmpleado(String[] parametros){
        empleados.setId(Integer.parseInt(parametros[0]));
        
        boolean b_res = empleados.delEmpleado();
        if(b_res){
            return successMessage("Se elimino al empleado");
        }
        return errorMessage("No se pudo eliminar al empleado");
    }

    public boolean validarParaList(String[] prt_parametros) {
        if (prt_parametros.length != 1) {
            return false;
        }
        if (!esNumero(prt_parametros[0])) {
            return false;
        }
        return true;
    }

    public boolean esNumero(String prt_parametros) {
        try {
            Integer.parseInt(prt_parametros);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String errorMessage(String parametro) {
        return "<div><strong>ERROR</strong><p>" + parametro + "</p></div>";
    }

    public String successMessage(String parametro) {
        return "<div><strong>EXITO</strong><p>" + parametro + "</p></div>";
    }

}
