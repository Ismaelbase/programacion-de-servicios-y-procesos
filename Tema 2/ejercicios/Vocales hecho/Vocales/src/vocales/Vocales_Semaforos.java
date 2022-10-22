
package vocales;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vocales_Semaforos {
    static int cuenta_total = 0;
    static Semaphore sem;
    static String txt = "";
    
    static class Contar_a extends Thread{
        
        
        Contar_a(Semaphore s){
            sem = s;
        }
        
        @Override
        public void run(){
            
            
            String[] partido = txt.split("");
            
            for(int i = 0; i<partido.length;i++){
                if(partido[i].equalsIgnoreCase("a")){
                    try {
                        sem.acquire();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Vocales_Semaforos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cuenta_total++;
                    System.out.println("Cuenta A: "+cuenta_total);
                    sem.release();
                }
            }
            
        }
        
    }
    
    static class Contar_e extends Thread{
        
        
        Contar_e(Semaphore s){
            sem = s;
        }
        
        @Override
        public void run(){
            
            
            String[] partido = txt.split("");
            
            for(int i = 0; i<partido.length;i++){
                if(partido[i].equalsIgnoreCase("e")){
                    try {
                        sem.acquire();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Vocales_Semaforos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cuenta_total++;
                    System.out.println("Cuenta E: "+cuenta_total);
                    sem.release();
                }
            }
            
        }
        
    }
    
    static class Contar_i extends Thread{
        
        
        Contar_i(Semaphore s){
            sem = s;
        }
        
        @Override
        public void run(){
            
            
            String[] partido = txt.split("");
            
            for(int i = 0; i<partido.length;i++){
                if(partido[i].equalsIgnoreCase("i")){
                    try {
                        sem.acquire();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Vocales_Semaforos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cuenta_total++;
                    System.out.println("Cuenta I: "+cuenta_total);
                    sem.release();
                }
            }
            
        }
        
    }
    
    static class Contar_o extends Thread{
        
        
        Contar_o(Semaphore s){
            sem = s;
        }
        
        @Override
        public void run(){
            
            
            String[] partido = txt.split("");
            
            for(int i = 0; i<partido.length;i++){
                if(partido[i].equalsIgnoreCase("o")){
                    try {
                        sem.acquire();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Vocales_Semaforos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cuenta_total++;
                    System.out.println("Cuenta O: "+cuenta_total);
                    sem.release();
                }
            }
            
        }
        
    }
    
    static class Contar_u extends Thread{
        
        
        Contar_u(Semaphore s){
            sem = s;
        }
        
        @Override
        public void run(){
            
            
            String[] partido = txt.split("");
            
            for(int i = 0; i<partido.length;i++){
                if(partido[i].equalsIgnoreCase("u")){
                    try {
                        sem.acquire();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Vocales_Semaforos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    cuenta_total++;
                    System.out.println("Cuenta U: "+cuenta_total);
                    sem.release();
                }
            }
            
        }
        
    }
    
    public static void main(String[] args) {
        Semaphore s = new Semaphore(1);
        
        Contar_a ca = new Contar_a(s);
        Contar_e ce = new Contar_e(s);
        Contar_i ci = new Contar_i(s);
        Contar_o co = new Contar_o(s);
        Contar_u cu = new Contar_u(s);
        
        txt = "Vamos a comprobar las vocales que tiene este texto.";
        
        ca.start();
        ce.start();
        ci.start();
        co.start();
        cu.start();
        
        System.out.println("La cuenta total es: "+cuenta_total);
    }
    
}
