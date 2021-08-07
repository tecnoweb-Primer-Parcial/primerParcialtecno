/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.util.ArrayList;
import Datos.DReporte;

/**
 *
 * @author Hp
 */
public class NReporte {
    
    private DReporte reporte;

    public NReporte() {
        reporte = new DReporte();
    }
    
    public String generarReporte(String[] parametros){
        ArrayList<DReporte> l_reporte = new ArrayList<>();
        if(validarParaList(parametros)){
            l_reporte = reporte.listReportes();
            if(l_reporte.size()>0){
                String s_res = "<h2>Reporte de cotizaciones por empleado</h2>";
                s_res = s_res + "<table border=1><tr>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Nombre</th>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Telefono</th>"
                        + "<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Cotizaciones</th>"
                        + "</tr>";
                for (DReporte i_reporte : l_reporte) {
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td>" + i_reporte.getNombre() + "</td>";
                    s_res = s_res + "<td>" + i_reporte.getTelf()+ "</td>";
                    s_res = s_res + "<td>" + i_reporte.getVentas()+ "</td>";
                    s_res = s_res + "</tr>";
                }
                s_res = s_res + "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
    }
    
    public String generarReporteDate(String[] parametros){
        ArrayList<DReporte> l_reporte = new ArrayList<>();
        if(validarParaList(parametros)){
            l_reporte = reporte.listResportDate();
            if(l_reporte.size()>0){
                String s_res = "<h2>Reporte de cotizaciones por fechas</h2>";
                s_res = s_res + "<table border=1><tr>"
                        +"<td align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Codigo</td>"
                        +"<td align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Cliente</td>"
                        +"<td align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Ejecutivo</td>"
                        +"<td align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Tarifa</td>"
                        +"<td align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Servicio</td>"
                        +"<td align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Empresa</td>"
                        +"</tr>";
                for (DReporte i_reporte : l_reporte) {
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_reporte.getCodigo()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_reporte.getCliente()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_reporte.getEjecutivo()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_reporte.getTarifa()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_reporte.getNombreS()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_reporte.getNombreCom()+ "</td>";
                    s_res = s_res + "</tr>";
                }
                s_res = s_res + "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
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
