/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libs;

/**
 *
 * @author Hp
 */
public class Fshared {
    public boolean validarParaList(String[] prt_parametros){
        if(prt_parametros.length != 1){
            return false;
        }
        if(!esNumero(prt_parametros[0])){
            return false;
        }
        return true;
    }
    
    public boolean esNumero(String prt_parametros){
        try {
            Integer.parseInt(prt_parametros);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public String errorMessage(String parametro){
        return "<div><strong>ERROR</strong><p>"+parametro+"</p></div>";
    }
    
        public String successMessage(String parametro){
        return "<div><strong>EXITO</strong><p>"+parametro+"</p></div>";
    }
}
