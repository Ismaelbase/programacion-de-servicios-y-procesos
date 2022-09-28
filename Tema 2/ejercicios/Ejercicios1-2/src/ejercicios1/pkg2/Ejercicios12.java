
package ejercicios1.pkg2;


class Ejercicio1 extends Thread{
    int numero;
    char letra;
    Thread hilo;
    
    Ejercicio1(int n,char l){
        hilo = new Thread(this,"Ej1");
        this.numero = n;
        this.letra = l;
        hilo.start();
    }
    
    @Override
    public void run(){
        for(int i=0;i<this.numero;i++){
            System.out.print(this.letra);
        }
    }
}


public class Ejercicios12 {

    
    public static void main(String[] args) {
        
        Ejercicio1 e1 = new Ejercicio1(4,'a');
        Ejercicio1 e2 = new Ejercicio1(2,'b');
        Ejercicio1 e3 = new Ejercicio1(5,'c');
        
    }
    
}
