public class Main {
    public static void main(String[] args) {
        Garfo[] garfos = new Garfo[5];

        for (int i = 0; i < 5; i++) {
            garfos[i] = new Garfo(i);
        }

        for (int i = 0; i < 5; i++) {
            // Definicao dos garfos como esquerdo e direito a partir do indice do filosofo, Exemplo:
            // i = 0 -> esquerdo = 0 e direito = 1
            // i = 2 -> esquerdo = 2 e direito = 3
            // i = 4 -> esquerdo = 4 e direito = 0
            Garfo esquerdo = garfos[i];
            Garfo direito = garfos[(i + 1) % 5];

            // Passando filosofo que implementa a interface Runnable para uma Thread que espera um Runnable
            Filosofo filosofo = new Filosofo(i, esquerdo, direito);
            Thread thread = new Thread(filosofo, "Filosofo-" + i);
            thread.start();
        }
    }
}