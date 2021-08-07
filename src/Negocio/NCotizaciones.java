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
                s_res = s_res + "<table><tr>"
                        +"<td>Códido</td>"
                        +"<td>Cliente</td>"
                        +"<td>Fecha de Cotización</td>"
                        +"<td>Ejecutivo</td>"
                        +"<td>Tarifa</td>"
                        +"<td>Id de Servicio</td>"
                        +"</tr>";
                for (DCotizaciones i_cotizaciones : l_cotizaciones) {
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td>" + i_cotizaciones.getCod()+ "</td>";
                    s_res = s_res + "<td>" + i_cotizaciones.getCliente()+ "</td>";
                    s_res = s_res + "<td>" + i_cotizaciones.getFechaCot()+ "</td>";
                    s_res = s_res + "<td>" + i_cotizaciones.getEjecutivo()+ "</td>";
                    s_res = s_res + "<td>" + i_cotizaciones.getTarifa()+ "</td>";
                    s_res = s_res + "<td>" + i_cotizaciones.getIdservice()+ "</td>";
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
        boolean b_res = cotizaciones.regCotizaciones();
        if(b_res){
            return successMessage("Registro de cotizaciones correcto");
        }
        return errorMessage("Registro sin exito");
    }
    
    public String editcotizaciones(String[] parametros){
        cotizaciones.setId(Integer.parseInt(parametros[0]));
        cotizaciones.setCod(parametros[1]);
        cotizaciones.setFechaCot(parametros[2]);
        cotizaciones.setEjecutivo(parametros[3]);
        cotizaciones.setTarifa(Integer.parseInt(parametros[4]));
        
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
