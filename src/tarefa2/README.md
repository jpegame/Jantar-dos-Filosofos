# Implementação do jantar dos filósofos com prevenção de deadlock

Nesta implementação, é feita uma prevenção para que não ocorra o deadlock, para isso é utilizado um método em que se inverte a ordem de pegar os garfos de um filósofo, no caso foi escolhido o filósofo com id 4 para pegar da ordem direita/esquerda, invés de esquerda/direita. Abaixo segue a implementação de como os garfos são pegos:

```java
private void pegarGarfo(Garfo primeiroGarfo, Garfo segundoGarfo) throws InterruptedException {
    this.log("está tentando pegar o primeiro garfo (" + garfoEsquerdo.getId() + ")");

    // Filosofo tenta primeiramente pegar o garfo primeiro garfo e ao pegar tenta o segundo, 
    // a função synchronized serve para garantir que apenas um filosofo consiga pegar o primeiroGarfo
    synchronized (primeiroGarfo) {
        this.log("pegou o primeiro garfo (" + primeiroGarfo.getId() + ")");
        this.log("tentando pegar o segundo garfo (" + segundoGarfo.getId() + ")");

        // Mesmo processo acima, mas agora para o segundo
        synchronized (segundoGarfo) {
            this.log("pegou ambos os garfos e começou a comer.");
            Thread.sleep((random.nextInt(3) + 1) * 1000);
            this.log("terminou de comer e soltou os garfos.\n");
        }
    }
}

private void comer() throws InterruptedException {
    // Um garfo é pego em uma ordem diferente para que a cadeia circular seja quebrada, prevenindo deadlock
    if (this.id == 4) {
        pegarGarfo(garfoDireito, garfoEsquerdo);
    } else {
        pegarGarfo(garfoEsquerdo, garfoDireito);
    }
}
```

## Resultados

Na resposta apresentada no terminal abaixo, foi possível que os filósofos comeram inúmeras vezes durante o tempo de 2 minutos de execução. Porém alguns filósofos comeram mais que outros, o filósofo 3 por exemplo comeu 26 vezes, enquanto o 4 comeu 18. O problema de starvation então ainda ocorre, mesmo não travando para que todos comam como o deadlock, o filósofo 4 ainda sim comeu menos que os outros, pelo fato de que o 3 (que foi o que mais se alimentou), travou o 4 de se alimentar.

```bash
Filósofo 0 está pensando.
Filósofo 4 pegou o primeiro garfo (0)
Filósofo 1 pegou o primeiro garfo (1)
Filósofo 4 tentando pegar o segundo garfo (4)
Filósofo 1 tentando pegar o segundo garfo (2)
Filósofo 3 terminou de comer e soltou os garfos.

Filósofo 3 está pensando.
Filósofo 2 pegou ambos os garfos e começou a comer.
Filósofo 4 pegou ambos os garfos e começou a comer.
Filósofo 0 está tentando pegar o primeiro garfo (0)
Filósofo 1 pegou ambos os garfos e começou a comer.
Filósofo 0 pegou o primeiro garfo (0)
Filósofo 0 tentando pegar o segundo garfo (1)
Filósofo 0 pegou ambos os garfos e começou a comer.

=== Estatísticas de execução (vezes que cada filósofo comeu) ===
Filósofo 0: 19 vezes
Filósofo 1: 20 vezes
Filósofo 2: 19 vezes
Filósofo 3: 26 vezes
Filósofo 4: 18 vezes
```
