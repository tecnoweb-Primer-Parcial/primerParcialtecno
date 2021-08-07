/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Negocio.NApertura;
import Negocio.NEmpleados;
import Negocio.NRutas;
import Negocio.NClientes;
import Negocio.NEmpresas;
import Negocio.NCotizaciones;
import Negocio.NEstadisticas;
import Negocio.NServices;
import Negocio.NReporte;
import Negocio.NStatusRuta;

import java.io.IOException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hp
 */
public class Manejador {

    private int max = 0;
    private PopMessage m_PopMessage2;
    private SMTPMessage m_SMTPMessage2;

    private String g_metodo = "";
    private String g_origen = "";

    public Manejador() {
        m_PopMessage2 = new PopMessage();
        max = m_PopMessage2.getSize();
        m_PopMessage2.cerrar();
    }

    public void leer() {
        m_PopMessage2 = new PopMessage();
        if (m_PopMessage2.getSize() > max) {
            max++;
            //analizarLineas(m_PopMessage2.getMessageArray(max));
            boolean estado = analizarLineasSi(m_PopMessage2.getMessageArray(max));
            if (estado) {
                enviar_respuesta_(true);
            } else {
                enviar_respuesta_(false);
            }
        }
        m_PopMessage2.cerrar();
    }

    public void analizarLineas(List<String> messageArray) {
        String lineaMetodo = "";
        String lineaUsuario = "";
        int i = 0;
        for (String line : messageArray) {
            //System.out.println(line.toString());
            if (line.contains("Return-Path:")) {
                lineaUsuario = line;
            }
            if (line.contains("Subject:") || line.contains("subject:")) {
                if (line.regionMatches(0, "Subject:", 0, 8) || line.regionMatches(0, "subject:", 0, 8)) {
                    while (!messageArray.get(i).contains("]")) {
                        lineaMetodo = lineaMetodo + messageArray.get(i);
                        i++;
                    }
                    lineaMetodo = lineaMetodo + messageArray.get(i);
                }
            }
            i++;
        }
        System.out.println("linea encontrada=>" + lineaMetodo);
        //i++;

        //obtener mail usuario
        String mailFrom = getCorreoEmisor(lineaUsuario);
        System.out.println(mailFrom);

        //obtener metodo
        //posisiones para metodo y parametros
        int posIni = lineaMetodo.indexOf("[");
        int posFin = lineaMetodo.indexOf("]");
        String metodo = getMetodo(lineaMetodo, posIni);
        System.out.println("metodo-" + metodo);
        //obtener parametros        
        String[] parametros = getParametros(lineaMetodo, posIni, posFin);
        System.out.println(parametros.toString());
        ejecutarMetodos(metodo, parametros, mailFrom);

    }

    private String getMetodo(String lineaMetodo, int posIni) {
        //obtener metodo
        String metodo = lineaMetodo.substring(8, posIni).trim();
        metodo = metodo.toUpperCase();
        if (metodo.length() == 0) {
            metodo = "AYUDA";
        }
        return metodo;
    }

    private String[] getParametros(String lineaMetodo, int posIni, int posFin) {
        String[] parametros = lineaMetodo.substring(posIni + 1, posFin).split(",");
        return parametros;
    }

    private String getCorreoEmisor(String lineaUsuario) {
        //posiciones para usuario mail
        int posIni1 = lineaUsuario.indexOf("<");
        int posFin1 = lineaUsuario.indexOf(">");
        return lineaUsuario.substring(posIni1 + 1, posFin1);
    }

    private void enviarMensajeCorreoOrigen(String prt_mailFrom, String prt_asunto, String prt_mensaje) {
        m_SMTPMessage2 = new SMTPMessage();
        m_SMTPMessage2.sendMessage("grupo05sa@tecnoweb.org.bo", prt_mailFrom, prt_asunto, prt_mensaje);
        m_SMTPMessage2.cerrar();
    }

    private boolean analizarLineasSi(List<String> messageArray) {
        g_origen = "";
        g_metodo = "";

        String lineaMetodo = "";
        String lineaUsuario = "";
        int i = 0;
        for (String line : messageArray) {
            //System.out.println(line.toString());
            if (line.contains("Return-Path:")) {
                lineaUsuario = line;
                //guardar linea correo origen
                g_origen = lineaUsuario;
            }
            if (line.contains("Subject:") || line.contains("subject:")) {
                if (line.regionMatches(0, "Subject:", 0, 8) || line.regionMatches(0, "subject:", 0, 8)) {
                    while (!messageArray.get(i).contains("]")) {
                        lineaMetodo = lineaMetodo + messageArray.get(i);
                        i++;
                    }

                    lineaMetodo = lineaMetodo + messageArray.get(i);
                    //guardar linea metodo globa;

                    g_metodo = lineaMetodo;
                    return true;
                }
            }
            i++;
        }
        return false;
    }

