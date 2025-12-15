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

    private void log(String text) {
        System.out.println("Filósofo " + this.nome + " " + text);
    }

    // Funcao que coloca a Thread para dormir entre 1 a 3 segundos
    private void pensar() throws InterruptedException {
        this.log("está pensando.");
        Thread.sleep((random.nextInt(3) + 1) * 1000);
    }

    private void comer() throws InterruptedException {
        this.log("está tentando pegar o garfo esquerdo (" + garfoEsquerdo.getId() + ")");

        // Filosofo tenta primeiramente pegar o garfo esquerdo e ao pegar tenta o direito, 
        // a função synchronized serve para garantir que apenas um filosofo consiga pegar o garfoEsquerdo
        synchronized (garfoEsquerdo) {
            this.log("pegou o garfo esquerdo (" + garfoEsquerdo.getId() + ")");
            this.log("tentando pegar o garfo direito (" + garfoDireito.getId() + ")");

            // Mesmo processo acima, mas agora para o direito
            synchronized (garfoDireito) {
                this.log("pegou ambos os garfos e começou a comer.");
                Thread.sleep((random.nextInt(3) + 1) * 1000);
                this.log("terminou de comer e soltou os garfos.\\n");
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
