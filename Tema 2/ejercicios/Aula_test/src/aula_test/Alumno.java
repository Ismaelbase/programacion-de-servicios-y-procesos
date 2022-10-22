package aula_test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Alumno extends Thread{
    
    Bienvenida saludo;
    
    public Alumno(Bienvenida b){
        saludo = b;
    }
    
    @Override
    public void run(){
        System.out.println("Llega a clase el alumno: "+Thread.currentThread().getId());
        try {
            sleep(1000);
            saludo.saludarProfesor();
        } catch (InterruptedException ex) {
            Logger.getLogger(Alumno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
