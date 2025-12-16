package tarefa4;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

class Filosofo implements Runnable {
    private final int id;
    private final Mesa mesa;
    private final Random random = new Random();

    //Metrica para tarefa5 que precisa do tempo medio de espera
    private long tempoTotalEspera = 0;
    private int numeroTentativas = 0;

    // AtomicInteger é um inteiro que permite operacoes atomicas (Thread-Safe) 
    // ideal para algoritmos concorrentes como este
    private final AtomicInteger numVezesQueComeu;

    public Filosofo(int id, Mesa mesa, AtomicInteger numVezesQueComeu) {
        this.id = id;
        this.mesa = mesa;
        this.numVezesQueComeu = numVezesQueComeu;
    }

    private void log(String text) {
        System.out.println("Filósofo " + this.id + " " + text);
    }

    private void pensar() throws InterruptedException {
        this.log("está pensando.");
        Thread.sleep((random.nextInt(3) + 1) * 1000);
    }

    private void comer() throws InterruptedException {
        // Começa a medir o tempo de espera e adiciona mais uma tentativa para comer
        long inicioEspera = System.nanoTime();
        numeroTentativas++;

        // Pega os garfos, espera tres segundos de comer e entao libera
        mesa.pegarGarfos(id);
        this.log("pegou ambos os garfos e começou a comer.");

        long fimEspera = System.nanoTime();
        tempoTotalEspera += (fimEspera - inicioEspera);

        Thread.sleep((random.nextInt(3) + 1) * 1000); // espera de 1 a 3 segundos

        // Incremento no número de vezes que comeu para estatisticas
        this.numVezesQueComeu.incrementAndGet();

        mesa.soltarGarfos(id);
        this.log("terminou de comer e soltou os garfos.\n");
    }

    public double getTempoMedioEspera() {
        if (numeroTentativas == 0) return 0.0;
        return (tempoTotalEspera / (double) numeroTentativas) / 1_000_000.0;
    }

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
