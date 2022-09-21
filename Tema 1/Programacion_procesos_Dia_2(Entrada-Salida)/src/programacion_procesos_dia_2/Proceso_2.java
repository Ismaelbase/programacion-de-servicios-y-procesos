
package programacion_procesos_dia_2;

import java.io.InputStream;

public class Proceso_2 {
        public static void main(String[] args) throws Exception{
        ProcessBuilder pb = new ProcessBuilder("CMD", "/C", "ipconfig","/all");
        Process p = pb.start();
        
        InputStream is = p.getInputStream();
        
        int res;
        
        while((res = is.read())!= -1){
            System.out.print((char) res);
        }
        is.close();
        System.exit(0);
    }
}
