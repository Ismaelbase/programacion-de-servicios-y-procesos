
package floristeria_3;

import java.util.concurrent.Semaphore;

public class Floristeria {
    
    private String[] flores = {"Lirio","Clavel","Rosa"};
    int[] mostrador = {0,0,0};
    Semaphore[] sem = new Semaphore[] {new Semaphore(1),new Semaphore(1), new Semaphore(1)};
    
    
    public class Preparador extends Thread{
        String nombre;
        Semaphore sem;
        int identificador;
        
        Preparador(String nom,Semaphore s, int id){
            this.nombre = nom;
            this.sem = s;
            this.identificador = id;
        }
        
        @Override
        public void run(){
            
        }
        
        
    }
    
    public class Montador extends Thread{
        
    }
    
    public static void main(String[] args) {
        
    }
    
}
