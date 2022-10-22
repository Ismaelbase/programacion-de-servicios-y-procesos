package carrera_caballos1;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Carrera_Caballos1 {

    public static int meta = 0;
    public static Random r = new Random();
    public static boolean carrera = true;

    public static class Caballo extends Thread {

        String nombre = "";
        Semaphore sem;
        int posicion = 0;

        Caballo(String nom, Semaphore s) {
            this.nombre = nom;
            this.sem = s;
        }

        @Override
        public void run() {

            while (this.posicion < meta && carrera) {
                try {
                    sleep(1500);
                } catch (InterruptedException ex) {
                    
                }

                try {
                    this.sem.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Carrera_Caballos1.class.getName()).log(Level.SEVERE, null, ex);
                }
                int tirada = r.nextInt(6) + 1;

                this.sem.release();

                this.posicion += tirada;

                System.out.println("Soy el caballo " + this.nombre + " he sacado un " + tirada + " voy por la casilla " + this.posicion);
            }

            if (carrera) {
                try {
                    this.sem.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Carrera_Caballos1.class.getName()).log(Level.SEVERE, null, ex);
                }
                carrera = false;
                System.out.println(this.nombre + "!!! ha ganado la carrera.");
                this.sem.release();
            }

        }

    }


    public static void main(String[] args) {
        Random r = new Random();

        meta = 20;

        Semaphore s1 = new Semaphore(1);

        Caballo c1 = new Caballo("Galante", s1);
        Caballo c2 = new Caballo("Sangre Segura", s1);
        Caballo c3 = new Caballo("Sardinilla", s1);

        
        c1.start();
        c2.start();
        c3.start();

    }

}
