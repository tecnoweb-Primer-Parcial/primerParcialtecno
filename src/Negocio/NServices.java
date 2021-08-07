/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

    import Datos.DServices;
    import java.util.ArrayList;
/**import
 *
 * @author Chon
 */
public class NServices {
    private DServices services;

    public NServices() {
        services = new DServices();
    }
    public String listservices(String[] prt_parametros){
        ArrayList<DServices> l_services = new ArrayList<>();
        if(validarParaList(prt_parametros)){
            l_services = services.listServices();
            if(l_services.size() > 0){
                String s_res = "<h2>Lista de servicios</h2>";
                s_res = s_res + "<table border=1><tr>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Id</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Nombre</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Código</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Descripción</th>"
                        +"<th align=\"center\"valign=\"top\"  bgcolor=\"#7FFFD4\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">Estado</th>"
                        +"</tr>";
                for (DServices i_services : l_services) {
                    s_res = s_res + "<tr>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_services.getId()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_services.getName()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_services.getCodigo()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_services.getDescription()+ "</td>";
                    s_res = s_res + "<td align=\"center\" bgcolor=\"#FFF8DC\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">" + i_services.isStatus()+ "</td>";
                    s_res = s_res + "</tr>";
                }
                s_res = s_res + "</table>";
                return s_res;
            }
            return successMessage("Lista vacia");
        }
        return errorMessage("Parametros incorrectos");
    }
    
    public String regservices(String[] parametros){
        services.setName(parametros[0]);
        services.setCodigo(parametros[1]);
        services.setDescription(parametros[2]);
        boolean b_res = services.regServices();
        if(b_res){
            return successMessage("Registro de servicio correcto");
        }
        return errorMessage("Registro sin exito");
    }
    
    public String editservices(String[] parametros){
        services.setId(Integer.parseInt(parametros[0]));
        services.setName(parametros[1]);
        services.setCodigo(parametros[2]);
        services.setDescription(parametros[3]);
        
        boolean b_res = services.editServices();
        if(b_res){
            return successMessage("Se modifico correctamente");
        }
        return errorMessage("No se pudo modificar el Servicio");
    }
    
    public String delservices(String[] parametros){
        services.setId(Integer.parseInt(parametros[0]));
        boolean b_res = services.delServices();
        if(b_res){
            return successMessage("Se elimino el servicio correctamente");
        }
        return errorMessage("No se pudo eliminar el servicio");
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
