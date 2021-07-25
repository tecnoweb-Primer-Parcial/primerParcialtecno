/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

import java.util.List;

/**
 *
 * @author Oni
 */
class Manejador {
    private int max = 0;
    private PopMessage m_PopMessage2;
    private SMTPMessage m_SMTPMessage2;
    
    private String g_metodo="";
    private String g_origen="";
    
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
            boolean estado=analizarLineasSi(m_PopMessage2.getMessageArray(max));
            if (estado) {
                enviar_respuesta_(true);
            }else{
                enviar_respuesta_(false);
            }
        }
        m_PopMessage2.cerrar();
    }
    
    public void analizarLineas(List<String> messageArray) {
        String lineaMetodo = "";
        String lineaUsuario = "";
        int i=0;
        for (String line : messageArray) {
            //System.out.println(line.toString());
            if (line.contains("Return-Path:")) {
              lineaUsuario = line;
            }
            if (line.contains("Subject:")||line.contains("subject:")) {
                if (line.regionMatches(0, "Subject:", 0,8 )||line.regionMatches(0, "subject:", 0,8 )) {
                    while(!messageArray.get(i).contains("]")){
                        lineaMetodo=lineaMetodo+messageArray.get(i);
                        i++;
                    }
                    lineaMetodo=lineaMetodo+messageArray.get(i);
                }                
            }
            i++;
            }
        System.out.println("linea encontrada=>"+lineaMetodo);
            //i++;
        
        //obtener mail usuario
        String mailFrom = getCorreoEmisor(lineaUsuario);
        System.out.println(mailFrom);
        
            //obtener metodo
            //posisiones para metodo y parametros
            int posIni = lineaMetodo.indexOf("[");
            int posFin = lineaMetodo.indexOf("]");
            String metodo = getMetodo(lineaMetodo, posIni);
            System.out.println("metodo-"+metodo);
            //obtener parametros        
            String[] parametros = getParametros(lineaMetodo, posIni, posFin);
            System.out.println(parametros.toString());
            ejecutarMetodos(metodo, parametros, mailFrom);
        

    }
}
