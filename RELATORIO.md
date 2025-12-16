## Introdução

O Jantar dos filósofos é um problema criado em 1965 por Edsger Dijkstra para simular o problema de recursos compartilhados por sistemas concorrentes. Ele consiste em uma mesa circular com N filósofos em que cada filósofo precisa pegar dois garfos, um de cada lado, para poder comer. O problema vem do fato que o número de garfos corresponde ao número de filósofos, então é preciso ter uma organização para que nenhum filósofo fique com fome.

## Metodologia

Para cada um dos testes foi obtidos métricas para assim ser possível verificar as soluções e definir qual é a mais adequada para cada situação. Todas as métricas foram extraidas do mesmo cenário de 5 filósofos, 5 garfos por 5 minutos. As métricas obtidas foram:

* Quantas vezes cada filósofo comeu
* Tempo médio de espera por filósofo (ms)
* Taxa de uso dos garfos (%)
* Coeficiente de variação (%)

## Resultados

### Solução com a inversão do filósofo

Métricas do filósofo:

| ID do filósofo | Número de refeições | Tempo média de espera por filósofo |
|:--------------:|:-------------------:|:----------------------------------:|
|0|52|1867,50 ms|
|1|55|1618,53 ms|
|2|55|1345,80 ms|
|3|56|1139,90 ms|
|4|51|1769,67 ms|

Métrica do garfo:

|ID do garfo|Taxa de uso|
|-----------|-----------|
|0|65,67%|
|1|63,67%|
|2|68,01%|
|3|75,01%|
|4|73,01%|

* Coeficiente de variação do número de refeições: `3,6042%`
* Coeficiente de variação do tempo de espera: `17,4176%`

### Solução com semáforos

Métricas do filósofo:

| ID do filósofo | Número de refeições | Tempo média de espera por filósofo |
|:--------------:|:-------------------:|:----------------------------------:|
|0|44|2733,95 ms|
|1|45|2544,07 ms|
|2|44|2867,33 ms|
|3|46|2362,25 ms|
|4|45|2778,36 ms|

Métrica do garfo:

|ID do garfo|Taxa de uso|
|-----------|-----------|
|0|87,68%|
|1|90,68%|
|2|84,35%|
|3|85,01%|
|4|92,01%|

* Coeficiente de variação do número de refeições: `1,6704%`
* Coeficiente de variação do tempo de espera: `6,8265%`

### Solução com monitor

Métricas do filósofo:

| ID do filósofo | Número de refeições | Tempo média de espera por filósofo |
|:--------------:|:-------------------:|:----------------------------------:|
|0|45|2889,83 ms|
|1|43|2796,40 ms|
|2|44|2773,67 ms|
|3|42|2907,98 ms|
|4|44|3023,70 ms|

Métrica do garfo:

|ID do garfo|Taxa de uso|
|-----------|-----------|
|0|56,68%|
|1|59,34%|
|2|59,01%|
|3|57,01%|
|4|53,34%|

* Coeficiente de variação do número de refeições: `2,3390%`
* Coeficiente de variação do tempo de espera: `3,1001%`

## Análise dos resultados

Com as métricas, agora obtidas, é possível verificar as diferentes implementações e definir o uso de cada uma delas.

* **Solução com a inversão do filósofo**: Nesta implementação, o número de vezes em cada filósofo se alimentou foi a maior obtida com a média de 53,8 refeições, porém também foi a que apresentou maior variação no número de refeições e principalmente no tempo de espera, uma variação de 17,4176% considerada alta. Portanto a solução não possui um bom fairness nos resultados. Esta solução consegue previnir o deadlock, ao quebrar a espera circular, porém não possui prevenção de starvation visto a alta variação.

* **Solução com semáforos**: Nesta implementação, a média de número de refeições diminuiu para 44,8 refeicões, mas em compensação apresentou a menor variação do número de refeições e um tempo de espera com CV de 6,8265%, apresentando um fairness melhor comparado a solução anterior. Portanto esta implementação consegue prevenir o deadlock como a anterior e possui menor risco de starvation, visto pela queda significativa de variação, mas não o previne.

* **Solução com monitor**: A última implementação foi a que os filósofos menos se alimentaram com uma média de 43.6, contudo o CV do tempo de espera foi o mais baixo com 3,1001%. Esta solução é a mais complexa e em que possui um tempo de espera maior, porém também é a solução com o tempo mais justo para com os filósofos. Finalmente, a implementação com monitor consegue tanto prevenir o deadlock quanto a starvation com a sua fila de espera.

## Conclusão

Em conclusão as soluções possuem diferentes casos de aplicações, sendo:

* **Solução com a inversão do filósofo**: útil para casos em que o desempenho é alta e desigualdades são mais toleradas.

* **Solução com semáforos**: útil para casos em que é preciso um desempenho moderado e com variações menores são mais toleradas.

* **Solução com monitor**: útil para casos em que as variações são críticas para o funcionamento e é necessário que a distribuição seja a mais justa possível, mesmo a favor do desempenho.
