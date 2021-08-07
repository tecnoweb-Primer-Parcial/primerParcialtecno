/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Negocio;

import java.util.ArrayList;
import Datos.DEstadisticas;

/**
 *
 * @author Hp
 */
public class NEstadisticas {

    private DEstadisticas cotizacion;

    public NEstadisticas() {
        cotizacion = new DEstadisticas();
    }

    public String showEstadistica() {
        ArrayList<DEstadisticas> cotejecutivo = new ArrayList<>();
            cotejecutivo = cotizacion.estadisticasCotizaciones();
            if (cotejecutivo.size() > 0) {
                int total = 0;
                for (DEstadisticas i_estaditica : cotejecutivo) {
                    total = (i_estaditica.getVentas()) + total;
                }

                String s_res = "";

                for (int i = 0; i < cotejecutivo.size(); i++) {
                    DEstadisticas e = cotejecutivo.get(i);
                    double porcentaje = (double) (e.getVentas()* 100) / total;
                    s_res = s_res + "<!DOCTYPE html>\n" + "<html lang=\"en\">\n" + "<head>\n"
                            + "    <meta charset=\"UTF-8\">\n"
                            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "    <style>\n" + "        .barra {\n" + "            height: 30px;\n"
                            + "            text-align: center;\n" + "            line-height: 30px;\n"
                            + "            margin: 20px;\n" + "            color: black;\n" + "        }\n" + "\n"
                            + "        .enero {\n" + "            width: " + redondearDecimales(porcentaje, 2) + "px;\n"
                            + "            background-color: #349CF9;\n" + "        }\n" + "\n" + "    </style>\n"
                            + "</head>\n" + "<body>\n" + "    <div>\n" + "        <div class=\"barra enero\">\n"
                            + "      </div>\n" + "      <div>\n" + "           " + e.getNombre()+ "\n"
                            + redondearDecimales(porcentaje, 2) + "%" + "      </div>\n" + "    </div>\n" + "</body>\n"
                            + "</html></td>";
                }
                return mensajeCorrecto(s_res);

            }
        return mensajeError("<tbody>" + "<tr>"
                + "<td align=\"center\" bgcolor=\"#FFEEAE\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">\n"
                + "ERROR AL REALIZAR LA ESTADISTICA</td>",
                "<td align=\"center\" bgcolor=\"#FFEEAE\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">\n"
                + "Existe un Problema con los parametros! </td>",
                "<td align=\"center\" bgcolor=\"#FFEEAE\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 5px 5px 5px 5px;\">\n"
                + "Intente nuevamente y por favor verifique si sus datos estan correctos\n" + "Gracias!"
                + "</td>" + "</tr></tbody>");
    }

