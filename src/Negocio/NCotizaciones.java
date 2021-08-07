/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;
  

    import Datos.DCotizaciones;
    import java.util.ArrayList;

/**
 *
 * @author Chon
 */
public class NCotizaciones {
     private DCotizaciones cotizaciones;

    public NCotizaciones() {
        cotizaciones = new DCotizaciones();
    }
    public String listcotizaciones(String[] prt_parametros){
        ArrayList<DCotizaciones> l_cotizaciones = new ArrayList<>();
        if(validarParaList(prt_parametros)){
            l_cotizaciones = cotizaciones.listCotizaciones();
            if(l_cotizaciones.size() > 0){
                String s_res = "<h2>Lista de cotizaciones</h2>";
                s_res = s_res + "<table border=1><tr>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Códido</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Cliente</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Fecha de Cotización</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Ejecutivo</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Tarifa</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Id de Servicio</th>"
                        +"</tr>";
                for (DCotizaciones i_cotizaciones : l_cotizaciones) {
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_cotizaciones.getCod()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_cotizaciones.getCliente()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_cotizaciones.getFechaCot()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_cotizaciones.getEjecutivo()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_cotizaciones.getTarifa()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_cotizaciones.getIdservice()+ "</td>";
                    s_res = s_res + "</tr>";
                }
                s_res = s_res + "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
    }
    
    public String regcotizaciones(String[] parametros){
        cotizaciones.setCod(parametros[0]);
        cotizaciones.setCliente(parametros[1]);
        cotizaciones.setFechaCot(parametros[2]);
        cotizaciones.setEjecutivo(parametros[3]);
        cotizaciones.setTarifa(Integer.parseInt(parametros[4]));
        cotizaciones.setIdservice(Integer.parseInt(parametros[5]));
        cotizaciones.setIdStatus(Integer.parseInt(parametros[6]));
        cotizaciones.setIdcom(Integer.parseInt(parametros[7]));
        boolean b_res = cotizaciones.regCotizaciones();
        if(b_res){
            return successMessage("Registro de cotizaciones correcto");
        }
        return errorMessage("Registro sin exito");
    }
    
    public String editcotizaciones(String[] parametros){
        cotizaciones.setId(Integer.parseInt(parametros[0]));
        cotizaciones.setCod(parametros[1]);
        cotizaciones.setCliente(parametros[2]);
        cotizaciones.setFechaCot(parametros[3]);
        cotizaciones.setEjecutivo(parametros[4]);
        cotizaciones.setTarifa(Integer.parseInt(parametros[5]));
        
        boolean b_res = cotizaciones.editCotizaciones();
        if(b_res){
            return successMessage("Se modifico correctamente");
        }
        return errorMessage("No se pudo modificar al cliente");
    }
    
    public String delcotizaciones(String[] parametros){
        cotizaciones.setId(Integer.parseInt(parametros[0]));
        boolean b_res = cotizaciones.delCotizaciones();
        if(b_res){
            return successMessage("Se elimino la cotizacion correctamente");
        }
        return errorMessage("No se pudo eliminar la cotizacion");
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
