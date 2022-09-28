package fibonacci;

class FiboRunnable implements Runnable{
    int cantidad;
    Thread hilo;
    
    FiboRunnable(int n){
        hilo = new Thread(this, "FIBORUNNABLE");
        cantidad = n;
        hilo.start();
    }
    
    @Override
    public void run(){
        int n1 = 1, n2 = 1, suma = 0;
        System.out.println(n1+" "+n2);
        for(int i = 0; i<this.cantidad; i++){
            suma = n1+ n2;
            System.out.println(" "+suma);
            
            n1 = n2;
            n2 = suma;
        }
    }
    
}


public class Fibonacci {
    public static void main(String[] args) {
        FiboRunnable f1 = new FiboRunnable(20);
        
    }
    
}
