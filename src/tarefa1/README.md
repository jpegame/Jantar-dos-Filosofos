# Implementação do jantar dos filósofos com deadlock

Na implementação feita neste código é possível ocorrer um problema de deadlock. O motivo se deve ao fato de que na implementação a baixo, o filosofo primeiro tentar pegar o garfo esquerdo e assim, tenta pegar o direito, porém caso todos filósofos peguem o garfo esquerdo, então nenhum deles consegue pegar o direito e ficam em deadlock. Abaixo apresenta-se o código da implementação da função comer:

```java
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
```

## Resultados

Abaixo é possível ver o problema de deadlock ocorrendo, no primeiro print do console, os filósofos 3 e 5 conseguiram comer, já no segundo print os filósofos não conseguem se alimentar, pois os garfos direitos dos mesmos estão bloqueados pelos garfos esquerdos dos filósofos ao seu lado.

### Retorno do terminal em que foi possível comer

```bash
Filósofo 5 está pensando.
Filósofo 4 está pensando.
Filósofo 2 está pensando.
Filósofo 3 está pensando.
Filósofo 3 está tentando pegar o garfo esquerdo (2)
Filósofo 5 está tentando pegar o garfo esquerdo (4)
Filósofo 3 pegou o garfo esquerdo (2)
Filósofo 5 pegou o garfo esquerdo (4)
Filósofo 3 tentando pegar o garfo direito (3)      
Filósofo 5 tentando pegar o garfo direito (0)      

Filósofo 3 pegou ambos os garfos e começou a comer.

Filósofo 5 pegou ambos os garfos e começou a comer.
Filósofo 1 está tentando pegar o garfo esquerdo (0)
Filósofo 4 está tentando pegar o garfo esquerdo (3)
Filósofo 2 está tentando pegar o garfo esquerdo (1)
```

### Retorno do terminal em que ocorreu o deadlock após mais execuções

```bash
Filósofo 3 está tentando pegar o garfo esquerdo (2)
Filósofo 2 terminou de comer e soltou os garfos.

Filósofo 2 está pensando.
Filósofo 3 pegou o garfo esquerdo (2)

Filósofo 3 pegou o garfo esquerdo (2)


Filósofo 1 pegou ambos os garfos e começou a comer.
Filósofo 3 tentando pegar o garfo direito (3)
Filósofo 2 está tentando pegar o garfo esquerdo (1)
Filósofo 1 terminou de comer e soltou os garfos.

Filósofo 1 está pensando.
Filósofo 2 pegou o garfo esquerdo (1)

Filósofo 5 pegou ambos os garfos e começou a comer.
Filósofo 2 tentando pegar o garfo direito (2)
Filósofo 1 está tentando pegar o garfo esquerdo (0)
Filósofo 5 terminou de comer e soltou os garfos.

Filósofo 5 está pensando.
Filósofo 1 pegou o garfo esquerdo (0)

Filósofo 4 pegou ambos os garfos e começou a comer.
Filósofo 1 tentando pegar o garfo direito (1)
```