    private void enviar_respuesta_(boolean b) {
        if (b) {
            String mailFrom = getCorreoEmisor(g_origen);

            int posIni = g_metodo.indexOf("[");
            int posFin = g_metodo.indexOf("]");
            String metodo = getMetodo(g_metodo, posIni);

            String[] parametros = getParametros(g_metodo, posIni, posFin);

            ejecutarMetodos(metodo, parametros, mailFrom);
        } else {
            System.out.println("lo siento no se pudo mandar no se encontro el metodo.. \r\n");
        }
    }

    private void ejecutarMetodos(String prt_asunto, String[] prt_parametros, String prt_mailFrom) {
        String mensaje = "";
        System.out.println(prt_asunto + "...\r\n");

        switch (prt_asunto) {
            //Angel Oni Terceros
            /**
             * ************************
             */
            //--------NRUTA-------------
            case "LISTRUTA": //metodo para listar RUTA
                System.out.println(prt_asunto + "...\r\n");
                NRutas n_rutas = new NRutas();
                String res_NRutas = n_rutas.listRutas(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_NRutas));
                break;
            case "REGISRUTA": // REGISTRAR RUTA
                NRutas regNRutas = new NRutas();
                String res_regRuta = regNRutas.regRutas(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_regRuta));
                break;
            case "EDITRUTA": // editar un RUTA
                NRutas editNRutas = new NRutas();
                String res_editNRutas = editNRutas.editRutas(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_editNRutas));
                break;
            case "DELRUTA": // eliminar un RUTA
                NRutas delNRutas = new NRutas();
                String res_delNRutas = delNRutas.delEmpresa(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_delNRutas));
                break;
                
            //**************
            //---------STATUS RUTA---------------------
            //-----------------------------------------
            case "LISTSTATUSRUTA": //metodo para listar RUTA
                System.out.println(prt_asunto + "...\r\n");
                NStatusRuta n_status_rutas = new NStatusRuta();
                String res_NStatusRuta = n_status_rutas.listStatusRuta(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_NStatusRuta));
                break;
            case "REGSTATUSRUTA": // REGISTRAR RUTA
                NStatusRuta regNStatusRutas = new NStatusRuta();
                String res_regStatusRuta = regNStatusRutas.regStatusRuta(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_regStatusRuta));
                break;
            case "EDITSTATUSRUTA": // editar un RUTA
                NStatusRuta editNStatusRutas = new NStatusRuta();
                String res_editNStatusRutas = editNStatusRutas.editStatusRuta(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_editNStatusRutas));
                break;
            case "DELSTATUSRUTA": // eliminar un RUTA
                NStatusRuta delNStatusRutas = new NStatusRuta();
                String res_delNStatusRutas = delNStatusRutas.delStatusRuta(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_delNStatusRutas));
                break;
                 
            //**************
            //---------APERTURA---------------------
            //-----------------------------------------
            case "LISTAPERTURA": //metodo para listar RUTA
                System.out.println(prt_asunto + "...\r\n");
                NApertura n_apertura = new NApertura();
                String res_NApertura = n_apertura.listApertura(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_NApertura));
                break;
            case "REGAPERTURA": // REGISTRAR RUTA
                NApertura regNApertura = new NApertura();
                String res_regApertura = regNApertura.regApertura(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_regApertura));
                break;
            case "EDITAPERTURA": // editar un RUTA
                NApertura editNApertura = new NApertura();
                String res_editNApertura = editNApertura.editApertura(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_editNApertura));
                break;
            case "DELAPERTURA": // eliminar un RUTA
                NApertura delNApertura = new NApertura();
                String res_delNApertura = delNApertura.delApertura(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_delNApertura));
                break;
                  
            //Cristhian Vargas Quiroz
           //------EMPLEADOS-----------------
           //**********************************
            case "LISTEMPLEADOS": //metodo para listar clientes
                System.out.println(prt_asunto + "...\r\n");
                NEmpleados m_Nempleados = new NEmpleados();
                String s_resEmpleados = m_Nempleados.listEmpleqados(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(s_resEmpleados));
                break;
            case "REGEMPLEADOS": // REGISTRAR EMPLEADOS
                NEmpleados regEmpleados = new NEmpleados();
                String res_regEmpleado = regEmpleados.regEmpleado(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_regEmpleado));
                break;
            case "EDITEMPLEADOS": // editar un empleado
                NEmpleados editEmpleado = new NEmpleados();
                String res_editEmpleado = editEmpleado.editEmpleado(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_editEmpleado));
                break;
            case "DELEMPLEADOS": // eliminar un empleado
                NEmpleados delEmpleado = new NEmpleados();
                String res_delEmpleado = delEmpleado.delEmpleado(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_delEmpleado));
                break;
            //-------------------------------    
            //------CLIENTES-----------------
            //**********************************
            
            case "LISTCLIENTES": // listar todos los clientes
                NClientes listClientes = new NClientes();
                String res_listClientes = listClientes.listClientes(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_listClientes));
                break;
            case "REGCLIENTE": // registrar un nuevo cliente
                NClientes regCliente = new NClientes();
                String res_regCliente = regCliente.regClientes(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_regCliente));
                break;
            case "EDITCLIENTE": // editar un cliente
                NClientes editCliente = new NClientes();
                String res_editCliente = editCliente.editCliente(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_editCliente));
                break;
            case "DELCLIENTE": // eliminar un cliente
                NClientes delCliente = new NClientes();
                String res_delCliente = delCliente.delCliente(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_delCliente));
                break;
            
            //-------------------------------    
            //------EMPRESA-----------------
            //**********************************
            
            case "LISTEMPRESAS": // listar todas las empresas
                NEmpresas listEmpresas = new NEmpresas();
                String res_listEmpresas = listEmpresas.listEmpresas(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_listEmpresas));
                break;
            case "REGEMPRESA": // registrar una nueva empresa
                NEmpresas regEmpresa = new NEmpresas();
                String res_regEmpresa = regEmpresa.regEmpresa(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_regEmpresa));
                break;
            case "EDITEMPRESA": // editar una empresa
                NEmpresas editEmpresa = new NEmpresas();
                String res_editEmpresa = editEmpresa.editEmpresa(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_editEmpresa));
                break;
            case "DELEMPRESA": // eliminar una empresa
                NEmpresas delEmpresa = new NEmpresas();
                String res_delEmpresa = delEmpresa.delEmpresa(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_delEmpresa));
                break;
            //Denilson Santa Cruz
            //-------------------------------    
            //------COTIZACIONES-----------------
            //**********************************
            
            case "LISTCOTIZACIONES": //metodo para listar cotizaciones
                System.out.println(prt_asunto + "...\r\n");
                NCotizaciones m_Ncotizaciones = new NCotizaciones();
                String s_resCotizaciones = m_Ncotizaciones.listcotizaciones(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(s_resCotizaciones));
                break;
            case "REGCOTIZACIONES": // REGISTRAR COTIZACIONES
                NCotizaciones regCotizaciones = new NCotizaciones();
                String res_regCotizaciones = regCotizaciones.regcotizaciones(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_regCotizaciones));
                break;
            case "EDITCOTIZACIONES": // editar una cotizacion
                NCotizaciones editCotizacion = new NCotizaciones();
                String res_editCotizacion = editCotizacion.editcotizaciones(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_editCotizacion));
                break;
            case "DELCOTIZACION": // eliminar una cotizacion
                NCotizaciones delCotizacion = new NCotizaciones();
                String res_delCotizacion = delCotizacion.delcotizaciones(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_delCotizacion));
                break;

            //-------------------------------    
            //------SERVICIOS-----------------
            //**********************************
            
            case "LISTSERVICIOS": // listar todos los servicios
                NServices listServices = new NServices();
                String res_listServices = listServices.listservices(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_listServices));
                break;
            case "REGSERVICIO": // registrar un nuevo servicio
                NServices regServices = new NServices();
                String res_regServices = regServices.regservices(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_regServices));
                break;
            case "EDITSERVICIO": // editar un servicio
                NServices editService = new NServices();
                String res_editService = editService.editservices(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_editService));
                break;
            case "DELSERVICIO": // eliminar un servicio
                NServices delService = new NServices();
                String res_delService = delService.delservices(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_delService));
                break;
            case "REPORTE": //GENERA UN REPORTE
                NReporte listReporte = new NReporte();
                String res_listReporte = listReporte.generarReporte(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_listReporte));
                break;
            case "REPORTEDATE":
                NReporte listReporteDate = new NReporte();
                String res_lisReporteDate = listReporteDate.generarReporteDate(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_lisReporteDate));
                break;
                
            case "ESTADISTICA":
                NEstadisticas cotizaciones = new NEstadisticas();
                String res_estadistica = cotizaciones.showEstadistica();
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(res_estadistica));
            //Cristhian Vargas Quiroz

            case "AYUDA": //metodo para comandos
                System.out.println("La cantidad de caracteres de:" + prt_asunto.length());
                System.out.println("la direccion origen es: " + prt_mailFrom);
                System.out.println("el asunto del mensaje es: " + prt_asunto);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeAyuda());
                break;
            default:
                System.out.println("La cantidad de caracteres de:" + prt_asunto.length());
                System.out.println("la direccion origen es: " + prt_mailFrom);
                System.out.println("el asunto del mensaje es: " + prt_asunto);

                System.out.println(prt_asunto + " no existe ...\r\n");
                String mensajeHelp = "<body>\n"
                        + "</body>\n"
                        + "\n"
                        + "<table style=\"width:100%;\">\n"
                        + "<tr bgcolor= \"#A9A9A9\"></br>\n"
                        + "\n"
                        + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                        + "                                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #333333; margin: 0;\">\n"
                        + "                                                GRUPO\n"
                        + "                                            </h2>\n"
                        + "											<h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #333333; margin: 0;\">\n"
                        + "                                                05 SA\n"
                        + "                                            </h2>\n"
                        + "                                        </td>\n"
                        + "                                    </tr>\n"
                        + "</table>\n"
                        + "\n"
                        + "<body style=\"margin: 0 !important; padding: 0 !important; background-color: #eeeeee;\" bgcolor=\"#eeeeee\">\n"
                        + "                \n"
                        + "                <!-- HIDDEN PREHEADER TEXT -->\n"
                        + "                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                        + "                    <tr>\n"
                        + "                        <td align=\"center\" style=\"background-color: #D8E7E7;\" bgcolor=\"#D8E7E7\"></br>\n"
                        + "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                        + "                            \n"
                        + "                            <tr>\n"
                        + "                                <td align=\"center\" style=\"padding: 35px 35px 20px 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                        + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                        + "                                    <tr>\n"
                        + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                        + "                                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #0B36FA; margin: 0;\">\n"
                        + "                                                EXPRINTER\n"
                        + "                                            </h2>\n"
                        + "                                        </td>\n"
                        + "                                    </tr>\n"
                        + "                \n"
                        + "                                    <tr>\n"
                        + "                                        <td align=\"left\" style=\"padding-top: 20px;\">\n"
                        + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n"
                        + "                                                <tr>\n"
                        + "                                                    <td width=\"100%\" align=\"left\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px; border-top: 3px solid #eeeeee; border-bottom: 3px solid #eeeeee;\">\n"
                        + "                                                        <font color=\"000000\"> ERROR DE COMANDO! "
                        + "                                                    </td>   \n"
                        + "                                                </tr>\n"
                        + "                                                <tr>\n"
                        + "                                                    <td align=\"center\" style=\" padding: 0px; background-color: #1E90FF;\" bgcolor=\"#00BFFF\">\n"
                        + "                                                        <table align=\"center\" border=\"2\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                        + "																Error en la instruccion, porfavor revisa los comandos enviando [AYUDA] "
                        + "                                                        </table>\n"
                        + "                                                    </td> \n"
                        + "                                                </tr>\n"
                        + "                                            </table>\n"
                        + "                                        </td>\n"
                        + "                                    </tr>\n"
                        + "                                </table>\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                            <tr>\n"
                        + "                                <td align=\"center\" style=\"padding: 35px; background-color: #ffffff;\" bgcolor=\"#ffffff\">\n"
                        + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                        + "                                    <tr>\n"
                        + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px; padding: 5px 0 10px 0;\">\n"
                        + "                                            <p style=\"font-size: 14px; font-weight: 800; line-height: 18px; color: #333333;\">\n"
                        + "                                                SISTEMA VIA MAIL\n"
                        + "                                            </p>\n"
                        + "                                        </td>\n"
                        + "                                    </tr>\n"
                        + "                                    <tr>\n"
                        + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px;\">\n"
                        + "                                            <p style=\"font-size: 14px; font-weight: 400; line-height: 20px; color: #777777;\">\n"
                        + "                                                FICTT - UAGRM.\n"
                        + "                                            </p>\n"
                        + "                                        </td>\n"
                        + "                                    </tr>\n"
                        + "                            <tr>\n"
                        + "                            </tr>\n"
                        + "                                    <tr>\n"
                        + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 14px; font-weight: 400; line-height: 24px;\">\n"
                        + "                                            <p style=\"font-size: 14px; font-weight: 400; line-height: 20px; color: #777777;\">\n"
                        + "                                                Santa Cruz - Bolivia.\n"
                        + "                                            </p>\n"
                        + "                                        </td>\n"
                        + "                                    </tr>\n"
                        + "                                </table>\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "							\n"
                        + "                            <tr>\n"
                        + "                                <td align=\"center\" style=\" padding: 35px; background-color: #A9A9A9;\" bgcolor=\"#1b9ba3\">\n"
                        + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                        + "                                    <tr>\n"
                        + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">\n"
                        + "                                            <h2 style=\"font-size: 20px; font-weight: 800; line-height: 30px; color: #ffffff; margin: 0;\">\n"
                        + "                                                TECNOLOGIA WEB\n"
                        + "                                            </h2>\n"
                        + "                                        </td>\n"
                        + "                                    </tr>\n"
                        + "                                </table>\n"
                        + "                                </td>\n"
                        + "                            </tr>\n"
                        + "                        </table>\n"
                        + "                        </td>\n"
                        + "                    </tr>\n"
                        + "                </table>\n"
                        + "                    \n"
                        + "                </body>";
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(mensajeHelp));
                break;
        }
    }

    public String getMensajeAyuda() {
        String titulo = "<div>\n"
                + " <h2>Comandos de la aplicacion EXPRINTER</h2>\n"
                + "</div>";
        String body = "<table style=\"width:100%;\">\n"
                + "<tr bgcolor= \"#000000\">\n"
                + "<td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                + "                                            <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #FFFFFF; margin: 0;\">\n"
                + "                                                GRUPO\n"
                + "                                            </h2>\n"
                + "                                             <h2 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #FFFFFF; margin: 0;\">\n"
                + "                                                05 SA\n"
                + "                                            </h2>\n"
                + "</td>\n"
                + "</tr>\n"
                + "</table>";
        String cad = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
                + "                    <tr>\n"
                + "                        <td align=\"center\" style=\"background-color: #E3E3E3;\" bgcolor=\"#E3E3E3\">\n"
                + "                        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" valign=\"top\" style=\"font-size:0; padding: 35px;\" bgcolor=\"#E3E3E3\">\n"
                + "                                <div style=\"display:inline-block; max-width:50%; min-width:100px; vertical-align:top; width:100%;\">\n"
                + "                                    <table align=\"left\" border-radius=\"20px\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:300px;\">\n"
                + "                                        <tr>\n"
                + "                                            <td align=\"center\" valign=\"middle\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 36px; font-weight: 800; line-height: 48px;\" class=\"mobile-center\">\n"
                + "                                                <h1 style=\"font-size: 36px; font-weight: 800; margin: 0; color: #2F4F4F;\">SISTEMA MAIL EXPRINTER</h1>\n"
                + "                                            </td>\n"
                + "                                             <head>\n"
                + "                                              <script src=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js\" integrity=\"sha512-RXf+QSDCUQs5uwRKaDoXt55jygZZm2V++WUZduaU/Ui/9EGp3f/2KZVahFZBKGH0s774sd3HmrhUy+SgOFQLVQ==\" crossorigin=\"anonymous\" referrerpolicy=\"no-referrer\"></script>\"\n"
                + "                                             </head>\n"
                + "                                            </tr>\n"
                + "                                    </table>\n"
                + "                                </div>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td align=\"center\" style=\"padding: 35px 35px 20px 35px; background-color: #E3E3E3;\" bgcolor=\"#E3E3E3\">\n"
                + "                                <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width:600px;\">\n"
                + "                                    <tr>\n"
                + "                                        <td align=\"center\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding-top: 25px;\">            \n"
                + "                                            <h3 style=\"font-size: 30px; font-weight: 800; line-height: 36px; color: #FFFFFF; margin: 0;\">\n"
                + "                                                Comandos de Ayuda para poder interactuar con el Sistema Mail de EXPRINTER\n"
                + "                                            </h3>\n"
                + "                                        </td>\n"
                + "                                    </tr>\n";
        String empleadosHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GESTIONAR EMPLEADOS \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        LISTAR EMPLEADOS:<br><br>\n"
                + "                                                        LISTEMPLEADOS[0] PARA LISTAR TODOS LOS EMPLEADOS <br>\n"
                + "                                                        REGISTRAR EMPLEADO:<br><br>\n"
                + "                                                        PARA REGISTRAR UN EMPLEADO DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        REGEMPLEADOS[nombre,email,password,ci,telefono,direccion,genero,nacimiento,rol=1 o 2]<br><br>"
                + "                                                        EJEMPLO:<br>\n"
                + "                                                        REGEMPLEADOS[Cristhian Vargas,cristhian@gmail.com,12345678,9636927,78588196,calle olimpo,M,1997-02-21,2] <br><br>"
                + "                                                        EDITAR EMPLEADO:<br><br>\n"
                + "                                                        PARA EDITAR UN EMPLEADO DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        EDITEMPLEADOS[id,nombre,email,ci,telefono,direccion,genero,nacimiento]<br><br>"
                + "                                                        EJEMPLO:<br>"
                + "                                                        EDITEMPLEADO[1,Cristhian Quiroz,criss@gmail.com,9685742,78589632,calle oliver,M,1997-02-23]<br><br>"
                + "                                                        Eliminar un empleado:<br><br>"
                + "                                                        DELEMPLEADOS[id]<br><br>"
                + "                                                        EJEMPLO:<br>"
                +                                                          "DELEMPLEADOS[4]<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
        String clientesHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GESTIONAR CLIENTES \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        LISTAR CLIENTES:<br><br>\n"
                + "                                                        LISTCLIENTES[0] PARA LISTAR TODOS LOS CLIENTES <br>\n"
                + "                                                        REGISTRAR CLIENTE:<br><br>\n"
                + "                                                        PARA REGISTRAR UN CLIENTE DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        REGCLIENTE[nombre,iniciales,cel,email,emailconf,observaciones,ejecutivo=1...,compañia=1....]<br><br>"
                + "                                                        EJEMPLO:<br>\n"
                + "                                                        REGCLIENTE[Cristhian Vargas,CVQ,78541263,cristhian78@gmail.com,cristhian78@gmail.com,ninguna,4,2] <br><br>"
                + "                                                        EDITAR CLIENTE:<br><br>\n"
                + "                                                        PARA EDITAR UN CLIENTE DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        EDITCLIENTE[id,nombre,iniciales,cel,email,emailconf,observaciones]<br><br>"
                + "                                                        EJEMPLO:<br>"
                + "                                                        EDITCLIENTE[5,Cristhian vasques,CVQ,78588196,cris7845@gmail.com,cris7845@gmail.com,ninguna]<br><br>"
                + "                                                        Eliminar un cliente:<br><br>"
                + "                                                        DELCLIENTE[id]<br><br>"
                + "                                                        EJEMPLO:<br>"
                +                                                          "DELCLIENTE[4]<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
        String empresaHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GESTIONAR EMPRESAS \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        LISTAR EMPRESAS:<br><br>\n"
                + "                                                        LISTEMPRESAS[0] PARA LISTAR TODAS LAS EMPRESAS <br>\n"
                + "                                                        REGISTRAR EMPRESA:<br><br>\n"
                + "                                                        PARA REGISTRAR UNA EMPRESA DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        REGEMPRESA[nombre,NIT,razonSocial,sitioWeb,direccion,telefono,fax,ubicacion,sector,tipo]<br><br>"
                + "                                                        EJEMPLO:<br>\n"
                + "                                                        REGEMPRESA[Fiambres LOLA,784512458,SA,google.com,barrio olimpo,3569856,78588196,norte,fiambres,privado] <br><br>"
                + "                                                        EDITAR EMPRESA:<br><br>\n"
                + "                                                        PARA EDITAR UNA EMPRESA DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        EDITEMPRESA[id,nombre,NIT,razonSocial,sitioWeb,direccion,telefono,fax,ubicacion,sector,tipo]<br><br>"
                + "                                                        EJEMPLO:<br>"
                + "                                                        EDITEMPRESA[6,EMPRESA lola,784512548,SRL,google.com,barrio olimpo,3556985,3556985,zona sur,fiambres,privado]<br><br>"
                + "                                                        Eliminar una empresa:<br><br>"
                + "                                                        DELEMPRESA[id]<br><br>"
                + "                                                        EJEMPLO:<br>"
                +                                                          "DELEMPRESA[4]<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
        
        String cotizacionesHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GESTIONAR COTIZACIONES \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        LISTAR COTIZACIONES:<br><br>\n"
                + "                                                        LISTCOTIZACIONES[0] PARA LISTAR TODAS LAS EMPRESAS <br>\n"
                + "                                                        REGISTRAR COTIZACION:<br><br>\n"
                + "                                                        PARA REGISTRAR UNA COTIZACION DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        REGCOTIZACIONES[codigo,cliente,fecha cotizacion,ejecutivo,tarifa,servicio,estado,empresa]<br><br>"
                + "                                                        EJEMPLO:<br>\n"
                + "                                                        REGCOTIZACIONES[OMX-248,CLASS MASTER,2021-08-21,Kelly escobar,5000,1,2,2] <br><br>"
                + "                                                        EDITAR COTIZACION:<br><br>\n"
                + "                                                        PARA EDITAR UNA COTIZACION DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        EDITCOTIZACIONES[id,codigo,fecha,ejecutivo,tarifa]<br><br>"
                + "                                                        EJEMPLO:<br>"
                + "                                                        EDITCOTIZACIONES[3,OMN*518,2021-07-22,Kelly Escobar,3500]<br><br>"
                + "                                                        Eliminar una cotizacion:<br><br>"
                + "                                                        DELCOTIZACION[id]<br><br>"
                + "                                                        EJEMPLO:<br>"
                +                                                          "DELCOTIZACION[4]<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
        String serviciosHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GESTIONAR SERVICIOS \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        LISTAR SERVICIOS:<br><br>\n"
                + "                                                        LISTSERVICIOS[0] PARA LISTAR TODOS LOS SERVICIOS <br>\n"
                + "                                                        REGISTRAR SERVICIO:<br><br>\n"
                + "                                                        PARA REGISTRAR UN SERVICIO DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        REGSERVICIO[nombre,codigo,descripcion,estado]<br><br>"
                + "                                                        EJEMPLO:<br>\n"
                + "                                                        REGSERVICIO[Mudanzas 2,OMI,solo en la ciudad] <br><br>"
                + "                                                        EDITAR SERVICIO:<br><br>\n"
                + "                                                        PARA EDITAR UN SERVICIO DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        EDITSERVICIO[id,nombre,codigo,descripcion]<br><br>"
                + "                                                        EJEMPLO:<br>"
                + "                                                        EDITSERVICIO[5,mudanza,OML,solo ciudad]<br><br>"
                + "                                                        Eliminar una SERVICIO:<br><br>"
                + "                                                        DELSERVICIO[id]<br><br>"
                + "                                                        EJEMPLO:<br>"
                +                                                          "DELSERVICIO[5]<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
        String reporteHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GENERAR REPORTES \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        GENERAR REPORTE:<br><br>\n"
                + "                                                        REPORTE[0] PARA MOSTRAR UN REPORTE DE COTIZACIONES POR EJECUTIVO <br>\n"
                + "                                                        GENERAR REPORTE POR AÑO:<br><br>\n"
                + "                                                        REPORTEDATE[0] PARA MOSTRAR UN REPORTE DEL SISTEMA MAIL ANUAL<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
        String statusRutaHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GESTIONAR ÈSTADO DE RUTAS \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        LISTAR ESTADOS DE RUTAS:<br><br>\n"
                + "                                                        LISTSTATUSRUTA: [0] PARA LISTAR TODOS LOS SERVICIOS <br>\n"
                + "                                                        REGISTRAR ESTADO DE RUTA:<br><br>\n"
                + "                                                        PARA REGISTRAR UN ESTADO DE RUTA DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        REGSTATUSRUTA[ruta,cotizacion,estado]<br><br>"
                + "                                                        EJEMPLO:<br>\n"
                + "                                                        REGSTATUSRUTA[Mudanzas 2,OMI,solo en la ciudad] <br><br>"
                + "                                                        EDITAR ESTADO DE LA RUTA:<br><br>\n"
                + "                                                        PARA EDITAR UN ESTADO DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        EDITSTATUSRUTA[id,ruta,cotizacion,estado]<br><br>"
                + "                                                        EJEMPLO:<br>"
                + "                                                        EDITSTATUSRUTA[1,3,aceptado]<br><br>"
                + "                                                        Eliminar una estado de ruta:<br><br>"
                + "                                                        DELSTATUSRUTA[id]<br><br>"
                + "                                                        EJEMPLO:<br>"
                +                                                          "DELSTATUSRUTA[2]<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
                String aperturaHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GESTIONAR APERTURAS CONTABLES \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        LISTAR APERTURAS DISPONIBLES:<br><br>\n"
                + "                                                        LISTAPERTURA: [0] PARA LISTAR TODOS LAS APERTURAS <br>\n"
                + "                                                        REGISTRAR UN NUEVA APERTURA:<br><br>\n"
                + "                                                        PARA REGISTRAR UNA APERTURA DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        REGAPERTURA[codigo,fecha inicial,fecha final,comentario,cotizacion]<br><br>"
                + "                                                        EJEMPLO:<br>\n"
                + "                                                        REGAPERTURA[OMX,2021-05-12,2021-06-02,en evaluacion,3] <br><br>"
                + "                                                        EDITAR apertura contable:<br><br>\n"
                + "                                                        PARA EDITAR UNA APRETURA DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        EDITAPERTURA[ID,codigo,fecha inicial, fecha final,comentario,cotizacion]<br><br>"
                + "                                                        EJEMPLO:<br>"
                + "                                                        EDITAPERTURA[OMX,2021-06-12,2021-06-26]<br><br>"
                + "                                                        Eliminar una apertura:<br><br>"
                + "                                                        DELAPERTURA[id]<br><br>"
                + "                                                        EJEMPLO:<br>"
                +                                                          "DELAPERTURA[2]<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
                String rutaHelp = "                          <tr>\n"
                + "                                        <td align=\"left\" style=\"padding-top: 20px; \"background-color: #FF8000;\">\n"
                + "                                            <table cellspacing=\"0\" cellpadding=\"0\" border=\"4\" background-color: #FF8000;\" width=\"100%\">\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\"  background-color: #00CED1;\" bgcolor=\"#000000\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 800; line-height: 24px; padding: 10px;\">\n"
                + "                                                GESTIONAR RUTAS \n"
                + "                                                    </td>\n"
                + "                                                </tr>\n"
                + "                                                <tr>\n"
                + "                                                    <td width=\"100%\" align=\"left\" background-color: #FFFAF0;\" bgcolor=\" #FFFAF0\" style=\"font-family: Open Sans, Helvetica, Arial, sans-serif; font-size: 16px; font-weight: 400; line-height: 24px; padding: 15px 10px 5px 10px;\">\n"
                + "                                                        <br>"
                + "                                                        LISTAR RUTAS DISPONIBLES:<br><br>\n"
                + "                                                        LISTAPERTURA: [0] PARA LISTAR TODOS LAS RUTAS <br>\n"
                + "                                                        REGISTRAR UN NUEVA RUTAS:<br><br>\n"
                + "                                                        PARA REGISTRAR UNA RUTAS DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        REGRUTAS[NOMBRE]<br><br>"
                + "                                                        EJEMPLO:<br>\n"
                + "                                                        REGISRUTA[CBBA-PN] <br><br>"
                + "                                                        EDITAR apertura contable:<br><br>\n"
                + "                                                        PARA EDITAR UNA RUTA DEBE CONTENER LOS SIGUIENTES DATOS:<br><br>\n"
                + "                                                        EDITRUTAS[ID,NOMBRE]<br><br>"
                + "                                                        EJEMPLO:<br>"
                + "                                                        EDITRUTAS[2,SNT-LPZ]<br><br>"
                + "                                                        Eliminar una ruta:<br><br>"
                + "                                                        DELRUTA[id]<br><br>"
                + "                                                        EJEMPLO:<br>"
                +                                                          "DELRUTA[2]<br>\n"
                + "                                                    </td>\n"
                + "                                                </tr>\n";
                
        return "Content-Type:text/html;\r\n<html>" + titulo + body + cad + empleadosHelp + clientesHelp + empresaHelp + cotizacionesHelp + serviciosHelp + statusRutaHelp + aperturaHelp + rutaHelp + reporteHelp + "</tr></td></table></table></table>" + "</html>";
    }

    public String getMensajeTabla(String res) {
        return "Content-Type:text/html;\r\n<html>" + res + "</html>";

    }

    public String getMensajeRespuesta(String res) {
        return "Content-Type:text/html;\r\n<html>" + res + "</html>";
    }
}
