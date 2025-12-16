package tarefa2;

public class Garfo {
    // Usado para saber qual garfo é pego para mostrar no LOG
    private final int id;

    //Metrica para tarefa5 que calcula a taxa de uso do garfo
    private long tempoTotalEmUso = 0;
    private long inicioUso = 0;

    public Garfo(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Métodos da tarefa5 para controlar o tempo de uso do garfo em que ao pegar o garfo inicia a contagem 
    // e ao liberar o garfo adiciona o tempo total em uso
    public void usarGarfo() {
        this.inicioUso = System.nanoTime(); // nanoTime é mais preciso para medir intervalos curtos, ideal para métricas
    }

    public void liberarGarfo() {
        this.tempoTotalEmUso += System.nanoTime() - this.inicioUso;
    }

    public long getTempoTotalEmUso() {
        return tempoTotalEmUso;
    }
}