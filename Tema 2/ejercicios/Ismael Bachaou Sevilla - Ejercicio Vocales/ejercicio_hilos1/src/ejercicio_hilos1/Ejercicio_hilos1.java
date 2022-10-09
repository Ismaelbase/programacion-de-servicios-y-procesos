
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
            int total_a = 0;

            for(int i=0;i<partido.length;i++){
                
                if(partido[i].equalsIgnoreCase("a")){
                    int aux = total_a;
                    aux++;
                    total_a = aux;

                    System.out.println(" - Vocal a -> "+total_a);
                }
            }
            total += total_a;
            System.out.println("");
            System.out.println("El total actual es : "+total);
            System.out.println("======================");
            sem.release();
        }
        
    }
    
    static class Contar_e extends Thread{
        String txt = "";
        
        
        Contar_e(String texto, Semaphore s){
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
            
            int total_e = 0;
            
            for(int i=0;i<partido.length;i++){
                
                if(partido[i].equalsIgnoreCase("e")){
                    int aux = total_e;
                    aux++;
                    total_e = aux;

                    System.out.println(" - Vocal e -> "+total_e);
                }
            }
            total += total_e;
            System.out.println("");
            System.out.println("El total actual es: "+total);
            System.out.println("======================");
            sem.release();
        }
        
    }
    
    static class Contar_i extends Thread{
        String txt = "";
        
        
        Contar_i(String texto, Semaphore s){
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
            
            int total_i = 0;

            for(int i=0;i<partido.length;i++){
                
                if(partido[i].equalsIgnoreCase("i")){
                    int aux = total_i;
                    aux++;
                    total_i = aux;
                    
                    System.out.println(" - Vocal i -> "+total_i);
                }
            }
            total += total_i;
            System.out.println("");
            System.out.println("El total actual es: "+total);
            System.out.println("======================");
            sem.release();
        }
        
    }
    
    static class Contar_o extends Thread{
        String txt = "";
        
        
        Contar_o(String texto, Semaphore s){
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
            
            int total_o = 0;
            for(int i=0;i<partido.length;i++){
                
                if(partido[i].equalsIgnoreCase("o")){
                    int aux = total_o;
                    aux++;
                    total_o = aux;
                    
                    System.out.println(" - Vocal o -> "+total_o);
                }
            }
            
            total+=total_o;
            System.out.println("");
            System.out.println("El total actual es: "+total);
            System.out.println("======================");
            sem.release();
        }
        
    }
    
    static class Contar_u extends Thread{
        String txt = "";
        
        
        Contar_u(String texto, Semaphore s){
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
            
            int total_u = 0;
            
            for(int i=0;i<partido.length;i++){
                
                if(partido[i].equalsIgnoreCase("u")){
                    int aux = total_u;
                    aux++;
                    total_u = aux;

                    System.out.println(" - Vocal u -> "+total_u);
                }
            }
            total+= total_u;
            System.out.println("");
            System.out.println("El total actual es: "+total);
            System.out.println("======================");
            sem.release();
        }
        
    }
    
    public static void main(String[] args) {
        
        Semaphore s = new Semaphore(1);
        
        String text = "Cuantas vocales tendra esta frase? Ni idea, vamos a averiguarlo";
        
        Contar_a c_a = new Contar_a(text,s);
        Contar_e c_e = new Contar_e(text,s);
        Contar_i c_i = new Contar_i(text,s);
        Contar_o c_o = new Contar_o(text,s);
        Contar_u c_u = new Contar_u(text,s);
        
        
        c_a.start();
        c_e.start();
        c_i.start();
        c_o.start();
        c_u.start();
        
        System.out.println("Total vocales: "+total);
        
    }
    
}
