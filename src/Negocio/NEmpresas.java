/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DEmpresa;
import java.util.ArrayList;

/**
 *
 * @author Hp
 */
public class NEmpresas {

    private DEmpresa empresa;

    public NEmpresas() {
        empresa = new DEmpresa();
    }

    public String listEmpresas(String[] parametros){
        ArrayList<DEmpresa> l_empresas = new ArrayList<>();
        if(validarParaList(parametros)){
            l_empresas = empresa.listEmpresa();
            if(l_empresas.size() > 0){
                String s_res = "<h2>Lista de empresas</h2>";
                s_res = s_res + "<table><tr>"
                        + "<td>Nombre</td>"
                        + "<td>NIT</td>"
                        + "<td>Razon social</td>"
                        + "<td>Direccion</td>"
                        + "<td>Telefono</td>"
                        + "<td>Ubicacion</td>"
                        + "<td>Sector</td>"
                        + "<td>Tipo de empresa</td>"
                        +"</tr>";
                for(DEmpresa i_empresas : l_empresas){
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td>" + i_empresas.getNombre() + "</td>";
                    s_res = s_res + "<td>" + i_empresas.getNit() + "</td>";
                    s_res = s_res + "<td>" + i_empresas.getRazonsocial() + "</td>";
                    s_res = s_res + "<td>" + i_empresas.getDireccion() + "</td>";
                    s_res = s_res + "<td>" + i_empresas.getTelf() + "</td>";
                    s_res = s_res + "<td>" + i_empresas.getUbicacion() + "</td>";
                    s_res = s_res + "<td>" + i_empresas.getSector() + "</td>";
                    s_res = s_res + "<td>" + i_empresas.getTipo() + "</td>";
                    s_res = s_res + "</tr>";
                }
                s_res = s_res + "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
    }
    
    public String regEmpresa(String[] parametros){
        empresa.setNombre(parametros[0]);
        empresa.setNit(parametros[1]);
        empresa.setRazonsocial(parametros[2]);
        empresa.setSitioweb(parametros[3]);
        empresa.setDireccion(parametros[4]);
        empresa.setTelf(Integer.parseInt(parametros[5]));
        empresa.setFax(Integer.parseInt(parametros[6]));
        empresa.setUbicacion(parametros[7]);
        empresa.setSector(parametros[8]);
        empresa.setTipo(parametros[9]);
        boolean b_res = empresa.regEmpresa();
        if(b_res){
            return successMessage("Empresa registrada exitosamente");
        }
        return errorMessage("Error al insertar la empresa");
    }
    
    public String editEmpresa(String[] parametros){
        empresa.setNombre(parametros[0]);
        empresa.setNit(parametros[1]);
        empresa.setRazonsocial(parametros[2]);
        empresa.setSitioweb(parametros[3]);
        empresa.setDireccion(parametros[4]);
        empresa.setTelf(Integer.parseInt(parametros[5]));
        empresa.setFax(Integer.parseInt(parametros[6]));
        empresa.setUbicacion(parametros[7]);
        empresa.setSector(parametros[8]);
        empresa.setTipo(parametros[9]);
        empresa.setId(Integer.parseInt(parametros[10]));
        boolean b_res = empresa.updateEmpresa();
        if(b_res){
            return successMessage("Empresa modificada exitosamente");
        }
        return errorMessage("Error al editar la empresa");
    }
    
    public String delEmpresa(String[] parametros){
        empresa.setId(Integer.parseInt(parametros[0]));
        boolean b_res = empresa.delEmpresa();
        if(b_res){
            return successMessage("Empresa eliminada exitosamente");
        }
        return errorMessage("No se pudo eliminar la empresa");
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
