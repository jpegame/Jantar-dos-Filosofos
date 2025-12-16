# Implementação do jantar dos filósofos com monitores

Esta implementação foi feita utilizando um monitor `Mesa` que define se um filósofo pode comer ou não. O monitor verifica se os garfos de ambos os lados estão disponíveis, senão coloca para espera o filósofo. Quando um filósofo parar de comer ocorre um aviso para todas as threads que estavam esperando para acordar. O código abaixo demonstra como este processo funciona:

> Tenta pegar garfos e se não pode comer adiciona na fila de espera

```java
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
```

> Solta os garfos ao terminar de comer e notifica as outras Threads para elas sairem do mode de espera

```java
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
```

## Fairness

O Fairness é garantido pelo fato de que por ser implementado uma Fila, o primeiro filósofo disponível para comer, será quem comerá, impedindo que algum fure a fila e coma a mais.

## Deadlock e Starvation

O filósofo sempre pega os dois garfos ao mesmo tempo, pois é garantido que estará disponível para uso, não existe estado intermediário, ou ele pega os dois garfos ou nenhum, impedindo deadlock.

Quando um filósofo termina de comer ele notifica todos os outros, e então o primeiro da fila de espera disponível pode comer, assim nenhum deles fica no processo de starvation.

## Comparativo com as outras versões

Em relação as outras versões, esta possui uma maior vantagem pois previne tanto o deadlock de ocorrer quanto o processo de starvation. Porém no caso testado pelo período de 2 minutos os filósofos comeram menos por conta da espera da fila garantida de fairness. Entretanto também houve pequena variaão entre o número de vezes em que um filósofo comeu como mostra os resultados abaixo:

```bash
=== Estatísticas de execução (vezes que cada filósofo comeu) ===
Filósofo 0: 15 vezes
Filósofo 1: 17 vezes
Filósofo 2: 17 vezes
Filósofo 3: 17 vezes
Filósofo 4: 16 vezes 
```
