
package programacion_procesos_dia_2;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;

public class Proceso_3 {
    public static void main(String[] args) throws Exception{
        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "ipconfterig");
        Process p = pb.start();
        
        InputStream is = p.getInputStream();
        InputStream err = p.getErrorStream();
        
        if(is.read() != -1){
            int r;
            while((r = is.read()) != -1){
                System.out.print((char) r);
            }
            is.close();
            System.exit(0);
        }else{
            
            PrintStream errf = new PrintStream(new File("ErrorFi.txt"));
            PrintStream console = System.out;
            System.setOut(errf);
            
            int er;
            while((er = err.read()) != -1){
                System.out.print((char) er);
            }
            err.close();
            System.setOut(console);
            System.exit(-1);
        }
        
    }
}
