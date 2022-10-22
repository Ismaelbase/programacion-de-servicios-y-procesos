package vocales;
import java.util.concurrent.Semaphore;

public class Vocales_Sincro {
    
    private static int cuenta_total = 0;
    private static String txt = "";
    
    static synchronized void  Contar_vocales(String v){
        String[] partido = txt.split("");
            for(int i = 0 ; i < partido.length;i++){
                if(partido[i].equalsIgnoreCase(v)){
                    cuenta_total++;
                    System.out.println("Cuenta "+v.toUpperCase()+": "+cuenta_total);
                }
            }
        }
    
    public static void main(String[] args) {
        
        txt = "Vamos a comprobar las vocales que tiene este texto.";
        
        Contar_vocales("a");
        Contar_vocales("e");
        Contar_vocales("i");
        Contar_vocales("o");
        Contar_vocales("u");
        
        System.out.println("La cuenta total es: "+cuenta_total);
    }
}
