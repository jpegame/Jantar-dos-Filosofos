package tarefa3;

import java.util.concurrent.Semaphore;

class Garfo {
    private final int id;

    // Garante que apenas uma thread esteja em posse deste garfo
    private final Semaphore semaforo = new Semaphore(1);

    public Garfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void pegar() throws InterruptedException {
        // bloqueia se outro filósofo estiver usando baseado no bloqueio linha 7
        semaforo.acquire();
    }

    public void soltar() {
        // libera o garfo para outra thread poder acessar este recurso
        semaforo.release();
    }
}