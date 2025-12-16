package tarefa4;

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

        Mesa mesa = new Mesa();
        Thread[] threads = new Thread[numFilosofos];
        Filosofo[] filosofos = new Filosofo[numFilosofos];
        AtomicInteger[] contador = new AtomicInteger[numFilosofos];

        for (int i = 0; i < 5; i++) {
            contador[i] = new AtomicInteger(0);
            Filosofo filosofo = new Filosofo(i, mesa, contador[i]);
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
                    i, mesa.getTaxaUsoGarfo(i, 300_000_000_000L)); // tempo total de uso / tempo total de execução em ns
            System.out.println();
        }

        double cv = coeficienteVariacao(contador);

        System.out.println(cv);
        System.out.println("----------------------------------------------------------");
        System.out.printf("Coeficiente de variação das refeições: %.4f%%%n", cv);
        System.out.println("==========================================================");
    }
}
