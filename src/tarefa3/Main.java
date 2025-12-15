package tarefa3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numFilosofos = 5;
        Garfo[] garfos = new Garfo[numFilosofos];
        Semaphore limiteFilosofo = new Semaphore(numFilosofos - 1); // 4 filósofos podem tentar ao mesmo tempo
        Thread[] threads = new Thread[numFilosofos];

        // Contador de cada um dos filósofos para validar quantas vezes cada um comeu
        AtomicInteger[] contador = new AtomicInteger[5];

        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new Garfo(i);
            contador[i] = new AtomicInteger(0);
        }

        for (int i = 0; i < numFilosofos; i++) {
            // Definicao dos garfos como esquerdo e direito a partir do indice do filosofo, Exemplo:
            // i = 0 -> esquerdo = 0 e direito = 1
            // i = 2 -> esquerdo = 2 e direito = 3
            // i = 4 -> esquerdo = 4 e direito = 0
            Garfo esquerdo = garfos[i];
            Garfo direito = garfos[(i + 1) % numFilosofos];
            
            Filosofo filosofo = new Filosofo(i, esquerdo, direito, contador[i], limiteFilosofo);
            threads[i] = new Thread(filosofo, "Filosofo-" + i);
            threads[i].start();
        }

        // Thread rodando por 2 minutos
        Thread.sleep(120_000);

        // Interrompe todos os filósofos
        for (Thread t : threads) {
            t.interrupt();
        }

        // Espera threads morrerem
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\n=== Estatísticas de execução (vezes que cada filósofo comeu) ===");
        for (int i = 0; i < 5; i++) {
            System.out.println("Filósofo " + i + ": " + contador[i].get() + " vezes");
        }
    }
}
