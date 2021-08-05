/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DClientes;
import java.util.ArrayList;

/**
 *
 * @author Hp
 */
public class NClientes {
    
    private DClientes clientes;

    public NClientes() {
        clientes = new DClientes();
    }
    
    public String listClientes(String[] prt_parametros){
        ArrayList<DClientes> l_clientes = new ArrayList<>();
        if(validarParaList(prt_parametros)){
            l_clientes = clientes.listClients();
            if(l_clientes.size() > 0){
                String s_res = "<h2>Lista de empleados</h2>";
                s_res = s_res + "<table><tr>"
                        +"<td>Nombre</td>"
                        +"<td>Nit</td>"
                        +"<td>Razon social</td>"
                        +"<td>Direccion</td>"
                        +"<td>Telefono</td>"
                        +"<td>Ubicacion</td>"
                        +"<td>Contacto</td>"
                        +"<td>Celular</td>"
                        +"<td>Email</td>"
                        +"</tr>";
                for (DClientes i_clientes : l_clientes) {
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td>" + i_clientes.getNombreC() + "</td>";
                    s_res = s_res + "<td>" + i_clientes.getNitC() + "</td>";
                    s_res = s_res + "<td>" + i_clientes.getRazonsocial() + "</td>";
                    s_res = s_res + "<td>" + i_clientes.getDireccion() + "</td>";
                    s_res = s_res + "<td>" + i_clientes.getTelf() + "</td>";
                    s_res = s_res + "<td>" + i_clientes.getUbicacion() + "</td>";
                    s_res = s_res + "<td>" + i_clientes.getNombreContact() + "</td>";
                    s_res = s_res + "<td>" + i_clientes.getCel() + "</td>";
                    s_res = s_res + "<td>" + i_clientes.getEmail() + "</td>";
                    s_res = s_res + "</tr>";
                }
                s_res = s_res + "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
    }
    
    public String regClientes(String[] parametros){
        clientes.setNombreContact(parametros[0]);
        clientes.setIniciales(parametros[1]);
        clientes.setCel(Integer.parseInt(parametros[2]));
        clientes.setEmail(parametros[3]);
        clientes.setEmailConf(parametros[4]);
        clientes.setObservaciones(parametros[5]);
        clientes.setIdEmployed(Integer.parseInt(parametros[6]));
        clientes.setIdCompany(Integer.parseInt(parametros[7]));
        boolean b_res = clientes.regClient();
        if(b_res){
            return successMessage("Registro de clientes correcto");
        }
        return errorMessage("Registro sin exito");
    }
    
    public String editCliente(String[] parametros){
        clientes.setNombreContact(parametros[0]);
        clientes.setIniciales(parametros[1]);
        clientes.setCel(Integer.parseInt(parametros[2]));
        clientes.setEmail(parametros[3]);
        clientes.setEmailConf(parametros[4]);
        clientes.setObservaciones(parametros[5]);
        clientes.setIdContact(Integer.parseInt(parametros[6]));
        boolean b_res = clientes.updateClient();
        if(b_res){
            return successMessage("Se modifico correctamente");
        }
        return errorMessage("No se pudo modificar al cliente");
    }
    
    public String delCliente(String[] parametros){
        clientes.setIdContact(Integer.parseInt(parametros[0]));
        boolean b_res = clientes.delClient();
        if(b_res){
            return successMessage("Se elimino al cliente correctamente");
        }
        return errorMessage("No se pudo eliminar al cliente");
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
