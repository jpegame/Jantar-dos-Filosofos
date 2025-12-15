package tarefa2;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Garfo[] garfos = new Garfo[5];
        Thread[] threads = new Thread[5];

        // Contador de cada um dos filósofos para validar quantas vezes cada um comeu
        AtomicInteger[] contador = new AtomicInteger[5];

        for (int i = 0; i < 5; i++) {
            garfos[i] = new Garfo(i);
            contador[i] = new AtomicInteger(0);
        }

        for (int i = 0; i < 5; i++) {
            // Definicao dos garfos como esquerdo e direito a partir do indice do filosofo, Exemplo:
            // i = 0 -> esquerdo = 0 e direito = 1
            // i = 2 -> esquerdo = 2 e direito = 3
            // i = 4 -> esquerdo = 4 e direito = 0
            Garfo esquerdo = garfos[i];
            Garfo direito = garfos[(i + 1) % 5];

            // Passando filosofo que implementa a interface Runnable para uma Thread que espera um Runnable
            Filosofo filosofo = new Filosofo(i, esquerdo, direito, contador[i]);
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