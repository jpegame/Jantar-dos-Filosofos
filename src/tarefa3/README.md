# Implementação do jantar dos filósofos com semáforos

A implementação foi feita utilizando semáforos, em que um é implementado no próprio garfo, que impede que mais de um filósofo pode interagir com ele conforme o Código 1 e outra implementação no filósofo que limita a 4 filósofos disponiveis para pegar o garfo, garantindo que 1 dos filósofos tenha os dois garfos disponíveis conforme o Código 2

> Código 1

```java
// Garante que apenas uma thread esteja em posse deste garfo
private final Semaphore semaforo = new Semaphore(1);

public void pegar() throws InterruptedException {
    // bloqueia se outro filósofo estiver usando baseado no bloqueio linha 7
    semaforo.acquire();
}

public void soltar() {
    // libera o garfo para outra thread poder acessar este recurso
    semaforo.release();
}
```

> Código 2

```java
// Semaforo que limite quantos filosofos podem pegar garfo ao mesmo tempo
private final Semaphore limiteFilosofo;

private void comer() throws InterruptedException {
    limiteFilosofo.acquire(); // limita 4 filósofos tentando ao mesmo tempo, bloqueando acessos alem do valor limite

    // Filosofos tentaram pegar os dois garfos, que por conta do limite de 4 no máximo, 
    // um filosofo sempre vai ter um garfo disponivel para poder comer, evitando o deadlock
    // No finally, após comer o semaforo libera espaço para mais um filosofo
    try {
        this.log("tentando pegar garfos.");
        garfoEsquerdo.pegar();
        garfoDireito.pegar();

        this.log("pegou ambos os garfos e começou a comer.");
        Thread.sleep((random.nextInt(3) + 1) * 1000);

        garfoDireito.soltar();
        garfoEsquerdo.soltar();

        // Incremento no número de vezes que comeu para estatisticas
        this.numVezesQueComeu.incrementAndGet();
        
        this.log("terminou de comer e soltou os garfos.\n");
    } finally {
        limiteFilosofo.release();
    }
}
```

## Resultados

A solução proposta na tarefa2 mostrou que a diferença da quantidade de vezes que um filósofo comeu era considerável, tendo o filósofo 4 comendo 18 vezes enquanto o 3 comeu 26 vezes. Já os resultados obtidos na execução com os semáforos foi mais uniforme sendo a maior diferença o filósofo 3 comendo 20 vezes, enquanto os filósofos 0 e 2 comeram 22. Abaixo apresenta as estatísticas obtidas:

```bash
=== Estatísticas de execução (vezes que cada filósofo comeu) ===
Filósofo 0: 22 vezes
Filósofo 1: 21 vezes
Filósofo 2: 22 vezes
Filósofo 3: 20 vezes
Filósofo 4: 21 vezes
```

### Vantagens

* Prevenção de deadlocks
* Mais uniforme o número de vezes que cada filósofo comeu
* Boa escabilidade, pois o número limite de filósofos sempre será (N-1)

### Desvantagens

* Redução do paralelismo, pois um filósofo sempre estará esperando
* Ainda é possível ocorrer starvation, se os outros filósofos ocuparem os espaços disponíveis
