---
title: Estilo Imperativo X Estilo Declarativo
description:
language: 
tags: []
cover_image:
series: Programação Funcional com Java
published: false
publish_date: 
---
# Estilo Imperativo X Estilo Declarativo com Java

Estou lendo um livro muito legal do renomado **Venkat Subramaniam** : [**"Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expression"**](https://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/) e por estar gostando muito, decidi publicar posts com o intuido de fixar meu aprendizado e assim também ajudar quem interessar!


## Mude a sua forma de pensar ao escrever seus códigos

Desde sua concepção, o Java tem fornecido uma forma imperativa para escrevermos nossos códigos, isto é: dizemos ao Java, passo a passo, o que queremos que ele faça e assim ter o resultado esperado. Essa forma tem funcionado, porém códigos imperativos tendem a serem verbosos e difíceis de manter. Para melhor descrever esse cenário, vamos programar um pouco! :)

Vamos dizer que temos uma certa necessidade em nossa regra de negócio: precisamos verificar se a cidade "São Paulo" faz parte de uma lista de cidades fornecida, para que assim possamos efetuar outras regras de negócios...

Pois bem, talvez você tenha imaginado construir um código similar conforme abaixo:

```java
    boolean found = false;

    for (String city : cities) {
        if ("São Paulo".equals(city)) {
            found = true;
            break;
        }
    }

    System.out.println("Found São Paulo? " + found);
```

No código acima, podemos ver um forma imperativa de busca para verificar se *São Paulo* está contido na coleção *cities*. 

Mas o que é utilizar o estilo imperativo?

**Estilo Imperativo - descrevemos explicitamente COMO o programa deve fazer suas tarefas especificando cada instrução passo a passo.**

Apesar de simples, é um código nada intuitivo de se **ler** e **compreender**; há variáves que, apesar de serem necessárias para computar nossa busca, acabam ofuscando o que realmente o código tem a intenção de executar, deixando-o distante da linguagem de negócio. 

Primeiramente, definimos a varíavel boleana nomeada **"found"** que será nossa flag que dirá se encontramos ou não a cidade, e então iteramos entre os elementos da coleção **cities**. Se encontrarmos a cidade que estamos procurando então definimos a flag como **"true"** e paramos a iteração na lista. Daí então, escrevemos na saída padrão o resultado de nossa busca.

Além de cansativo, traz muitos detalhes de implementação que ofuscam não acham!?

Como desbravadores da linguagem Java, no minuto que olhamos esse código nós rapidamente pensaremos em uma construção mais concisa e fácil de ler, ao parecido com isso:

```java
    System.out.println("Found São Paulo?:" + cities.contains("São Paulo"));
```

Que diferença não é!?

Isso é um exemplo do estilo declarativo: agora podemos rapidamente compreender a intenção do código para o negócio de maneira mais concisa e direta.

**Estilo Declarativo - descrevemos O QUE o programa deve fazer sem explicitamente dizer COMO ele deve fazer.**

Outras melhorias que podemos perceber em nosso código com essa abordagem:

- Não há bagunça em torno do código com variaveis mutáveis;
- A iteração na lista ocorrem "debaixo do capô";
- Menos desordem;
- Mais clareza; Foco na regra de negócio;
- Menos impedância: o código segue a intenção do negócio;
- Menos propenso a erros;
- Maior facilidade de entendimento e manutenção;

Bom, concordo que estamos olhando um exemplo simples - uma método para checar se um elemento está presente em uma coleção já está faz tempo no Java.

Mas agora, conhecendo esses benefícios na utilização desse estilo **"declarativo"**, por que não utilizar essa abordagem para não escrever códigos imperativos para operações mais avançadas como por exemplo: processamento com dados complexos (oriundos de fontes externas: banco de dados, web, ou arquivos), programação concorrente, etc.? 

Calma, mostre-me mais algum exemplo mais avançado!

## Além de casos simples

Vamos olhar um outro exemplo:

Vamos definir uma coleção de preços e vamos desenvolver algumas formas para calcular descontos [^1]:

```java

    var currency = Monetary.getCurrency("BRL");

    final var prices = Arrays.asList(
            Money.of(10, currency),
            Money.of(30, currency),
            Money.of(17, currency),
            Money.of(20, currency),
            Money.of(15, currency),
            Money.of(18, currency),
            Money.of(45, currency),
            Money.of(12, currency));

```
Vamos dizer que queremos o total de preços que sejam maiores que BRL 18.00, aplicando 15% de desconto.

### Uma forma habitual

Em uma forma habitual, chegaríamos um código como o abaixo:

