/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import Negocio.NEmpleados;

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
            metodo = "COMANDOS";
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
        //m_SMTPMessage2.sendMessage("grupo05sc@virtual.fcet.uagrm.edu.bo", prt_mailFrom, prt_asunto, prt_mensaje);
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
            case "LISTEMPLEADOS": //metodo para listar clientes
                System.out.println(prt_asunto + "...\r\n");
                NEmpleados m_Nempleados = new NEmpleados();
                String s_resEmpleados = m_Nempleados.listEmpleqados(prt_parametros);
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(s_resEmpleados));
                break;
            case "COMANDOS": //metodo para comandos
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
                String mensaje5 = "<div class='error'><strong>ERROR!!! </strong><p class='texto-error'>en la instruccion porfavor revisa  al enviado COMANDOS[]; de la aplicacion</p></div>";
                enviarMensajeCorreoOrigen(prt_mailFrom, prt_asunto, getMensajeRespuesta(mensaje5));
                break;
        }
    }

    public String getMensajeAyuda() {
        String estilo = "<link rel='stylesheet' href='https://codepen.io/ingyas/pen/NENBOm.css'>";
        String titulo = "<div>\n"
                + " <h2>Comandos de la aplicacion \"EXPRINTER\"</h2>\n"
                + "</div>";
        String ayudaEmpleados = "<div class=\"box\">\n" + "<div class=\"box-title\">\n"
                + "<h3>COMANDOS DE EMPLEADOS</h3>\n" + "</div>\n"
                + "<strong>LISTAR EMPLEADOS :</strong>\n" + "<p>listar todo los EMPLEADOS = 0</p>\n"
                + "<p>listar LOS EMPLEADOS</p>\n" + "<p>ListEmpleados[0]</p>\n"
                + "<p>ejemplo: ListEmpleados[0]</p>\n"
                + "</div>\n"
                + "</div>";
        return "Content-Type:text/html;\r\n<html>" + estilo + titulo + ayudaEmpleados + "</html>";
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
