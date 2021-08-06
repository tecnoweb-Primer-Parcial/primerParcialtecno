/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DEmpresa;
import Datos.DRutas;
import java.util.ArrayList;

/**
 *
 * @author Oni
 */
public class NRutas {
    private final DRutas d_rutas;

    public NRutas() {
        this.d_rutas = new DRutas();
    }
    
    public String listRutas(String[] parametros){
        ArrayList<DRutas> l_rutas = new ArrayList<>();
        if(validarParaList(parametros)){
            l_rutas = d_rutas.listRutas();
            if(l_rutas.size() > 0){
                String s_res = "<h2>Lista de Rutas</h2>";
                s_res += "<table><tr>"
                        + "<td>ID</td>"
                        + "<td>Nombre Ruta</td>"
                        +"</tr>";
                for(DRutas i_ruta : l_rutas){
                    s_res += "<tr>";
                    s_res += "<td>" + i_ruta.getId() + "</td>";
                    s_res += "<td>" + i_ruta.getNombreRuta() + "</td>";
                    s_res += "</tr>";
                }
                s_res += "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
    }
    
    public String regRutas(String[] parametros){
        d_rutas.setNombreRuta(parametros[1]);
        boolean b_res = d_rutas.regRuta();
        if(b_res){
            return successMessage("Ruta registrada exitosamente");
        }
        return errorMessage("Error al insertar las Rutas");
    }
    
    public String editRutas(String[] parametros){
        d_rutas.setId(Integer.parseInt(parametros[0]));
        d_rutas.setNombreRuta(parametros[1]);
        boolean b_res = d_rutas.editRutas();
        if(b_res){
            return successMessage("Ruta modificada exitosamente");
        }
        return errorMessage("Error al editar la Ruta");
    }
    
    public String delEmpresa(String[] parametros){
        d_rutas.setId(Integer.parseInt(parametros[0]));
        boolean b_res = d_rutas.delRutas();
        if(b_res){
            return successMessage("Ruta eliminada exitosamente");
        }
        return errorMessage("No se pudo eliminar la Ruta");
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