```java

    var totalOfDiscountedPrices = Money.of(0, currency);

    for (MonetaryAmount price : prices) {
        if (price.compareTo(Money.of(18, currency)) > 0) {
            totalOfDiscountedPrices = totalOfDiscountedPrices.add(price.multiply(0.85));
        }
    }

    System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);

```

Apesar de ser um código bem familiar, é interessante analisá-lo:
- Primairamente iniciamos a variável para mantermos o total dos preços com desconto;
- Iteramos na coleção de preços, pegando os preços que forem maior que BRL 18, e para cada preço encontrado, computamos o preço com desconto e adicionamos ao valor total;
- E para terminar, escrevemos o total dos preços com desconto na saída da aplicação.


Aqui está a saída que o código produziu:

```txt
    Total of discounted prices: BRL 80.75
```

Funcionou conforme esperado, mas a sensação de sugeira ao escrever esse código com certeza veio a tona. Muitos de nós (incluindo eu, é claro) aprendemos assim e utilizavamos o que podíamos de acordo com a ferramentas que temos. Se olharmos esse código, ele está cuidando de coisas bem baixo nível perto do que o negócio em si pede. 

Códigos como esse sofre de "Primitive Obsession" [^2] além de desafiarem o princípio de responsabilidade única do "SOLID" (listamos, filtramos e calculamos em um só lugar - multipla responsabilidade).

### Bom, podemos fazer melhor...realmente bem melhor! 

Olhando com calma, podemos deixar o nosso código mais próximo da necessidade do negócio, respeitando o requerimento especificado e, diminuindo assim chances de má interpretação.

Vamos evitar criar variáveis mutáveis e manipulá-las atribuindo valores e vamos trazer as coisas para um nível maior de abstração, assim como no código abaixo:

```java
    var totalOfDiscountedPrices = prices
            .stream()
            .filter(price -> price.compareTo(Money.of(18, currency)) > 0)
            .map(price -> price.multiply(0.85))
            .reduce(Money.of(0, currency), Money::add);

    System.out.println("Total of discounted prices: " + totalOfDiscountedPrices);
```

E ao executar esse código, temos a mesma saída conforme mostrado no exemplo imperativo:

```txt
    Total of discounted prices: BRL 80.75 
```

Como em nosso primeiro exemplo, nós reduzimos longas linhas de código com um encadeiamento de métodos a partir do método *stream()* a partir da lista de preços. 
O código está conciso, nós conseguimos até ler em voz alta e assim perceber o quanto próximo da especificação do requerimento estamos:

- *filtrar os preços que foram maior que BRL 18.00;*
- *mapeie o preço atual para um novo preço aplicando o desconto de 15% e...*
- *reduza os preços com desconto somando os e armazenando na variável **totalOfDiscountedPrices***. 

A utilização do método *stream()* fornecido pelas Collections do Java trazem um tipo especial de iterator com uma variedade de funções especiais que poderemos trazer em próximos posts!

Ao invés de explicitamente iterar entre os preços na lista (de maneira imperativa), nós delegamos essa iteração para a implementação interna do iterator e utilizamos alguns métodos especiais, como o *filter*, onde definimos o a condição do filtro, e o *map*, onde dado um valor podemos transformaço em outro, e assim conseguir o comportamento desejado de forma **declarativa**:

Novamente, podemos confirmar essas melhorias:

### Melhorias utilizando um estilo declarativo

- Não há bagunça em torno do código com variaveis mutáveis;
- A iteração na lista ocorrem "debaixo do capô";
- Menos desordem;
- Mais clareza; Foco na regra de negócio;
- Menos impedância: o código segue a intenção do negócio;
- Menos propenso a erros;
- Maior facilidade de entendimento e manutenção;

Com isso, compreendo o porque é interessante repensar nossa maneira de programar e procurar utilizar o estilo declarativo em nossos códigos.

Estou lendo um livro muito legal do renomado **Venkat Subramaniam** : [**"Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expression"**](https://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/) e por estar gostando muito, decidi publicar posts com o intuido de fixar meu aprendizado e assim também ajudar quem interessar!


Source dos exemplos [^3]:

  -  https://github.com/dearrudam/learning-notes/blob/main/java/ImperativeSample01.java
  -  https://github.com/dearrudam/learning-notes/blob/main/java/DeclarativeSample01.java
    
  -  https://github.com/dearrudam/learning-notes/blob/main/java/ImperativeDiscount.java
  -  https://github.com/dearrudam/learning-notes/blob/main/java/DeclarativeDiscount.java
    

[^1]: Estou usando [JSR 354 Java Money](http://javamoney.github.io/).
[^2]: Primitive Obsession [PT-BR](https://refactoring.guru/pt-br/smells/primitive-obsession) ou [EN-US](https://refactoring.guru/smells/primitive-obsession) 
[^3]: Utilizei [JBang](https://www.jbang.dev/); 

