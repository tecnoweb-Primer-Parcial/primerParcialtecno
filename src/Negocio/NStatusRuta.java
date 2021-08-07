/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;


import Datos.DStatus_ruta;
import java.util.ArrayList;

/**
 *
 * @author Oni
 */
public class NStatusRuta {
     private final DStatus_ruta d_status_ruta;

    public NStatusRuta() {
        this.d_status_ruta = new DStatus_ruta();
    }
    
    public String listStatusRuta(String[] parametros){
        ArrayList<DStatus_ruta> l_status_rutas = new ArrayList<>();
        if(validarParaList(parametros)){
            l_status_rutas = d_status_ruta.listStatusRuta();
            if(l_status_rutas.size() > 0){

                String s_res = "<h2>Lista del Estado de la Ruta</h2>";
                s_res += "<table border=1><tr>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">ID</th>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">ID Ruta</th>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Nombre Ruta</th>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">ID Cotizacion</th>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Codigo Cotizacion</th>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Cliente</th>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Estado Ruta</th>"
                        +"</tr>";

                for(DStatus_ruta i_ruta : l_status_rutas){
                    s_res += "<tr>";
                    s_res += "<th align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_ruta.getId() + "</th>";
                    s_res += "<th align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_ruta.getIdRutas()+ "</th>";
                    s_res += "<th align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_ruta.getNombreRuta() + "</th>";
                    s_res += "<th align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_ruta.getIdCotizacion() + "</th>";
                    s_res += "<th align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_ruta.getCod() + "</th>";
                    s_res += "<th align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_ruta.getCliente() + "</th>";
                    s_res += "<th align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_ruta.getEstadoRuta() + "</th>";
                    s_res += "</tr>";
                }
                s_res += "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
    }
    
    public String regStatusRuta(String[] parametros){
        d_status_ruta.setIdRutas(Integer.parseInt(parametros[0]));
        d_status_ruta.setIdCotizacion(Integer.parseInt(parametros[1]));
        d_status_ruta.setEstadoRuta(parametros[2]);
        boolean b_res = d_status_ruta.regStatusRuta();
        if(b_res){
            return successMessage("Estado de la Ruta registrada exitosamente");
        }
        return errorMessage("Error al insertar los estado de la ruta ");
    }
    
    public String editStatusRuta(String[] parametros){
        d_status_ruta.setId(Integer.parseInt(parametros[0]));
        d_status_ruta.setIdRutas(Integer.parseInt(parametros[1]));
        d_status_ruta.setIdCotizacion(Integer.parseInt(parametros[2]));
        d_status_ruta.setEstadoRuta(parametros[3]);
        boolean b_res = d_status_ruta.editStatusRuta();
        if(b_res){
            return successMessage("Estado de la Ruta modificada exitosamente");
        }
        return errorMessage("Error al editar el estado de la Ruta");
    }
    
    public String delStatusRuta(String[] parametros){
        d_status_ruta.setId(Integer.parseInt(parametros[0]));
        boolean b_res = d_status_ruta.delStatusRuta();
        if(b_res){
            return successMessage("Estado de la Ruta eliminada exitosamente");
        }
        return errorMessage("No se pudo eliminar el estado Ruta");
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