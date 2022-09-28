package fibonacci;

class FiboThread extends Thread{
    int cantidad;
    Thread hilo;
    
    FiboThread(int n){
        hilo = new Thread(this, "FiboThread");
        cantidad = n;
        hilo.start();
    }
    
    @Override
    public void run(){
        int n1 = 1, n2 = 1 , suma = 0;
        
        System.out.println(" "+n1+"\n "+n2);
        
        for(int i = 0; i<this.cantidad; i++){
            suma = n1+n2;
            System.out.println(" "+suma);
            n1 = n2;
            n2 = suma;
        }
        
        
    }
    
}

public class FibonacciThread {
    public static void main(String[] args) {
        FiboThread f1 = new FiboThread(10);
    }
}
