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

Na resposta apresentada no terminal abaixo, foi possível que os filósofos ainda se alimentavam mesmo após 5 minutos de execução.

```bash
Filósofo 3 está tentando pegar o primeiro garfo (3)
Filósofo 1 pegou ambos os garfos e começou a comer.
Filósofo 2 está pensando.
Filósofo 3 pegou o primeiro garfo (3)
Filósofo 3 tentando pegar o segundo garfo (4)
Filósofo 4 terminou de comer e soltou os garfos.

Filósofo 0 está tentando pegar o primeiro garfo (0)
Filósofo 3 pegou ambos os garfos e começou a comer.
Filósofo 4 está pensando.
Filósofo 0 pegou o primeiro garfo (0)
Filósofo 0 tentando pegar o segundo garfo (1)
Filósofo 3 terminou de comer e soltou os garfos.

Filósofo 1 terminou de comer e soltou os garfos.

Filósofo 2 está tentando pegar o primeiro garfo (2)
Filósofo 0 pegou ambos os garfos e começou a comer.
Filósofo 1 está pensando.
Filósofo 3 está pensando.
Filósofo 2 pegou o primeiro garfo (2)
Filósofo 2 tentando pegar o segundo garfo (3)
Filósofo 2 pegou ambos os garfos e começou a comer.
```