    public double redondearDecimales(double valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial;
        parteEntera = Math.floor(resultado);
        resultado = (resultado - parteEntera) * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        resultado = (resultado / Math.pow(10, numeroDecimales)) + parteEntera;
        return resultado;
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

    private String mensajeError(String p_parametro, String p_parametro1, String p_parametro2) {
        return  "<table style=\"width:100%;\">\n" + "<tr bgcolor= \"#1E90FF\"></br>\n" + "\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                + "                                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #FFFFFF; margin: 0;\">\n"
                + "                                                GRUPO\n"
                + "                                            </h2>\n"
                + "											<h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #FFFFFF; margin: 0;\">\n"
                + "                                                05 SA\n"
                + "                                            </h2>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "</table>\n" + "\n"
                + "<body style=\"margin: 0 !important; padding: 0 !important; background-color: #eeeeee;\" bgcolor=\"#eeeeee\">\n"
                + "                \n" + "                <!-- HIDDEN PREHEADER TEXT -->\n"
                + "                <div style=\"display: none; font-size: 1px; color: #1E90FF; line-height: 1px; font-family: Open Sans, Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\">\n"
                + "                COMANDOS PARA EL SISTEMA DE EXPRINTER \n" + "                </div>\n"
                + "                \n"
                + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"background-color: #D8E7E7;\" bgcolor=\"#D8E7E7\"></br>\n"
                + "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                            \n" + "                            <tr>\n"
                + "                                <td align=\"center\" style=\"padding: 35px 35px 20px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                                    <tr>\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                + "                                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #FFFFFF; margin: 0;\">\n"
                + "                                                EXPRINTER\n"
                + "                                            </h2>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "                \n" + "                                    <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\">\n"
                + "                                                        <font color=\"#FFFFFF\">" + p_parametro + "\n"
                + "                                                    </td>   \n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td align=\"center\" style=\" padding: 0px; background-color: #E6E6FA;\" bgcolor=\"#1b9ba3\">\n"
                + "                                                        <table align=\"center\" border=\"2\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "								" + p_parametro1 + "\n"
                + "                                                                     " + p_parametro2 + "\n"
                + "                                                        </table>\n"
                + "                                                    </td> \n"
                + "                                                </tr>\n"
                + "                                            </table>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "                                </table>\n" + "                                </td>\n"
                + "                            </tr>\n" + "                            <tr>\n"
                + "                                <td align=\"center\" style=\"padding: 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                                    <tr>\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px; padding: 5px 0 10px 0;\">\n"
                + "                                            <p style=\"font-size: 14px; font-weight: 800; line-height: 18px; color: #333333;\">\n"
                + "                                                SISTEMA VIA MAIL\n"
                + "                                            </p>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" style=\" padding: 35px; background-color: #E6E6FA;\" bgcolor=\"#1b9ba3\">\n"
                + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                                    <tr>\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">\n"
                + "                                            <h2 style=\"font-size: 24px; font-weight: 800; line-height: 30px; color: #ffffff; margin: 0;\">\n"
                + "                                                TECNOLOGIA WEB\n"
                + "                                            </h2>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "                                </table>\n" + "                                </td>\n"
                + "                            </tr>\n" + "                        </table>\n"
                + "                        </td>\n" + "                    </tr>\n" + "                </table>\n"
                + "                    \n" + "                </body>";
    }

    private String mensajeCorrecto(String p_parametro) {
        return  "<table style=\"width:100%;\">\n" + "<tr bgcolor= \"#1E90FF\"></br>\n" + "\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                + "                                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #FFFFFF; margin: 0;\">\n"
                + "                                                GRUPO\n"
                + "                                            </h2>\n"
                + "											<h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #FFFFFF; margin: 0;\">\n"
                + "                                                05 SA\n"
                + "                                            </h2>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "</table>\n" + "\n"
                + "<body style=\"margin: 0 !important; padding: 0 !important; background-color: #eeeeee;\" bgcolor=\"#eeeeee\">\n"
                + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"background-color: #D8E7E7;\" bgcolor=\"#D8E7E7\"></br>\n"
                + "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                            \n" + "                            <tr>\n"
                + "                                <td align=\"center\" style=\"padding: 35px 35px 20px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                                    <tr>\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                + "                                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #0B36FA; margin: 0;\">\n"
                + "                                                EXPRINTER\n"
                + "                                            </h2>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "                \n" + "                                    <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\">\n"
                + "                                                        ESTADISTICAS\n"
                + "                                                    </td>   \n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td align=\"center\" style=\" padding: 0px; background-color: #E0FFFF;\" bgcolor=\"#1b9ba3\">\n"
                + "                                                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "																" + p_parametro + "\n"
                + "                                                        </table>\n"
                + "                                                    </td> \n"
                + "                                                </tr>\n"
                + "                                            </table>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "                                </table>\n" + "                                </td>\n"
                + "                            </tr>\n" + "                            <tr>\n"
                + "                                <td align=\"center\" height=\"100%\" valign=\"top\" width=\"100%\" style=\"padding: 0 35px 35px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                + "                                </td>\n" + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" style=\"padding: 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                                    <tr>\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px; padding: 5px 0 10px 0;\">\n"
                + "                                            <p style=\"font-size: 14px; font-weight: 800; line-height: 18px; color: #333333;\">\n"
                + "                                                SISTEMA VIA MAIL\n"
                + "                                            </p>\n"
                + "                                        </td>\n" + "                                    </tr>\n"                                  
                + "                            <tr>\n"
                + "                                <td align=\"center\" style=\" padding: 35px; background-color: #1b9ba3;\" bgcolor=\"#1b9ba3\">\n"
                + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                                    <tr>\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">\n"
                + "                                            <h2 style=\"font-size: 24px; font-weight: 800; line-height: 30px; color: #ffffff; margin: 0;\">\n"
                + "                                                TECNOLOGIA WEB  I-2021\n"
                + "                                            </h2>\n"
                + "                                        </td>\n" + "                                    </tr>\n"
                + "                                </table>\n" + "                                </td>\n"
                + "                            </tr>\n" + "                        </table>\n"
                + "                        </td>\n" + "                    </tr>\n" + "                </table>\n"
                + "                    \n" + "                </body>";
    }
}
