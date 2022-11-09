
package casino;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Casino {
    static int numero_ruleta;
    static Semaphore mutex_numero_ruleta = new Semaphore(1);
    static int saldo_banca=50;
    static Semaphore mutex_saldo_banca = new Semaphore(1);
    static int contador_apuesta=0;
    
    
    public static class jugador extends Thread
    {
        int saldo=1000;
        boolean parar=false;
        boolean apostado=false;
        int apuesta;
        char letra;
        
        jugador(char l)
        {
            letra=l;
        }
        
        @Override
        public void run()
        {
            while(!parar)
            {
                int numero = (int) (Math.random() * 36) + 1;
                apuesta = numero;   //Tenemos nuestra apuesta
                saldo-=10;          //Precio por apostar
                
                try {
                    sleep(3500);    //Realizamos nuestra apuesta
                    System.out.println("("+letra+") Mi apuesta: "+apuesta+ "(Numero de Apuesta " + contador_apuesta +")");
                } catch (InterruptedException ex) {
                    Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                try {
                    sleep(500);     //Esperamos a que el croupier saque la bola
                } catch (InterruptedException ex) {
                    Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(apuesta == numero_ruleta)    //Una vez saca la bola comprobamos 
                {
                    try {
                        mutex_saldo_banca.acquire();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    saldo_banca -= 360;
                    saldo+=360;
                    System.out.println("("+letra+")He ganado. Y mi saldo: "+saldo+" Saldo banca: "+saldo_banca);
                    mutex_saldo_banca.release();         
                    
                }
                else
                {
                    try {
                        mutex_saldo_banca.acquire();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    saldo_banca += 10;
                   
                    System.out.println("("+letra+")He perdido. Y mi saldo: "+saldo+" Saldo banca: "+saldo_banca);
                    mutex_saldo_banca.release();
                }
                
                //En el hipotetico caso de que la banca gaste los 50K, ya tiene que perder veces, pero lo debemos controlar
                if(saldo_banca<=0){
                    parar=true;
                    System.out.println("La banca se ha tenido que endeudar para pagar tu apuesta");
                }
                
                
            }
        }
    }
    public static class croupier extends Thread
    {
        
        @Override
        public void run()
        {
            while(saldo_banca>0)
            {   
                try {
                    sleep(3000);    //Esperamos a las apuestas de los jugadores
                } catch (InterruptedException ex) {
                    Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                }
                int numero = (int) (Math.random() * 37);
                try {
                    mutex_numero_ruleta.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Casino.class.getName()).log(Level.SEVERE, null, ex);
                }
                numero_ruleta=numero;
                System.out.println("Ha salido el "+numero_ruleta + "(Numero de Apuesta " + contador_apuesta +")");
                contador_apuesta++;
                mutex_numero_ruleta.release();
                
            }
            
        }
    }
   
    public static void main(String[] args) {
        croupier c = new croupier();
        jugador j = new jugador('A');
        jugador j2 = new jugador('B');
        
        c.start();
        j.start();
        j2.start();
        
        
    }
    
}
