package aula_test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Profesor extends Thread{
    Bienvenida saludo;
    String nombre;
    public Profesor(Bienvenida b,String nom){
        saludo = b;
        nombre = nom;
    }
    
    @Override
    public void run(){
        System.out.println(nombre +" ha llegado.");
        try {
            sleep(3000);
            this.saludo.llegadaProfesor(this.nombre);
        } catch (InterruptedException ex) {
            Logger.getLogger(Profesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
