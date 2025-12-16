package tarefa4;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numFilosofos = 5;

        Mesa mesa = new Mesa();
        Thread[] threads = new Thread[numFilosofos];
        AtomicInteger[] contador = new AtomicInteger[5];

        for (int i = 0; i < 5; i++) {
            contador[i] = new AtomicInteger(0);
            threads[i] = new Thread(new Filosofo(i, mesa, contador[i]), "Filosofo-" + i);
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
