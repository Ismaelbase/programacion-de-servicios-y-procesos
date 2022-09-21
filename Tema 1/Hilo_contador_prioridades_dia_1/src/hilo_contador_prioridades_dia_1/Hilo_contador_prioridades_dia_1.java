

package hilo_contador_prioridades_dia_1;

public class Hilo_contador_prioridades_dia_1 {

    public static void main(String[] args) {
            HiloContador h1 = new HiloContador("Hilo_1");
            HiloContador h2 = new HiloContador("Hilo_2");
            HiloContador h3 = new HiloContador("Hilo_3");
            
            h3.setPriority(1);
            h1.setPriority(10);
            h2.setPriority(5);
            
            
            h1.start();
            h2.start();
            h3.start();
            
            try{
                h1.join();
                h2.join();
                h3.join();
            }catch(InterruptedException ex){
                System.out.println(" Hilo principal interrumpido.");
            }
          }
        }


class HiloContador extends Thread {

            String nombre;

            public HiloContador(String n) {
                super();
                nombre = n;
            }

            @Override
            public void run() {
                int cont = 0;

                while (cont < 1000) {
                    System.out.println("    " + nombre + ": " + cont + " Prioridad:  "
                            + super.getPriority());
                    cont++;
                }
            }
            
          
    }

