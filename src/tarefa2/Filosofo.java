package tarefa2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Filosofo implements Runnable {
    private final int id;
    private final Garfo garfoEsquerdo;
    private final Garfo garfoDireito;
    private final Random random = new Random();

    // AtomicInteger é um inteiro que permite operacoes atomicas (Thread-Safe) 
    // ideal para algoritmos concorrentes como este
    private final AtomicInteger numVezesQueComeu;

    public Filosofo(int id, Garfo esquerdo, Garfo direito, AtomicInteger numVezesQueComeu) {
        this.id = id;
        this.garfoEsquerdo = esquerdo;
        this.garfoDireito = direito;
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

    private void pegarGarfo(Garfo primeiroGarfo, Garfo segundoGarfo) throws InterruptedException {
        this.log("está tentando pegar o primeiro garfo (" + garfoEsquerdo.getId() + ")");

        // Filosofo tenta primeiramente pegar o garfo primeiro garfo e ao pegar tenta o segundo, 
        // a função synchronized serve para garantir que apenas um filosofo consiga pegar o primeiroGarfo
        synchronized (primeiroGarfo) {
            this.log("pegou o primeiro garfo (" + primeiroGarfo.getId() + ")");
            this.log("tentando pegar o segundo garfo (" + segundoGarfo.getId() + ")");

            // Mesmo processo acima, mas agora para o segundo
            synchronized (segundoGarfo) {
                this.log("pegou ambos os garfos e começou a comer.");
                Thread.sleep((random.nextInt(3) + 1) * 1000);

                // Incremento no número de vezes que comeu para estatisticas
                this.numVezesQueComeu.incrementAndGet();
                this.log("terminou de comer e soltou os garfos.\n");
            }
        }
    }

    private void comer() throws InterruptedException {
        // Um garfo é pego em uma ordem diferente para que a cadeia circular seja quebrada, prevenindo deadlock
        if (this.id == 4) {
            pegarGarfo(garfoDireito, garfoEsquerdo);
        } else {
            pegarGarfo(garfoEsquerdo, garfoDireito);
        }
    }

    // processo em que os filosofos pensam por um tempo aleatorio e tentam comer pelo tempo aleatorio infinitamente
    @Override
    public void run() {
        try {
            while (true) {
                pensar();
                comer();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
