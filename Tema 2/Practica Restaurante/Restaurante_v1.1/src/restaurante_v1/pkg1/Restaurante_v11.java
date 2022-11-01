package restaurante_v1.pkg1;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Restaurante_v11 {

    static int pizzas_servidas = 0;
    static int bocatas_servidos = 0;

    static Semaphore[] semaforos = {new Semaphore(1), new Semaphore(1)};
    static int[] precios = {12, 6};

    static int total_clientes = 0;
    static Semaphore semaforo_clientes = new Semaphore(1);

    static String[] nombre_producto = {"Pizza", "Bocadillo"};
    static int[] mostrador = {0, 0};
    static Semaphore[] semaforo_mostrador = {new Semaphore(1), new Semaphore(1)};

    static int ganancias = 0;

    static boolean restaurante_abierto = true;

    public static class Pizzero extends Thread {

        Pizzero() {
        }

        public void run() {
            while (restaurante_abierto) {
                try {
                    System.out.println("Pizzero -> Estirando masa de la Pizza.");
                    sleep(2000);
                    System.out.println("Pizzero -> Poniendo ingredientes de la Pizza.");
                    sleep(1000);
                    System.out.println("Pizzero -> Cocinando Pizza.");
                    sleep(5000);

                    semaforo_mostrador[0].acquire();
                        mostrador[0]++;
                    System.out.println("Pizzero -> Pizza terminada, en el mostrador hay [" + mostrador[0] + "] Pizzas preparadas.");
                    semaforo_mostrador[0].release();

                } catch (InterruptedException ex) {
                    System.out.println("Error al hacer Pizza.");
                }
            }
        }

    }

    public static class Bocatero extends Thread {

        Bocatero() {
        }

        public void run() {
            while (restaurante_abierto) {
                try {
                    System.out.println("Bocatero -> Voy a cortar el pan del Bocata.");
                    sleep(1000);
                    System.out.println("Bocatero -> Voy a poner mayonesa al Bocata.");
                    sleep(1000);
                    System.out.println("Bocatero -> Voy a poner el resto de ingredientes al Bocata.");
                    sleep(2000);
                    System.out.println("Bocatero -> Voy a envolver el Bocata.");
                    sleep(3000);

                    semaforo_mostrador[1].acquire();
                        mostrador[1]++;
                    System.out.println("Bocatero -> Bocata terminado, en el mostrador hay [" + mostrador[1] + "] Bocatas preparados.");
                    semaforo_mostrador[1].release();

                } catch (InterruptedException ex) {
                    System.out.println("Error al hacer Bocata.");
                }

            }
        }
    }

    public static class Cliente extends Thread {

        Random producto = new Random();

        private int producto_deseado;
        private int cantidad_deseada;

        int id = 0;
        static int contador = 0;

        private boolean atendido = false;

        Cliente() {
            this.contador++;
            this.id = contador;
            producto_deseado = producto.nextInt(2);
            cantidad_deseada = producto.nextInt(4) + 1;
        }

        public void run() {

            try {
                semaforo_clientes.acquire();
                    total_clientes++;
                semaforo_clientes.release();
            } catch (InterruptedException ex) {
                System.out.println("Error al sumar clientes al total.");
            }

            if (producto_deseado == 0) {
                pizzas_servidas += this.cantidad_deseada;
            } else if (producto_deseado == 1) {
                bocatas_servidos += this.cantidad_deseada;
            }

            while (restaurante_abierto && !this.atendido) {
                try {
                    semaforos[producto_deseado].acquire();
                } catch (InterruptedException ex) {

                }
                int cogidos = 0;

                try {

                    System.out.println("Cliente " + this.id + " -> Hola, quiero " + nombre_producto[producto_deseado] + ", voy a pensar que cantidad quiero.");
                    sleep(10000);
                    System.out.println("Cliente " + this.id + " -> Vale, quiero " + this.cantidad_deseada + " " + nombre_producto[producto_deseado] + ".");

                    while (cantidad_deseada > cogidos) {
                        if (mostrador[producto_deseado] >= cantidad_deseada - cogidos) {
                            System.out.println("Cliente " + this.id + " -> Hay " + (cantidad_deseada - cogidos) + " " + nombre_producto[producto_deseado] + " o mas en el mostrador, me llevo los que queria: " + cantidad_deseada + " !");
                            int precio = 0;

                            semaforo_mostrador[producto_deseado].acquire();
                                mostrador[producto_deseado] = mostrador[producto_deseado] - (cantidad_deseada - cogidos);
                            semaforo_mostrador[producto_deseado].release();

                            precio = cantidad_deseada * precios[producto_deseado];

                            System.out.println("Cliente " + this.id + " -> Cada " + nombre_producto[producto_deseado] + " son " + precios[producto_deseado] + " euros, me llevo " + cantidad_deseada + " asi que te pago: " + precio + " euros.");
                            System.out.println("Cliente " + this.id + " -> Gracias, adios!");

                            ganancias += precio;
                            cogidos = cantidad_deseada;
                            this.atendido = true;

                            try {
                                semaforo_clientes.acquire();
                                    total_clientes--;
                                semaforo_clientes.release();
                            } catch (InterruptedException ex) {
                                System.out.println("Error al sumar clientes al total.");
                            }

                        } else {

                            cogidos += mostrador[producto_deseado];
                                System.out.println("Cliente " + this.id + " -> Aun no hay suficientes " + nombre_producto[producto_deseado] + ", cogere [" + mostrador[producto_deseado] + "] y esperare al resto, ahora mismo tengo [" + cogidos + "].");
                            semaforo_mostrador[producto_deseado].acquire();
                                mostrador[producto_deseado] = 0;
                            semaforo_mostrador[producto_deseado].release();
                            sleep(10000);
                        }
                    }

                } catch (InterruptedException ex) {
                }

                if (total_clientes == 0) {
                    restaurante_abierto = false;
                    System.out.println("Restaurante -> Hemos atendido a todos los clientes!");
                    System.out.println("Restaurante -> Hemos vendido un total de " + ganancias + " euros vendiendo " + pizzas_servidas + " Pizzas y " + bocatas_servidos + " Bocatas.");
                    semaforos[producto_deseado].release();
                } else {
                    semaforos[producto_deseado].release();
                }
            }

        }

        public static void main(String[] args) throws InterruptedException {
            Scanner teclado = new Scanner(System.in);

            Pizzero p1 = new Pizzero();
            Bocatero b1 = new Bocatero();

            System.out.println("Cuantos clientes quieres que vayan al restaurante?");
            int cantidad = teclado.nextInt();

            Cliente[] clientes = new Cliente[cantidad];

            p1.start();
            b1.start();

            for (int i = 0; i < cantidad; i++) {
                clientes[i] = new Cliente();
                clientes[i].start();
            }
            clientes[cantidad - 1].join();

        }
    }
}
