package ejercicios1.pkg2;

class Ejercicio2Runnable implements Runnable{
    int numero;
    Thread hilo;
    
    
    Ejercicio2Runnable(int n, String nombre){
        this.numero = n;
        this.hilo =new Thread(this, nombre);
        hilo.start();
    }

    @Override
    public void run(){
        int suma=0;
        for(int i=this.numero;i > 0; i--){
            suma+=i;
            System.out.println("Hilo: "+this.hilo.getName()+" Suma: "+suma);
            
        }
    }
}



public class Ejercicio2 {
    public static void main(String[] args) {
        Ejercicio2Runnable e1 = new Ejercicio2Runnable(10, "Hilo 1");
        Ejercicio2Runnable e2 = new Ejercicio2Runnable(10, "Hilo 2");
        Ejercicio2Runnable e3 = new Ejercicio2Runnable(10, "Hilo 3");
    }
}
