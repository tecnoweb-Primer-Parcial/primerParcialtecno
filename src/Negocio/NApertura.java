/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import Datos.DAperturas;
import Datos.DStatus_ruta;
import java.util.ArrayList;

/**
 *
 * @author Oni
 */
public class NApertura {
    private final DAperturas d_apertura;

    public NApertura() {
        this.d_apertura = new DAperturas();
    }
    
    public String listApertura(String[] parametros){
        ArrayList<DAperturas> l_aperturas = new ArrayList<>();
        if(validarParaList(parametros)){
            l_aperturas = d_apertura.listAperturas();
            if(l_aperturas.size() > 0){

                String s_res = "<h2>Lista de Apertura Contable</h2>";
                s_res += "<table><tr>"
                        + "<th>ID</th>"
                        + "<th>Codigo</th>"
                        + "<th>Fecha Inicio</th>"
                        + "<th>Fecha Fin</th>"
                        + "<th>Comentario</th>"
                        + "<th>ID Cotizacion</th>"
                        + "<th>Codigo</th>"
                        + "<th>Cliente</th>"
                        +"</tr>";

                for(DAperturas i_apertura : l_aperturas){
                    s_res += "<tr>";
                    s_res += "<th>" + i_apertura.getId() + "</th>";
                    s_res += "<th>" + i_apertura.getCodigo()+ "</th>";
                    s_res += "<th>" + i_apertura.getFechaIni()+ "</th>";
                    s_res += "<th>" + i_apertura.getFechaFin()+ "</th>";
                    s_res += "<th>" + i_apertura.getComentario()+ "</th>";
                    s_res += "<th>" + i_apertura.getIdCotizacion()+ "</th>";
                    s_res += "<th>" + i_apertura.getCodCotizacion()+ "</th>";
                    s_res += "<th>" + i_apertura.getCliente()+ "</th>";
                    s_res += "</tr>";
                }
                s_res += "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
    }
    
    public String regApertura(String[] parametros){
        d_apertura.setCodigo(parametros[0]);
        d_apertura.setFechaIni(parametros[1]);
        d_apertura.setFechaFin(parametros[2]);
        d_apertura.setComentario(parametros[3]);
        d_apertura.setIdCotizacion(Integer.parseInt(parametros[4]));
        boolean b_res = d_apertura.regAperturas();
        if(b_res){
            return successMessage("Apertura contable registrada exitosamente");
        }
        return errorMessage("Error al insertar las aperturas contable");
    }
    
    public String editApertura(String[] parametros){
        d_apertura.setId(Integer.parseInt(parametros[0]));
        d_apertura.setCodigo(parametros[1]);
        d_apertura.setFechaIni(parametros[2]);
        d_apertura.setFechaFin(parametros[3]);
        d_apertura.setComentario(parametros[4]);
        d_apertura.setIdCotizacion(Integer.parseInt(parametros[5]));
        boolean b_res = d_apertura.editAperturas();
        if(b_res){
            return successMessage("Apertura contable modificada exitosamente");
        }
        return errorMessage("Error al editar el estado de la APertura Contable");
    }
    
    public String delApertura(String[] parametros){
        d_apertura.setId(Integer.parseInt(parametros[0]));
        boolean b_res = d_apertura.delAperturas();
        if(b_res){
            return successMessage("Apertura contable eliminada exitosamente");
        }
        return errorMessage("No se pudo eliminar la apertura contable");
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
