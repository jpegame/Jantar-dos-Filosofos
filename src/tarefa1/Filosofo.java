package tarefa1;

import java.util.Random;

public class Filosofo implements Runnable {
    private final String nome;
    private final Garfo garfoEsquerdo;
    private final Garfo garfoDireito;
    private final Random random = new Random();

    public Filosofo(String nome, Garfo esquerdo, Garfo direito) {
        this.nome = nome;
        this.garfoEsquerdo = esquerdo;
        this.garfoDireito = direito;
    }

    // Funcao que coloca a Thread para dormir entre 1 a 3 segundos
    private void pensar() throws InterruptedException {
        System.out.println("Filósofo " + this.nome + " está pensando.");
        Thread.sleep((random.nextInt(3) + 1) * 1000);
    }

    private void comer() throws InterruptedException {
        System.out.println("Filósofo " + this.nome + " está tentando pegar o garfo esquerdo (" 
                + garfoEsquerdo.getId() + ")");

        // Filosofo tenta primeiramente pegar o garfo esquerdo e ao pegar tenta o direito, 
        // a função synchronized serve para garantir que apenas um filosofo consiga pegar o garfoEsquerdo
        synchronized (garfoEsquerdo) {
            System.out.println("Filósofo " + this.nome + " pegou o garfo esquerdo (" 
                    + garfoEsquerdo.getId() + ")");
            System.out.println("Filósofo " + this.nome + " tentando pegar o garfo direito (" 
                    + garfoDireito.getId() + ")");

            // Mesmo processo acima, mas agora para o direito
            synchronized (garfoDireito) {
                System.out.println(" \nFilósofo " + this.nome + " pegou ambos os garfos e começou a comer.");
                Thread.sleep((random.nextInt(3) + 1) * 1000);
                System.out.println("Filósofo " + this.nome + " terminou de comer e soltou os garfos.\n");
            }
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
