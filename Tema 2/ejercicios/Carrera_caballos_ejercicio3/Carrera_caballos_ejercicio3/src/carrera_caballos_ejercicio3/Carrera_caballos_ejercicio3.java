package carrera_caballos_ejercicio3;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Carrera_caballos_ejercicio3 {

    public static int meta = 0;
    public static boolean carrera = true;
    public static Random r = new Random();
    public static Semaphore sem = new Semaphore(1);

    public static class Caballo extends Thread {

        int id = 0;
        int posicion = 0;

        Caballo() {
            this.id++;
        }

        @Override
        public void run() {

            while (posicion < meta && carrera) {

                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Carrera_caballos_ejercicio3.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    sem.acquire();
                } catch (InterruptedException ex) {

                }

                int tirada = r.nextInt(6) + 1;
                this.posicion += tirada;
                System.out.println("Soy el caballo " + this.id + " y he sacado un " + tirada + ", ahora voy por la posicion " + this.posicion);
                sem.release();
            }

            if (carrera) {
                try {
                    sem.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Carrera_caballos_ejercicio3.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                carrera = false;
                System.out.println(this.id+" ha ganado esta carrera ! ! ");
                
                sem.release();
            }

        }

    }

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        
        System.out.println("Cuantos caballos quieres que participen?: ");
        
        meta = 20;
        
        int caballos = teclado.nextInt();
        
        
        // Para crear varias instancia de un objeto en un for : 

        Semaphore s1 = new Semaphore(1);
        Caballo [] horses = new Caballo [caballos]; 
            
        for (int i = 0; i < caballos; i++) {
             horses[i] = new Caballo();
             
             horses[i].start();
        }
        
        

    }

}
