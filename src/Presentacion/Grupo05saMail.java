/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentacion;

/**
 *
 * @author Oni
 */
public class Grupo05saMail {

    public static void main(String[] args) {
        // TODO code application logic here
        Manejador mail = new Manejador();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ///leer
                    mail.leer();
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        System.out.println("error al iniciar el ciclo de escucha");
                    }
                }
            }
        }).start();

    }
}
