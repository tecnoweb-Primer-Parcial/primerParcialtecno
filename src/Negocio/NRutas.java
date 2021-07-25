/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

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
    
    public String listIngreso(String[] prt_parametros) {
        ArrayList<DRutas> l_rutas=new ArrayList<>();
        if (validarParaListRutas(prt_parametros)) {
            d_rutas.setId(Integer.parseInt(prt_parametros[0]));
            l_rutas=d_rutas.listRutas();
        if (l_rutas.size()>0)
        {
            
               String s_res="<thead><tr>"
                                    + "<th align=\"center\">ID</th>"
                                    + "<th align=\"center\">NOMBRE</th>"
                                                                       
                              + "</tr></thead>";
            for (DRutas i_Rutas : l_rutas) {
                s_res=s_res+"<tbody><tr>";
                s_res=s_res+"<td align=\"center\">"+i_Rutas.getId()+"</td>";
                s_res=s_res+"<td align=\"center\">"+i_Rutas.getNombreRuta()+"</td>";
                s_res=s_res+"</tr></tbody>";
            
                 
            } 
            String cad = "";
        cad += "<body style=\"margin: 0 !important; padding: 0 !important; background-color: #eeeeee;\" bgcolor=\"#eeeeee\">\n"
                + "\n"
                + "<!-- HIDDEN PREHEADER TEXT -->\n"
                + "<div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: Open Sans, Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\">\n"
                + "COMANDOS PARA EL SISTEMA DE GESTION \n"
                + "</div>\n"
                + "\n"
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "    <tr>\n"
                + "        <td align=\"center\" style=\"background-color: #eeeeee;\" bgcolor=\"#eeeeee\">\n"
                + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "            <tr>\n"
                + "                <td align=\"center\" valign=\"top\" style=\"font-size:0; padding: 35px;\" bgcolor=\"#044767\">\n"
                + "                <div style=\"display:inline-block; max-width:50%; min-width:100px; vertical-align:top; width:100%;\">\n"
                + "                    <table align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\n"
                + "                        <tr>\n"
                + "                            <td align=\"center\" valign=\"top\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 36px; font-weight: 800; line-height: 48px;\" class=\"mobile-center\">\n"
                + "                                <h1 style=\"font-size: 36px; font-weight: 800; margin: 0; color: #ffffff;\">Grupo02SC</h1>\n"
                + "                            </td>\n"
                + "                        </tr>\n"
                + "                    </table>\n"
                + "                </div>\n"
                + "                </td>\n"
                + "            </tr>\n"
                + "            <tr>\n"
                + "                <td align=\"center\" style=\"padding: 35px 35px 20px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                + "                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #333333; margin: 0;\">\n"
                + "                                SISTEMA DE GESTION TALLER DON JUAN\n"
                + "                            </h2>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "\n"
                + "                    <tr>\n"
                + "                        <td align=\"left\" style=\"padding-top: 20px;\">\n"
                + "                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n"
                + "                                <tr>\n"
                + "                                    <td width=\"100%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\">\n"
                + "                                        Respuesta Del Sistema\n"
                + "                                    </td>   \n"
                + "                                </tr>\n"
                + "                                <tr>\n"
                + "                                    <td align=\"center\" style=\" padding: 0px; background-color: #ff6600;\" bgcolor=\"#1b9ba3\">\n"
                + "                                        <table align=\"center\" border=\"2\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
//                + "                                            <tr>                                            \n"
//                + "                                                <td width=\"100%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; color: #ffffff; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                    " + s_res + "\n"
//                + "                                                </td>                                            \n"
//                + "                                            </tr>\n"
                + "                                        </table>\n"
                + "                                    </td> \n"
                + "                                </tr>\n"
                + "                            </table>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "                </td>\n"
                + "            </tr>\n"
                + "            <tr>\n"
                + "                <td align=\"center\" height=\"100%\" valign=\"top\" width=\"100%\" style=\"padding: 0 35px 35px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                + "                </td>\n"
                + "            </tr>\n"
                + "            <tr>\n"
                + "                <td align=\"center\" style=\" padding: 35px; background-color: #1b9ba3;\" bgcolor=\"#1b9ba3\">\n"
                + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">\n"
                + "                            <h2 style=\"font-size: 24px; font-weight: 800; line-height: 30px; color: #ffffff; margin: 0;\">\n"
                + "                                TECNOLOGIA WEB  II-2020\n"
                + "                            </h2>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "                </td>\n"
                + "            </tr>\n"
                + "            <tr>\n"
                + "                <td align=\"center\" style=\"padding: 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                + "                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px; padding: 5px 0 10px 0;\">\n"
                + "                            <p style=\"font-size: 14px; font-weight: 800; line-height: 18px; color: #333333;\">\n"
                + "                                SISTEMA VIA MAIL\n"
                + "                            </p>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px;\">\n"
                + "                            <p style=\"font-size: 14px; font-weight: 400; line-height: 20px; color: #777777;\">\n"
                + "                                FICTT - UAGRM   ,  Santa Cruz de la Sierra - Bolivia.\n"
                + "                            </p>\n"
                + "                        </td>\n"
                + "                    </tr>\n"
                + "                </table>\n"
                + "                </td>\n"
                + "            </tr>\n"
                + "        </table>\n"
                + "        </td>\n"
                + "    </tr>\n"
                + "</table>\n"
                + "    \n"
                + "</body>";
        return cad;       
        }
        return mensajeCorrecto("lista vacia");
        }
        return mensajeCorrecto("parametros incorrectos");
    }  
     
     public String regIngreso(String[] prt_parametros) {
            d_rutas.setNombreRuta(prt_parametros[0]);
            
            boolean b_res=d_rutas.regRuta();
            if (b_res) {
                return mensajeCorrecto("registro del Ingreso correctamentamente");
            }
            return mensajeError("problemas con la conexion! no se pudo ejecutar la consulta ");
    }
     
     public String editIngreso(String[] prt_parametros) {
            d_rutas.setId(Integer.parseInt(prt_parametros[0]));
            d_rutas.setNombreRuta(prt_parametros[1]);
          
            boolean b_res=d_rutas.editRutas();
            if (b_res) {
                return mensajeCorrecto("edicion del Ingreso correctamentamente");
            }
            return mensajeError("problemas con la conexion! no se pudo ejecutar la consulta ");
    }
     
      public String delIngreso(String[] prt_parametros) {
      
            d_rutas.setId(Integer.parseInt(prt_parametros[0]));
            boolean b_res=d_rutas.delRutas();
            if (b_res) {
                return mensajeCorrecto("eliminiacion de ingreso realizado correctamentamente");
            }
            return mensajeError("problemas con la conexion! no se pudo ejecutar la consulta ");
    }
     
      private String mensajeError(String p_parametro) {
        return  "<div class='error'><strong>ERROR!!! </strong><p class='texto-error'>"+p_parametro+"</p></div>";
    }

    private String mensajeCorrecto(String p_parametro) {
        return  "<div class='correcto'><strong>SUCCEFULL!!! </strong><p class='texto-error'>"+p_parametro+"</p></div>";
    }
    private boolean esNumero(String prt_parametro) {
        try {
            Integer.parseInt(prt_parametro);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean validarParaListRutas(String[] prt_parametros) {
        if (prt_parametros.length!=1) {
            return false;
        }
        if (!esNumero(prt_parametros[0])) {
            return false;
        }
        return true;
    }
}
