/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

/**
 *
 * @author Hp
 */
public class Grupo05saMail {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args){
        Manejador mail = new Manejador();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    //leer
                    mail.leer();
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        System.out.println("Error al iniciar el ciclo escuchar");
                    }
                }
            }
        }).start();
    }
}
