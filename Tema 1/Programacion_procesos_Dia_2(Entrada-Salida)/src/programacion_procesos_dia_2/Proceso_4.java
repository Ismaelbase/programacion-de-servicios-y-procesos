
package programacion_procesos_dia_2;

import java.io.InputStream;
import java.io.OutputStream;

public class Proceso_4 {
    public static void main(String[] args) throws Exception{
        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "time");
        Process p = pb.start();
        
        InputStream is = p.getInputStream();
        OutputStream os = p.getOutputStream();
        
        int linea;
        int cont = 0;
        
        while(cont < 10){
            os.write('F');
            os.flush();
            cont++;
        }
        //Esto cierra el flujo de salida.
        os.close();
        
        
        while((linea = is.read()) != -1){
            System.out.print((char) linea);
        }
        
        is.close();
    }
}
