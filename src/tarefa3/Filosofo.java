package tarefa3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Random;

class Filosofo implements Runnable {
    private final int id;
    private final Garfo garfoEsquerdo;
    private final Garfo garfoDireito;
    private final Random random = new Random();

    // Semaforo que limite quantos filosofos podem pegar garfo ao mesmo tempo
    private final Semaphore limiteFilosofo;

    // AtomicInteger é um inteiro que permite operacoes atomicas (Thread-Safe) 
    // ideal para algoritmos concorrentes como este
    private final AtomicInteger numVezesQueComeu;

    public Filosofo(int id, Garfo esquerdo, Garfo direito, AtomicInteger numVezesQueComeu, Semaphore limiteFilosofo) {
        this.id = id;
        this.garfoEsquerdo = esquerdo;
        this.garfoDireito = direito;
        this.limiteFilosofo = limiteFilosofo;
        this.numVezesQueComeu = numVezesQueComeu;
    }

    private void log(String text) {
        System.out.println("Filósofo " + this.id + " " + text);
    }

    // Funcao que coloca a Thread para dormir entre 1 a 3 segundos
    private void pensar() throws InterruptedException {
        this.log("está pensando.");
        Thread.sleep((random.nextInt(3) + 1) * 1000);
    }

    private void comer() throws InterruptedException {
        limiteFilosofo.acquire(); // limita 4 filósofos tentando ao mesmo tempo, bloqueando acessos alem do valor limite

        // Filosofos tentaram pegar os dois garfos, que por conta do limite de 4 no máximo, 
        // um filosofo sempre vai ter um garfo disponivel para poder comer, evitando o deadlock
        // No finally, após comer o semaforo libera espaço para mais um filosofo
        try {
            this.log("tentando pegar garfos.");
            garfoEsquerdo.pegar();
            garfoDireito.pegar();

            this.log("pegou ambos os garfos e começou a comer.");
            Thread.sleep((random.nextInt(3) + 1) * 1000);

            garfoDireito.soltar();
            garfoEsquerdo.soltar();

            // Incremento no número de vezes que comeu para estatisticas
            this.numVezesQueComeu.incrementAndGet();
            
            this.log("terminou de comer e soltou os garfos.\n");
        } finally {
            limiteFilosofo.release();
        }
    }

    // processo em que os filosofos pensam por um tempo aleatorio e tentam comer pelo tempo aleatorio
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                pensar();
                comer();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}