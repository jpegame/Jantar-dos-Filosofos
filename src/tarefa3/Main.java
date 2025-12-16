package tarefa3;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static double coeficienteVariacao(AtomicInteger[] valores) {
        // Validação para evitar divisão por zero
        int n = valores.length;
        if (n == 0) return 0.0;

        // Calcula a média
        double soma = 0.0;
        for (AtomicInteger val : valores) {
            soma += val.get();
        }
        double media = soma / n;

        // Calcula o desvio padrão
        double somaQuadrados = 0.0;
        for (AtomicInteger val : valores) {
            somaQuadrados += Math.pow(val.get() - media, 2);
        }
        double desvioPadrao = Math.sqrt(somaQuadrados / n);

        return (desvioPadrao / media) * 100; // Retorna o coeficiente de variação
    }

    public static void main(String[] args) throws InterruptedException {
        int numFilosofos = 5;
        Garfo[] garfos = new Garfo[numFilosofos];
        Semaphore limiteFilosofo = new Semaphore(numFilosofos - 1); // 4 filósofos podem tentar ao mesmo tempo
        Thread[] threads = new Thread[numFilosofos];
        Filosofo[] filosofos = new Filosofo[numFilosofos];

        // Contador de cada um dos filósofos para validar quantas vezes cada um comeu
        AtomicInteger[] contador = new AtomicInteger[numFilosofos];

        for (int i = 0; i < numFilosofos; i++) {
            garfos[i] = new Garfo(i);
            contador[i] = new AtomicInteger(0);
        }

        for (int i = 0; i < numFilosofos; i++) {
            // Definicao dos garfos como esquerdo e direito a partir do indice do filosofo, Exemplo:
            // i = 0 -> esquerdo = 0 e direito = 1
            // i = 2 -> esquerdo = 2 e direito = 3
            // i = 4 -> esquerdo = 4 e direito = 0
            Garfo esquerdo = garfos[i];
            Garfo direito = garfos[(i + 1) % numFilosofos];
            
            Filosofo filosofo = new Filosofo(i, esquerdo, direito, contador[i], limiteFilosofo);
            threads[i] = new Thread(filosofo, "Filosofo-" + i);
            threads[i].start();
            filosofos[i] = filosofo;
        }

        // Thread rodando por 5 minutos, alterado para a tarefa5
        Thread.sleep(300_000);

        // Interrompe todos os filósofos
        for (Thread t : threads) {
            t.interrupt();
        }

        // Espera threads morrerem
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("\n================ ESTATÍSTICAS DE EXECUÇÃO ================\n");

        for (int i = 0; i < 5; i++) {
            System.out.printf("Filósofo %d%n", i);
            System.out.printf("  • Refeições realizadas        : %d%n", contador[i].get());
            System.out.printf("  • Tempo médio de espera       : %.2f ms%n",
                    filosofos[i].getTempoMedioEspera());
        }

        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < 5; i++) {
            System.out.printf("• Taxa de uso do garfo %d      : %.2f%%%n",
                    i,
                    (garfos[i].getTempoTotalEmUso() / 300_000_000_000.0) * 100); // tempo total de uso / tempo total de execução em ns
            System.out.println();
        }

        double cv = coeficienteVariacao(contador);

        System.out.println(cv);
        System.out.println("----------------------------------------------------------");
        System.out.printf("Coeficiente de variação das refeições: %.4f%%%n", cv);
        System.out.println("==========================================================");
    }
}
