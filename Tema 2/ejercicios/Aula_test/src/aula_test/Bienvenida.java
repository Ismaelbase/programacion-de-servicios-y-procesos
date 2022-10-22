
package aula_test;

public class Bienvenida {
    
    boolean clase_empezada;
    
    Bienvenida(){
        clase_empezada = false;
    }
    
    public synchronized void saludarProfesor() throws InterruptedException{

        while(clase_empezada == false){
            wait();
        }
        System.out.println("Buenos dias profesor.");

    }
    
    public synchronized void llegadaProfesor(String nombre_prof){
        // El profe llega, saluda y avisa de que ya está en clase.
        
        System.out.println("Hola buenos dias a todos, soy el profesor "+nombre_prof+".");
        clase_empezada = true;
        notifyAll();
    }
    
}
