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

Coeficiente de variação: `3,6042%`

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

Coeficiente de variação: `1,6704%`

### Solução com monitor

teste
