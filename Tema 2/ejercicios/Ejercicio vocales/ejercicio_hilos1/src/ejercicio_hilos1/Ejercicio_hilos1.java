
package ejercicio_hilos1;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ejercicio_hilos1 {
    static int total = 0;
    static Semaphore sem;
    
    static class Contar_a extends Thread{
        String txt = "";
        
        
        Contar_a(String texto, Semaphore s){
            txt = texto;
            sem = s;
        }
        
        @Override
        public void run(){
            try {
                sem.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Ejercicio_hilos1.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String[] partido = this.txt.split("");
            
            for(int i=0;i<partido.length;i++){
                
                if(partido[i].equalsIgnoreCase("a")){
                    int aux = total;
                    aux++;
                    total = aux;
                    
                    System.out.println(i+" - Vocal a -> "+total);
                }
            }
            sem.release();
        }
        
        
    }
    
    public static void main(String[] args) {
        
        Semaphore s = new Semaphore(1);
        
        Contar_a c_a = new Contar_a("Hola que pasa",s);
        
        c_a.start();
        
        System.out.println("Total vocales: "+total);
        
    }
    
}
