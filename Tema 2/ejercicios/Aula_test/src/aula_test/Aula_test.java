package aula_test;

import java.util.Scanner;

public class Aula_test {
    
    public static void main(String[] args) {
        
        Bienvenida b1 = new Bienvenida();
        
        Scanner scan = new Scanner(System.in);
        
        int num_alumnos = scan.nextInt();
        
        for (int i = 0; i < num_alumnos; i++) {
            new Alumno(b1).start();
        }
        Profesor profe = new Profesor (b1,"Jose Jordan");
        profe.start();
        
    }
    
}
