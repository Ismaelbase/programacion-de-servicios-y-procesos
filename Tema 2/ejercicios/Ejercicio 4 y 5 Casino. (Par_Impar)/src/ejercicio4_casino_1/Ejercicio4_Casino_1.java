package ejercicio4_casino_1;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ejercicio4_Casino_1 {

    static Random r = new Random();

    static int ronda = 0;

    static boolean banca_abierta = true;

    static Semaphore semaforo_ganadores = new Semaphore(1);
    static int jugadores_ganadores = 0;

    static Semaphore semaforo_perdedores = new Semaphore(1);
    static int jugadores_perdedores = 0;

    static boolean carta_sacada = false;
    static int numero_ganador;
    
    static double apuesta = 10;
    
    public static class Crupier extends Thread {

        double saldo = 50000;

        Crupier() {

        }

        @Override
        public void run() {

            while (this.saldo > 0 && banca_abierta) {
                try {
                    
                    ronda++;
                    
                    semaforo_ganadores.acquire();
                        jugadores_ganadores = 0;
                    semaforo_ganadores.release();

                    semaforo_perdedores.acquire();
                        jugadores_perdedores = 0;
                    semaforo_perdedores.release();
                    
                    System.out.println("############################################################################################");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("############################################################################################");
                    System.out.println("Crupier -> Vamos con la ronda " + ronda + "...");
                    
                    sleep(2000);
                    numero_ganador = r.nextInt(36) + 1;
                    System.out.println("Crupier -> El numero ganador es el...");
                    sleep(2000);

                    carta_sacada = true;

                    System.out.println("Crupier -> $$$$ [" + numero_ganador + "] $$$$");
                    sleep(2000);
                    System.out.println("Crupier -> Hare las cuentas de esta ronda.");
                    sleep(4000);

                    carta_sacada = false;

                    double perdidas = 0;
                    double ganancias = 0;
                    
                    if(jugadores_ganadores == 0 && jugadores_perdedores == 0){
                        banca_abierta = false;
                    }else{
                        perdidas = apuesta * 36 * jugadores_ganadores;
                        System.out.println("Crupier -> Un total de [" + jugadores_ganadores + "] han ganado esta ronda, por lo que he perdido " + perdidas + " euros.");

                        ganancias = apuesta * jugadores_perdedores;
                        System.out.println("Crupier -> Un total de ["+jugadores_perdedores + "] han perdido esta ronda, por lo que he ganado "+ganancias+" euros.");

                        this.saldo -= perdidas;
                        this.saldo += ganancias;

                        System.out.println("Crupier -> El saldo actual de la banca es de ["+this.saldo+"] euros.");
                    }
                    
                    
                    
                    
                } catch (InterruptedException ex) {
                    System.out.println("Error en el crupier.");
                }
            }
            
            if(saldo <= 0){
                System.out.println("Crupier -> La banca se queda sin saldo, enhorabuena jugadores.");
                banca_abierta = false;
            }else{
                System.out.println("Crupier -> No hay mas jugadores apostando, la ruleta se cierra.");
            }

        }

    }

    public static class Jugador extends Thread {

        static int contador = 0;
        int id = 0;
        int tipo_jugador = -1;
        
        boolean anterior_ganada = false;

        
        double saldo = 1000;
        
        Jugador() {
            this.contador++;
            this.id = this.contador;
        }
        
        // Si introduce un 1 el jugador juega a impares, si introduce un 2 a pares.
        
        Jugador(int par_impar) {
            this.contador++;
            this.id = this.contador;
            this.tipo_jugador = par_impar;
        }

        @Override
        public void run() {

            double ganancia;
            int contador_ronda = 1;

            while (this.saldo > 0 && banca_abierta) {

                if (carta_sacada && contador_ronda == ronda) {
                    contador_ronda++;
                    
                    int numero_apuesta = r.nextInt(36) + 1;
                    
                    // Si par_impar vale -1 significa que juega normal
                    if(tipo_jugador == -1){
                        if (numero_apuesta == numero_ganador) {
                            try {
                                semaforo_ganadores.acquire();
                                    jugadores_ganadores++;
                                semaforo_ganadores.release();

                                ganancia = apuesta * 36;

                                this.saldo += ganancia;

                                System.out.println("Jugador " + this.id + " -> Aposte por el [" + numero_apuesta + "], gane " + ganancia + " euros, ahora tengo " + this.saldo + " euros.");

                            } catch (InterruptedException ex) {
                                System.out.println("Error en el semaforo de los ganadores.");
                            }

                        } else {
                            try {
                                semaforo_perdedores.acquire();
                                    jugadores_perdedores++;
                                semaforo_perdedores.release();

                                this.saldo -= apuesta;
                                System.out.println("Jugador " + this.id + " -> Aposte por el [" + numero_apuesta + "], perdi " + apuesta + " euros que aposte, ahora tengo " + this.saldo + " euros.");
                            } catch (InterruptedException ex) {

                            }
                        }
                        // Cuando par_impar es 1 juega a impares.
                    }else if(tipo_jugador == 1){
                        if(numero_ganador % 2 != 0){
                            try {
                                semaforo_ganadores.acquire();
                                    jugadores_ganadores++;
                                semaforo_ganadores.release();
                                
                                ganancia = apuesta * 2;
                                
                                this.saldo += ganancia;
                                
                                System.out.println("Jugador " + this.id + " -> Aposte por [IMPARES], gane " + ganancia + " euros, ahora tengo " + this.saldo + " euros.");
                                
                            } catch (InterruptedException ex) {
                                System.out.println("Fallo en jugador impar.");
                            }
                        }else{
                            try {
                                semaforo_perdedores.acquire();
                                    jugadores_perdedores++;
                                semaforo_perdedores.release();
                                
                                this.saldo -= apuesta;
                                
                                System.out.println("Jugador " + this.id + " -> Aposte por [IMPARES], perdi " + apuesta + " euros que aposte, ahora tengo " + this.saldo + " euros.");
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Ejercicio4_Casino_1.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                
                        }
                    }else if (tipo_jugador == 2){
                        if(numero_ganador % 2 == 0){
                            try {
                                semaforo_ganadores.acquire();
                                    jugadores_ganadores++;
                                semaforo_ganadores.release();
                                
                                ganancia = apuesta * 2;
                                
                                this.saldo += ganancia;
                                
                                System.out.println("Jugador " + this.id + " -> Aposte por [PARES], gane " + ganancia + " euros, ahora tengo " + this.saldo + " euros.");
                                
                            } catch (InterruptedException ex) {
                                System.out.println("Fallo en los pares.");
                            }
                        }else{
                            try {
                                semaforo_perdedores.acquire();
                                    jugadores_perdedores++;
                                semaforo_perdedores.release();
                                
                                this.saldo -= apuesta;
                                
                                System.out.println("Jugador " + this.id + " -> Aposte por [PARES], perdi " + apuesta + " euros que aposte, ahora tengo " + this.saldo + " euros.");
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Ejercicio4_Casino_1.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }else if (tipo_jugador == 3){
                        
                    }
                }else{
                    try {
                        sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Ejercicio4_Casino_1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            
            if(this.saldo <= 0){
                System.out.println("Jugador "+this.id+" me he quedado con un saldo de "+this.saldo+", no puedo seguir jugando...");
            }

        }

    }

    public static void main(String[] args) {
        Crupier c1 = new Crupier();
        // Jugador vacio -> Jugador normal.
        // Jugador (1) -> Jugador a impares.
        // Jugador (2) -> Jugador a pares.
        
        Jugador j1 = new Jugador();
        Jugador j2 = new Jugador();
        Jugador j3 = new Jugador();
        Jugador j4 = new Jugador();
        Jugador j5 = new Jugador(1);
        Jugador j6 = new Jugador(2);

        c1.start();

        j1.start();
        j2.start();
        j3.start();
        j4.start();
        j5.start();
        j6.start();
    }

}
