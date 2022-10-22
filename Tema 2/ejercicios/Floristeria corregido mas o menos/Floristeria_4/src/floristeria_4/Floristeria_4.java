package floristeria_4;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Floristeria_4 {
    static String[] nombre_flor = {"Lirio","Clavel","Rosa"};
    static boolean[] mostrador = {false,false,false};
    static Semaphore[] array_semaforos = {new Semaphore(1),new Semaphore(1),new Semaphore(1)};
    static int total_ramos = 0;
    static boolean flores_listas = false;
    static boolean terminado = false;
    static int total_flores = 0;
    
    public static class Preparador extends Thread{
        // No es necesario pedir este semaforo por el main, se puede crear aquí:
        Semaphore sem_preparador;
        
        String nombre;
        int id;
        
        Preparador(String nom, int identificador, Semaphore s){
            this.nombre = nom;
            this.id = identificador;
            this.sem_preparador = s;
        }
        
        @Override
        public void run(){
            
            while(!terminado){
                try {
                    if(mostrador[id] == false){
                        System.out.println("Soy el trabajador "+this.nombre+" y voy a poner esta flor: "+nombre_flor[id]);
                        array_semaforos[id].acquire();
                        mostrador[id]=true;
                        array_semaforos[id].release();
                        
                        sem_preparador.acquire();
                        total_flores++;
                        System.out.println("He puesto un "+nombre_flor[id]+" con esta se han puesto un total de "+total_flores+" flores.");
                        sem_preparador.release();
                    }else{
                        sleep(2000);
                    }
                    
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Error en semaforo de preparador.");
                }
                
//                System.out.println(mostrador[0]+" "+mostrador[1]+" "+mostrador[2]);
                
            }
            
        }
    }
    
    public static class Montador extends Thread{
        Semaphore sem_montador;
        int cantidad_ramos;
        String nombre;
        
        Montador(String nom, int cant, Semaphore s){
            this.nombre = nom;
            this.sem_montador = s;
            this.cantidad_ramos = cant;
        }
        
        @Override
        public void run(){
            
            int i = 0;
            
            while(i<this.cantidad_ramos){
                
                // Se puede hacer que duerma mientras esto no sea verdadero !!! : 
                if(mostrador[0] && mostrador[1] && mostrador[2]){
                    
                    try {
                        
                        System.out.println("Soy el montador "+this.nombre+" voy a intentar montar un ramo.");
                        array_semaforos[0].acquire();
                        array_semaforos[1].acquire();
                        array_semaforos[2].acquire();
                        
                        mostrador[0] = false;
                        mostrador[1] = false;
                        mostrador[2] = false;
//                        System.out.println(mostrador[0]+" "+mostrador[1]+" "+mostrador[2]);
                        
                        this.sem_montador.acquire();
                        total_ramos++;
                        this.sem_montador.release();
                        
                        System.out.println("Ramo montado, ahora hay un total de "+total_ramos+" ramos montados.");
                        
                    } catch (InterruptedException ex) {
                        throw new RuntimeException("Error del montador en el array de semaforos.");
                    }
                    
                    
                    array_semaforos[0].release();
                    array_semaforos[1].release();
                    array_semaforos[2].release();
                    
                    i++;
                }else{
                    try {
                        sleep(2000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException("Error al dormir al montador.");
                    }
                }
                
            }
            terminado = true;
            
        }
        
        
    }
    
    
    public static void main(String[] args) {
        // String nom, int identificador, Semaphore s
        Semaphore semaforo_preparador = new Semaphore(1);
        // Este semaforo no es necesario ya que montador solo interactua el solo con la variable total_ramos: 
        Semaphore semaforo_montador = new Semaphore(1);
        
        Preparador p1 = new Preparador("Ana",0,semaforo_preparador);
        Preparador p2 = new Preparador("Julio",1,semaforo_preparador);
        Preparador p3 = new Preparador("Roberto",2,semaforo_preparador);
        
        // String nom, int cant, Semaphore s
        Montador m1 = new Montador("Carmen",5,semaforo_montador);
        
        p1.start();
        p2.start();
        p3.start();
        m1.start();
        
    }
    
}
