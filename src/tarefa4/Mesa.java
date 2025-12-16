package tarefa4;

import java.util.LinkedList;
import java.util.Queue;

class Mesa {
    // Define True como em um uso
    private final boolean[] garfos = new boolean[5];
    private final Queue<Integer> fila = new LinkedList<>();

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
    }

    public synchronized void soltarGarfos(int filosofoId) {
        // Pega os garfos utilizados pelo filosofo e define como false, 
        // ou seja não está ocupado e notifica os outros, acordando os 
        // filosofos em estado de espera
        int esquerdo = filosofoId;
        int direito = (filosofoId + 1) % 5;

        this.garfos[esquerdo] = false;
        this.garfos[direito] = false;
        notifyAll();
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
