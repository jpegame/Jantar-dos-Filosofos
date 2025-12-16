package tarefa3;

import java.util.concurrent.Semaphore;

class Garfo {
    private final int id;

    //Metrica para tarefa5 que calcula a taxa de uso do garfo
    private long tempoTotalEmUso = 0;
    private long inicioUso = 0;

    // Garante que apenas uma thread esteja em posse deste garfo
    private final Semaphore semaforo = new Semaphore(1);

    public Garfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void pegar() throws InterruptedException {
        this.inicioUso = System.nanoTime(); // nanoTime é mais preciso para medir intervalos curtos, ideal para métricas
        // bloqueia se outro filósofo estiver usando baseado no bloqueio linha 7
        semaforo.acquire();
    }

    public void soltar() {
        // libera o garfo para outra thread poder acessar este recurso
        this.tempoTotalEmUso += System.nanoTime() - this.inicioUso;
        semaforo.release();
    }

    public long getTempoTotalEmUso() {
        return tempoTotalEmUso;
    }
}