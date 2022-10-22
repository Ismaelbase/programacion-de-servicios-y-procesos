package floristeria_maria;


import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.concurrent.Semaphore;

public class Floristeria_maria {

    static Semaphore cant_flores = new Semaphore(1);
    static int[] cant = new int[3];
    static Semaphore[] controlador = {new Semaphore(1),new Semaphore(1),new Semaphore(1)};
    static int final_flores=0;
    static int final_ramos =0;
    final static int flores_por_ramo = 3;

    public static class Preparador extends Thread{
        int num;
        boolean terminado=false;
        String tipo_flor;

        public Preparador(int numero) {
            this.num=numero;

            switch(numero){
                case 0:
                    tipo_flor="Rosa";
                    break;

                case 1:
                    tipo_flor="Lirio";
                    break;

                case 2:
                    tipo_flor="Clavel";
                    break;
            }
        }

        public void fin_preparador(){
            terminado = true;
        }

        @Override
        public void run() {
            while(!terminado && final_ramos<2){
                try {
                    sleep(1000);
                    controlador[num].acquire();
                        cant[num]++;
                        System.out.println("Se está preparando la flor: "+tipo_flor);
                    controlador[num].release();

                    cant_flores.acquire();
                        final_flores++;
                        System.out.println("El total de flores es: "+final_flores);
                    cant_flores.release();
                } catch (InterruptedException ex) {
                    System.out.println("FALLO. LA EJECUCIÓN HA SIDO INTERRUMPIDA.");
                }
            }
        }

    }
    
    public static class Montador extends Thread{
        int cant2=0;
        public Montador(int m) {
            cant2=m;
        }

        @Override
        public void run() {
                while (final_ramos < cant2) {
                    for (int i = 0; i < flores_por_ramo; i++) {
                        while (cant[i]==0) {
                            try {
                               sleep(100);
                            } catch (InterruptedException ex) {
                                
                            }
                        }
                        try{
                            controlador[i].acquire();
                            cant[i]--;
                            controlador[i].release();
                        }catch (InterruptedException ex) {
                            
                        }
                    }
                    final_ramos++;
                    System.out.println("Número final de ramos: "+final_ramos);
                    try{
                        sleep(100);
                    }catch (InterruptedException ex) {
                        
                    }
                }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Preparador pre1 = new Preparador(0);
        Preparador pre2 = new Preparador(1);
        Preparador pre3 = new Preparador(2);

        Montador mont = new Montador(3);

        pre1.start();
        pre2.start();
        pre3.start();

        mont.start();
        mont.join();

        pre1.fin_preparador();
        pre2.fin_preparador();
        pre3.fin_preparador();
    }

}