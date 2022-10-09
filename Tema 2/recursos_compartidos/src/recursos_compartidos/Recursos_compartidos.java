package recursos_compartidos;
import java.util.concurrent.Semaphore; // Para importar semaforos ! 
import java.util.logging.Level;
import java.util.logging.Logger;

public class Recursos_compartidos {
    static int v_compartido = 0;
    static Semaphore sem;
    
    static class sumador extends Thread{
        int aumento;
        
        sumador(int n,Semaphore s){
            aumento = n;
            sem = s;
        }
        
        @Override
        public void run(){
            try {
                sem.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Recursos_compartidos.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = 0; i< aumento; i++){
                int aux = v_compartido;
                aux += 1;
                v_compartido = aux;
                
                System.out.println(i+" - Sumador: "+v_compartido );
            }
            sem.release();
        }
    }
    
    static class restador extends Thread{
        int aumento = 0;
        
        restador(int n,Semaphore s){
            aumento = n;
            sem = s;
        }
        
        
        @Override
        public void run(){
            try {
                sem.acquire();
            } catch (InterruptedException ex) {
                Logger.getLogger(Recursos_compartidos.class.getName()).log(Level.SEVERE, null, ex);
            }
            for(int i = 0; i< aumento; i++){
                int aux = v_compartido;
                aux -= 1;
                v_compartido = aux;
                
                System.out.println(i+" - Restador: "+v_compartido );
            }
            sem.release();
        }
        
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        
        Semaphore s = new Semaphore(1); // Creamos un semaforo
        
        sumador c1 = new sumador(4,s); // Y lo metemos al constructor del hilo.
        restador c2 = new restador(2,s);
        
        
        
        c1.start();
        c2.start();
        
        System.out.println("Valor: " + v_compartido);
        
    }
    
}
