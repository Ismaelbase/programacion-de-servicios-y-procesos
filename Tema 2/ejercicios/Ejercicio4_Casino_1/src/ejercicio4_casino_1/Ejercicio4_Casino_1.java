package ejercicio4_casino_1;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ejercicio4_Casino_1 {

    static Random r = new Random();

    static int ronda = 0;

    static boolean banca_abierta = true;

    static Semaphore semaforo_banca = new Semaphore(1);
    static double saldo_banca = 50000;

    static boolean carta_sacada = false;
    static int numero_ganador;

    static double apuesta = 10;
    static double apuesta_martingala = apuesta;

    public static class Crupier extends Thread {

        Crupier() {

        }

        @Override
        public void run() {

            while (saldo_banca > 0 && banca_abierta) {
                try {

                    ronda++;

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

                    System.out.println("Crupier -> Dispongo actualmente de " + saldo_banca + " euros");
//
//                    double perdidas = 0;
//                    double ganancias = 0;
//                    
//                    if(){
//                        banca_abierta = false;
//                    }else{
//                        perdidas = apuesta * 36 * jugadores_ganadores;
//                        System.out.println("Crupier -> Un total de [" + jugadores_ganadores + "] han ganado esta ronda, por lo que he perdido " + perdidas + " euros.");
//
//                        ganancias = apuesta * jugadores_perdedores;
//                        System.out.println("Crupier -> Un total de ["+jugadores_perdedores + "] han perdido esta ronda, por lo que he ganado "+ganancias+" euros.");
//
//                        this.saldo -= perdidas;
//                        this.saldo += ganancias;
//
//                        System.out.println("Crupier -> El saldo actual de la banca es de ["+this.saldo+"] euros.");
//                    }

                } catch (InterruptedException ex) {
                    System.out.println("Error en el crupier.");
                }
            }

            if (saldo_banca <= 0) {
                System.out.println("Crupier -> La banca se queda sin saldo, enhorabuena jugadores.");
                banca_abierta = false;
            } else {
                System.out.println("Crupier -> No hay mas jugadores apostando, la ruleta se cierra.");
            }

        }

    }

    public static class Jugador extends Thread {

        static int contador = 0;
        int id = 0;
        int tipo_jugador = -1;

        double martin_gala = apuesta;

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
                    if (tipo_jugador == -1) {
                        if (numero_apuesta == numero_ganador) {
                            try {

                                ganancia = apuesta * 36;

                                this.saldo += ganancia;

                                System.out.println("Jugador " + this.id + " -> Aposte por el [" + numero_apuesta + "], gane " + ganancia + " euros, ahora tengo " + this.saldo + " euros.");

                                semaforo_banca.acquire();
                                saldo_banca -= ganancia;
                                semaforo_banca.release();

                            } catch (InterruptedException ex) {
                                System.out.println("Error en el semaforo de los ganadores.");
                            }

                        } else {
                            try {
                                semaforo_banca.acquire();
                                saldo_banca += apuesta;
                                semaforo_banca.release();

                                this.saldo -= apuesta;
                                System.out.println("Jugador " + this.id + " -> Aposte por el [" + numero_apuesta + "], perdi " + apuesta + " euros que aposte, ahora tengo " + this.saldo + " euros.");
                            } catch (InterruptedException ex) {

                            }
                        }
                        // Cuando par_impar es 1 juega a impares.
                    } else if (tipo_jugador == 1) {
                        if (numero_ganador % 2 != 0) {
                            try {
                                ganancia = apuesta * 2;

                                this.saldo += ganancia;

                                semaforo_banca.acquire();
                                saldo_banca -= ganancia;
                                semaforo_banca.release();

                                System.out.println("Jugador " + this.id + " -> Aposte por [IMPARES], gane " + ganancia + " euros, ahora tengo " + this.saldo + " euros.");

                            } catch (InterruptedException ex) {
                                System.out.println("Fallo en jugador impar.");
                            }
                        } else {
                            try {

                                this.saldo -= apuesta;

                                semaforo_banca.acquire();
                                saldo_banca += apuesta;
                                semaforo_banca.release();

                                System.out.println("Jugador " + this.id + " -> Aposte por [IMPARES], perdi " + apuesta + " euros que aposte, ahora tengo " + this.saldo + " euros.");
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Ejercicio4_Casino_1.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    } else if (tipo_jugador == 2) {
                        if (numero_ganador % 2 == 0) {
                            try {

                                ganancia = apuesta * 2;

                                this.saldo += ganancia;

                                semaforo_banca.acquire();
                                saldo_banca -= ganancia;
                                semaforo_banca.release();

                                System.out.println("Jugador " + this.id + " -> Aposte por [PARES], gane " + ganancia + " euros, ahora tengo " + this.saldo + " euros.");

                            } catch (InterruptedException ex) {
                                System.out.println("Fallo en los pares.");
                            }
                        } else {
                            try {

                                this.saldo -= apuesta;

                                semaforo_banca.acquire();
                                saldo_banca += apuesta;
                                semaforo_banca.release();

                                System.out.println("Jugador " + this.id + " -> Aposte por [PARES], perdi " + apuesta + " euros que aposte, ahora tengo " + this.saldo + " euros.");
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Ejercicio4_Casino_1.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    } else if (tipo_jugador == 3) {

                        if (anterior_ganada) {
                            martin_gala = apuesta;
                        }
                        // Si gana, gana normal
                        if (numero_apuesta == numero_ganador) {
                            ganancia = martin_gala * 36;
                            try {
                                semaforo_banca.acquire();
                                saldo_banca -= ganancia;
                                semaforo_banca.release();

                                this.saldo += ganancia;

                                System.out.println("Jugador " + this.id + " -> Aposte al metodo Martingala por el [" + numero_apuesta + "], gane " + ganancia + " euros, ahora tengo " + this.saldo + " euros.");
                                anterior_ganada = true;

                            } catch (InterruptedException ex) {

                            }
                            // Si pierde, dobla su apuesta.
                        } else {
                            try {
                                semaforo_banca.acquire();
                                saldo_banca += martin_gala;
                                semaforo_banca.release();

                                this.saldo -= martin_gala;

                                System.out.println("Jugador " + this.id + " -> Aposte al metodo Martingala por el [" + numero_apuesta + "], perdi " + martin_gala + " euros que aposte, ahora tengo " + this.saldo + " euros.");
                                martin_gala *= 2;
                                anterior_ganada = false;
                            } catch (InterruptedException ex) {

                            }
                        }
                    }
                } else {
                    try {
                        sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Ejercicio4_Casino_1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }

            if (this.saldo <= 0) {
                System.out.println("Jugador " + this.id + " me he quedado con un saldo de " + this.saldo + ", no puedo seguir jugando...");
            }

        }

    }

    public static void main(String[] args) {
        Crupier c1 = new Crupier();

        // Jugador vacio -> Jugador normal.
        // Jugador (1) -> Jugador a impares.
        // Jugador (2) -> Jugador a pares.
        // Jugador (3) -> Jugador martingala.
        Jugador[] jugadores = new Jugador[23];
        for (int i = 0; i < jugadores.length; i++) {
            jugadores[i] = new Jugador();
            System.out.println(jugadores[i]);
        }

        Jugador j1 = new Jugador();
        Jugador j2 = new Jugador();
        Jugador j3 = new Jugador();
        Jugador j4 = new Jugador();
        Jugador j5 = new Jugador(1);
        Jugador j6 = new Jugador(2);
        Jugador j7 = new Jugador(3);

        c1.start();

        j1.start();
        j2.start();
        j3.start();
        j4.start();
        j5.start();
        j6.start();
        j7.start();
    }

}
