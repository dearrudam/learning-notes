---
title: [PT-BR] Transformando listas com Java Stream API 
description: 
language: PT-BR 
tags: [java,beginners,tutorial,braziliandevs]
cover_image:
series: Programação Funcional com Java
published: false
publish_date: Tue 13 Sep 2022 01:48:18 PM -03
---
E aí pessoal! Espero que todos estejam bem!!!

Bom, após descansar do [**TDC Business 2022**](https://thedevconf.com/tdc/2022/business/), onde tive a honra e a felicidade de realizar um sonho: ser coordenador na trilha de Carreira e Mentoria, ser expositor da empresa em que trabalho ([StackSpot](https://stackspot.com)) e ser palestrante na trilha de arquitetura Java, falando sobre **Streams API** junto com [Rolmer Telis de Oliveria](https://www.linkedin.com/in/rolmertelis/), estou de volta explorando, estudando e aprendendo cada vez mais sobre **Programação Funcional com Java**.

No artigo anterior, vimos que a partir do Java 8, a interface `Iterable` foi melhorada com um método especial: um  **default method** chamado `forEach`. Agora, caso estivermos trabalhando com objetos que implementam a interface `Iterable`, poderemos usufruir desse método para iterar nos itens de maneira declarativa, utilizando uma *lambda expression* ou *method references*.

## Mas o que são Default Methods

A feature **default method**, que foi adicionado ao Java 8, permitiu uma evolução suave de toda a API do Java, permitindo *métodos padrões* fossem implementados em interfaces, mantendo assim toda uma retro-compatibilidade das versões anteriores do Java. 

Com isso, podemos dizer que todos objetos que implementam a interface `Iterable` poderão usufruir do método `forEach`, já que esse é um **default method**, implementado na própria interface `Iterable`.


## Muito mais que iterar em uma lista

Já conhecemos o método `forEach`, na qual podemos iterar nos itens de uma determinada lista ou coleções, mas sabemos também que só iterar não nos basta! 

Sem dúvida, vamos ter que realizar operações muito mais complexas: como  filtrar, transformar ou modificar os dados dessas listas.

Manipular coleções para produzir outros resultados é tão comum como iterar através dos elementos de uma coleção.

Vamos dizer que precisamos pegar uma lista de produtos e pegar só os preços desses produtos. 

Abaixo segue o `Record` que representará nosso produto:

*PS*: Vamos utilizar a especificação [JSR 354 Java Money](http://javamoney.github.io/)[^1]

```java
  record Product(String description, MonetaryAmount price) {}
```

E aqui está a lista de produtos:

```java
  var currency = Monetary.getCurrency("BRL");
  var products = List.of(
      new Product("bean", Money.of(5.99,currency)),
      new Product("rice", Money.of(12.49,currency)),
      new Product("coffee", Money.of(18.99,currency)),
      new Product("bread", Money.of(6.59,currency)),
      new Product("chocolate", Money.of(6.80,currency))
  );
```
Uma vez que estamos utilizando uma lista imutável criada através do método `List.of`, nós precisamos criar uma nova lista para armazenar esses preços. 

Utilizando uma abordagem imperativa, nós escreveríamos um código parecido com esse:

```java
  List<MonetaryAmount> prices = new ArrayList<>();
  for(Product product: products){
      prices.add(product.price());
  }
```
Precisamos criar uma lista vazia para só então coletar todos os preços ao iterar pelos produtos da lista. Como primeiro passo para utilizar um estilo funcional, vamos usar o *internal interator* `forEach` fornecido pela interface `Iterable`, como fizemos no [*blogpost* anterior](https://dev.to/dearrudam/pt-br-collections-foreach-lambda-expressions-o-que-sao-external-ou-internal-iterators-1e2m).

```java
  List<MonetaryAmount> prices = new ArrayList<>();
  products.forEach(product -> prices.add(product.price()));
```
Estamos utilizando um *internal iterator*, mas ainda há a necessidade de criar uma lista vazia e ainda assim adicionar os preços item a item. Que tal explorar um pouco uma abordagem mais funcional para solucionar esse detalhe?

## Muito mais que um simples iterador!

A criação de uma lista vazia e a manipulação explicita desta instância introduz variáveis mutáveis, o que deixam o nosso código mais propenso a erros, pois estamos, além de iterar pelos produtos, temos que nos preocupar em coletar só os preços e adicioná-los na nova lista: estamos instruindo nosso programa como a coleta deve ser feita ao invés de declarar qual é a informação que queremos coletar explicitamente. 

A partir do Java 8, a interface `Stream`[^2] foi adicionada com o intuito de permitir a manipulação de coleções de maneira mais declarativa, mais funcional.

A interface `Stream` é muito mais que um simples iterador: ela fornece uma API fluente na qual nos permite, em conjunto com *Lambda Expressions* e *Reference Methods*, encadear operações que podem ou não utilizar **objetos de função** afim de nos permitir executar tarefas como filtrar, mapear ou transformar, contar, reduzir e por aí vai.

Graças aos **default method**s, objetos que implementam uma `Collection` detém o método `stream` para adquirir instâncias do tipo `Stream`.

Abaixo, vamos utilizar um `Stream` a partir da lista de produtos:

```java
  List<MonetaryAmount> prices = products
                .stream()
                .map(product -> product.price)
                .collect(Collectors.toList());
```
Como podemos ver, um método chamado `map` fornecido pela interface `Stream` nos permitem mapear ou transformar cada item do `Stream` no valor solicitado, nesse caso, o preço de cada produto. No passo seguinte, instruímos o `Stream` para que, através do método `collect`, colete o resultado como uma nova lista, e isso está sendo declarado através de uma instância do tipo `Collector` fornecido através da chamada do método `toList` da classe fábrica `Collectors`.

Do ponto de vista de execução, você pode pensar que cada método, que representa uma operação, é executada de maneira **eager**, ou seja, no momento da chamada da operação, mas isso não é verdade para alguns metodos da interface `Stream`.

A interface `Stream` fornece as **operações intermediárias** e **operações terminadoras**. 

As **operações intermediárias** são operações **lazy**, isto é, elas só são performadas quando uma operação terminal é executada. 

Já as **operações terminadoras** são responsáveis por efetivamente desencadear toda as operações encadeadas através da interface fluente `Stream`. 

O encadeamento dessas operações ocorre com uma ou várias operações intermediárias em conjunto com uma operação terminal, formando assim o **Stream Pipeline**. Vamos conhecer mais sobre a interface `Stream` em blogposts futuros! :wink:

Em nosso exemplo, a operação `map` é uma operação intermediária, e ela só será disparada quando alguma operação terminal for executada, no nosso caso, a operação `collect`.

Agora, em nosso exemplo, não estamos lidando mais com váriaveis mutáveis, e assim deixando declarativo o que queremos. Não precisamos mais criar uma lista vazia. Todas essas preocupações estão sendo controladas e delegadas para a implementação fornecida pela API do Java.

Mas podemos também utilizar no lugar da *Lambda Expression* um *Method Reference*, diminuindo ainda mais a chance de algum possível erro na lógica de dentro da declaração da expressão. Vejamos como fica o resultado:

```java
  List<MonetaryAmount> prices = products
                .stream()
                .map(Product::price)
                .collect(Collectors.toList());
```
Talvez, você deve estar se perguntando: 

### Quando devemos utilizar Method References?

Nós normalmente utilizamos muito mais *lambda expressions* do que *method references* quando estamos programando em Java. Mas isso não significa que *method references* não são importantes ou menos útil. 

Se uma *lambda expression* simplesmente repassa os parâmetros para outro método, mesmo sendo um método de uma instância ou estático, podemos substitui-la pelo método em questão, referenciando-o, por isso o nome *method reference*. 

Além de deixar o código conciso do que utilizando *lambda expressions*, ao utilizar *method reference* ganhamos a habilidade de utilizar métodos nomeados, e assim facilitar a compreensão do código.

### Mas e quanto a performance?

Como já sabemos, a linguagem Java já tem um longo caminho e é usada em um grande número de aplicativos corporativos onde o desempenho é crítico. Mas mesmo sabendo disso, é muito razoável questionar se as novas features afetarão o desempenho. 

A resposta é sim, mas principalmente para melhor! Pode parecer ingênuo essa afirmação a princípio, mas antes de discutirmos sobre melhorias de desempenho, vamos lembrar as sábias palavras de Donald Knuth:

> We should forget about small efficiencies, say about 97% of the time: premature optimization is the root of all evil.

Traduzindo:

> Devemos esquecer as pequenas eficiências, digamos que cerca de 97% das vezes: a otimização prematura é a raiz de todo o mal.

Com isso em mente, devemos ser ousados em experimentar novos estilos onde eles podem fazer sentido. Se, ao utilizar o novo estilo e esse atender o desempenho adequado às necessidades da aplicação, então podemos adotá-lo e seguir em frente, caso contrário, devemos avaliar de forma crítica o design de código afim de encontrar os gargalos reais que o código está apresentando. 

As especificações inseridas a partir do Java 8 fornecem um grande flexibilidade para facilitar as otimizações do compilador. Essas otimizações em conjunto com a instrução otimizada do bytecode *InvokeDynamic* podem fazer com que as chamadas utilizando *Lambda Expressions* sejam bem rápidas.  

Vamos fazer um teste sobre performance.

Abaixo, temos um código imperativo que contam os números primos em uma coleção de números:

```java
  long count = 0;
  for (long number : numbers) {
      if (isPrime(number)) {
          count++;
      }
  }
```
Utilizamos no examplo o habitual `for loop` para invocar um método `isPrime` para determinar se cada número na coleção é um número primo. Se ele for primo, nós incrementamos a variável `count`. Vamos medir o tempo considerando uma lista de 1.000.000 (um milhão) de números.

```bash
  PT0.163687193S
```
Isso levou aproximadamente **0.163** segundos, mas vamos reduzir esse código; vamos ver ver se utilizando um novo estilo que queremos adotar bate esse desempenho. Vamos refatorar o código para o estilo funcional: onde o código é declarativo, é criado favorecendo a imutabilidade, não tem efeitos colaterais, seguindo um encadeamento de funções de primeira ordem:

```java
 numbers.stream()
         .filter(i -> isPrime(i))
         .count();
```
Aqui, transformamos a coleção em um `Stream` e então aplicamos um filtro através de uma operação intermediária, `filter`, declarando que queremos somente os números primos da coleção, e finalmente efetuamos o cálculo ao performar a operação terminal `count`. Vamos ver quanto tempo essa versão levou para executar a mesma lógica na mesma coleção de 1.000.000 (um milhão) de números:

```bash
 PT0.167082778S
```
A saída, como podemos ver, utilizando *lambda expression*, foi aproximadamente o mesmo: **0.167** segundos; Pode parecer que não ganhamos ou perdemos nada, mas na verdade, ganhamos muito sim!

É algo trivial paralelizar códigos desenvolvidos utilizando o estilo funcional, agora paralelizar um código imperativo, por outro lado, se ele já não foi arquitetado para esse fim, provavelmente não será algo tão trivial assim, não é?

Segue uma versão utilizando o estilo funcional e paralelizada de nosso código:

```java
  numbers.parallelStream()
              .filter(i -> isPrime(i))
              .count();
```
Com praticamente nenhum esforço, apenas chamando um outro *default method* chamado `parallelStream` da classe `Collection`, que também fornece uma instância de `Stream`, habilitamos o paralelismo. Vamos ver se tivemos ganho de desempenho executando esse código:

```bash
 PT0.055318673S
```
Executando essa versão paralelizada, em um processador com 8 núcleos, utilizando Java 17, levou aproximadamente **0.055** segundos para executar a tarefa.

Bom, brincadeiras à parte, antes de comemorarmos essa performance, temos que admitir que um grande número de métricas de desempenho podem ser artificiais e não podemos confiar cegamente neles. O que esse exemplo quer demonstrar nada mais é que o uso de lambda expressions e o estilo funcional não significam desempenho ruim. Sempre ao criar código real para aplicações corporativas, devemos ficar de olho no desempenho e tratar as preocupações onde elas surgirem.

### E é isso aí pessoal:thumbsup: ...

Estou curtindo demais ler o livro do renomado **Venkat Subramaniam** : **"Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expression"** [^3], então vou continuar a publicar mais blogposts com o intuido de fixar meu aprendizado e assim também ajudar quem interessar!

Espero que tenha gostado do texto! :smile:

Se gostou e achou relevante esse conteúdo, compartilhe com seus amigos.:wink:

Críticas e sugestões serão sempre bem-vindos!!!

No próximo blogpost, vamos conhecer mais sobre **Stream API**, seu funcionamento, seu pipeline, suas operações e detalhes entre outras coisas...

Até a próxima!

### Source dos exemplos [^4]:
- [GettingProductPrices.java](https://github.com/dearrudam/learning-notes/blob/main/java/GettingProductPrices.java)
- [CheckingPerformance.java](https://github.com/dearrudam/learning-notes/blob/main/java/CheckingPerformance.java)
  
### Referências:

[^1]: Estou usando [JSR 354 Java Money](http://javamoney.github.io/).
[^2]: Javadoc - [Package java.util.stream](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html)
[^3]: Livro - ["Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expression" by Venkat Subramaniam](https://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/)
[^4]: Estou usando [JBang](https://www.jbang.dev/);