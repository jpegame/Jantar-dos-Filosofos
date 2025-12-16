package tarefa4;

import java.util.LinkedList;
import java.util.Queue;

class Mesa {
    // Define True como em um uso
    private final boolean[] garfos = new boolean[5];
    private final Queue<Integer> fila = new LinkedList<>();

    //Metrica para tarefa5 que calcula a taxa de uso do garfo
    private final long[] inicioUsoGarfo = new long[5];
    private final long[] tempoTotalUsoGarfo = new long[5];

    public synchronized void pegarGarfos(int filosofoId) throws InterruptedException {
        this.fila.add(filosofoId);

        // Coloca o filosofo em espera enquanto ele não pode comer
        while (!this.podeComer(filosofoId)) {
            wait();
        }

        // Caso o filosofo possa comer, remove da fila de espera e ocupa os garfos
        // a esquerda e direita para comer

        this.fila.remove();
        int esquerdo = filosofoId;
        int direito = (filosofoId + 1) % 5;

        this.garfos[esquerdo] = true;
        this.garfos[direito] = true;

        // Começa a contagem de tempo do garfo para a métrica de taxa de uso
        inicioUsoGarfo[esquerdo] = System.nanoTime();
        inicioUsoGarfo[direito] = System.nanoTime();
    }

    public synchronized void soltarGarfos(int filosofoId) {
        // Pega os garfos utilizados pelo filosofo e define como false, 
        // ou seja não está ocupado e notifica os outros, acordando os 
        // filosofos em estado de espera
        int esquerdo = filosofoId;
        int direito = (filosofoId + 1) % 5;

        // Finaliza a contagem de tempo do garfo para a métrica de taxa de uso
        long agora = System.nanoTime();
        tempoTotalUsoGarfo[esquerdo] += agora - inicioUsoGarfo[esquerdo];
        tempoTotalUsoGarfo[direito] += agora - inicioUsoGarfo[direito];

        this.garfos[esquerdo] = false;
        this.garfos[direito] = false;
        notifyAll();
    }

    // Retorna a taxa de uso do garfo em porcentagem levando 
    // em conta o tempo de execução, no caso 5 minutos
    public double getTaxaUsoGarfo(int id, long tempoTotalSimulacao) {
        return (tempoTotalUsoGarfo[id] / (double) tempoTotalSimulacao) * 100;
    }

    private boolean podeComer(int filosofoId) {
        int esquerdo = filosofoId;
        int direito = (filosofoId + 1) % 5;

        // Caso a head da fila esteja vazia ou 
        // o filosofo passado não seja a head da fila, retorna que não pode comer
        if (this.fila.peek() == null || this.fila.peek() != filosofoId) {
            return false;
        }

        // Caso passe retorna True se o garfo esquerdo e direito forem false
        return !(this.garfos[esquerdo] || this.garfos[direito]);
    }
}
