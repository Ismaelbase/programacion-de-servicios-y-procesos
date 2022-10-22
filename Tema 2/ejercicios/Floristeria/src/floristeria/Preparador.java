package floristeria;

import java.util.concurrent.Semaphore;

public class Preparador extends Thread{
    String nombre;
    Semaphore sem;
    
    public Preparador(String nom,Semaphore s){
        this.nombre = nom;
        this.sem = s;
    }
    
    @Override
    public void run(){
        
    }
}
