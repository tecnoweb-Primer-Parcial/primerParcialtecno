/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Negocio.NEmpleados;
import Negocio.NRutas;
import Negocio.NClientes;
import Negocio.NEmpresas;
import Negocio.NCotizaciones;
import Negocio.NServices;

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
            /***************************/
            //--------NRUTA-------------
            /***************************/
            case "LISTERUTA": //metodo para listar RUTA
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
            

            //Cristhian Vargas Quiroz
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
                String mensaje5 = "<div class='error'><strong>ERROR!!! </strong><p class='texto-error'>en la instruccion porfavor revisa  al enviado AYUDA[]; de la aplicacion</p></div>";
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(mensaje5));
                break;
        }
    }

    public String getMensajeAyuda() {
        String estilo = "<link>";
        String titulo = "<div>\n"
                + " <h2>Comandos de la aplicacion EXPRINTER</h2>\n"
                + "</div>";
        //cristhian vargas quiroz
        String ayudaEmpleados = "<div>\n" + "<div>\n"
                + "<h3>COMANDOS DE EMPLEADOS</h3>\n" + "</div>\n"
                
                + "<strong>LISTAR EMPLEADOS :</strong>\n" + "<p>listar todo los EMPLEADOS = 0</p>\n"
                + "<p>listar LOS EMPLEADOS</p>\n" + "<p>ListEmpleados[0]</p>\n"
                + "<p>ejemplo: ListEmpleados[0]</p>\n"
                
                + "<strong>Registrar un empleado<strong>\n"
                + "<p>REGEMPLEADOS[nombre,email,password,ci,telefono,direccion,genero,nacimiento,rol=1 o 2]</p>\n"
                + "<strong>Ejemplo de registrar un empleado</strong>\n"
                + "REGEMPLEADOS[Cristhian Vargas,cristhian@gmail.com,12345678,9636927,78588196,calle olimpo,M,1997-02-21,2]\n"
                
                + "<strong>Editar un empleado<strong>\n"
                + "<p>EDITEMPLEADOS[id,nombre,email,ci,telefono,direccion,genero,nacimiento]</p>\n"
                + "<strong>Ejemplo de editar un empleado</strong>\n"
                + "<p>EDITEMPLEADOS[1,Cristhian Quiroz,criss@gmail.com,9685742,78589632,calle oliver,M,1997-02-23]</p>\n"
                
                + "<strong>Eliminar un empleado<strong>\n"
                + "<p>DELEMPLEADOS[id]</p>\n"
                + "<strong>Ejemplo de eliminar un empleado</strong>\n"
                + "<p>DELEMPLEADOS[9]</p>\n"
                
                + "</div>\n"
                + "</div>";
        
                String ayudaClientes = "<div>\n" + "<div>\n"
                + "<h3>COMANDOS DE CLIENTES</h3>\n" + "</div>\n"
                
                + "<strong>LISTAR CLIENTES :</strong>\n" + "<p>listar todo los CLIENTES = 0</p>\n"
                + "<p>listar LOS CLIENTES</p>\n" + "<p>LISTCLIENTES[0]</p>\n"
                + "<p>ejemplo: LISTCLIENTES[0]</p>\n"
                
                + "<strong>Registrar un cliente<strong>\n"
                + "<p>REGCLIENTE[nombre,iniciales,cel,email,emailconf,observaciones,ejecutivo=1...,compañia=1....]</p>\n"
                + "<strong>Ejemplo de registrar un empleado</strong>\n"
                + "REGCLIENTE[Cristhian Vargas,CVQ,78541263,cristhian78@gmail.com,cristhian78@gmail.com,ninguna,4,2]\n"
                
                + "<strong>Editar un cliente<strong>\n"
                + "<p>EDITCLIENTE[id,nombre,iniciales,cel,email,emailconf,observaciones]</p>\n"
                + "<strong>Ejemplo de editar un cliente</strong>\n"
                + "<p>EDITCLIENTE[5,Cristhian vasques,CVQ,78588196,cris7845@gmail.com,cris7845@gmail.com,ninguna]</p>\n"
                
                + "<strong>Eliminar un cliente<strong>\n"
                + "<p>DELCLIENTE[id]</p>\n"
                + "<strong>Ejemplo de eliminar un cliente</strong>\n"
                + "<p>DELCLIENTE[6]</p>\n"
                
                + "</div>\n"
                + "</div>";
                
                String ayudaEmpresa = "<div>\n" + "<div>\n"
                + "<h3>COMANDOS DE EMPRESAS</h3>\n" + "</div>\n"
                
                + "<strong>LISTAR EMPRESAS :</strong>\n" + "<p>listar todo las EMPRESAS = 0</p>\n"
                + "<p>LISTAR LAS EMPRESAS</p>\n" + "<p>LISTEMPRESAS[0]</p>\n"
                + "<p>ejemplo: LISTEMPRESAS[0]</p>\n"
                
                + "<strong>Registrar una empresa<strong>\n"
                + "<p>REGEMPRESA[nombre,NIT,razonSocial,sitioWeb,direccion,telefono,fax,ubicacion,sector,tipo]</p>\n"
                + "<strong>Ejemplo de registrar un empleado</strong>\n"
                + "REGCLIENTE[Fiambres LOLA,784512458,SA,google.com,barrio olimpo,3569856,78588196,norte,fiambres,privado]\n"
                
                + "<strong>Editar una empresa<strong>\n"
                + "<p>EDITEMPRESA[id,nombre,NIT,razonSocial,sitioWeb,direccion,telefono,fax,ubicacion,sector,tipo]</p>\n"
                + "<strong>Ejemplo de editar una empresa</strong>\n"
                + "<p>EDITEMPRESA[6,EMPRESA lola,784512548,SRL,google.com,barrio olimpo,3556985,3556985,zona sur,fiambres,privado]</p>\n"
                
                + "<strong>Eliminar una empresa<strong>\n"
                + "<p>DELEMPRESA[id]</p>\n"
                + "<strong>Ejemplo de eliminar una empresa</strong>\n"
                + "<p>DELEMPRESA[7]</p>\n"
                //cristhian vargas quiroz
                
                + "</div>\n"
                + "</div>";
        return "Content-Type:text/html;\r\n<html>" + estilo + titulo + ayudaEmpleados + ayudaClientes + ayudaEmpresa + "</html>";
    }

    public String getMensajeTabla(String res) {
        String estilo = "<link rel='stylesheet' href='https://codepen.io/ingyas/pen/NENBOm.css'>";
        return "Content-Type:text/html;\r\n<html>" + estilo + res + "</html>";

    }

    public String getMensajeRespuesta(String res) {

        String estilo = "<link rel='stylesheet' href='https://codepen.io/ingyas/pen/NENBOm.css'>";
        return "Content-Type:text/html;\r\n<html>" + estilo + res + "</html>";
    }
}
