---
title: [PT-BR] Iterando em coleções com Lambda Expressions... o que é external or internal iterator? 
description: 
language: PT-BR 
tags: [java,beginners,tutorial,braziliandevs]
cover_image:
series: Programação Funcional com Java
published: false
publish_date: 
---

É muito comum utilizarmos estruturas de dados para nos ajudar a atender as necessidades requeridas em nossas aplicações. Uma dessas estruturas são as *collections*. Elas são tão comuns que remover até mesmo uma pequena quantidade de cerimônia para operá-las traria um grande ganho na redução de possíveis confusões que podem aparecer em nossos códigos.

Vamos explorar como podemos utilizar as `Lambda Expressions` para manipular essas tais coleções. Com elas podemos, de uma forma declarativa, filtrar dados, realizar transformações, criar novas coleções, concatenar elementos, entre outras operações. 

## Iterando em Coleções

Iterar por meio de uma lista é uma operação básica nas coleções (`collection`), e ao longo dos anos, essa operação também sofreu mudanças significativa dentro da linguagem Java.

Vamos começar com um exemplo - enumerando uma lista de nomes - utilizando uma abordagem mais antiga e seguir evoluindo até uma versão onde a escrita dessa operação apresente uma forma mais concisa, expressiva, elegante.

Aqui criamos uma coleção imutável de lista de nomes:

```java
  final List<String> developers = List.of(
              "Maximillian",
              "Otavio Santana",
              "Bruno Souza",
              "Elder Moraes",
              "Sérgio Lopes",
              "Fernando Boaglio");
```
Abaixo, uma forma de iterar e escrever cada item no console:

```java
    for (int i = 0; i < developers.size(); i++) {
        System.out.println(developers.get(i));
    }
```
Provavelmente, alguma vez, durande a escrita de um laço `for` conforme escrito acima, vc se deparou questionando: é `i <` ou `i <=`. Essa abordagem é bem verbosa e propensa a erros, e outra, ela só é útil se precisarmos manipular elementos em um particular índice na coleção.

Uma outra forma que o Java também oferece do que o *bom e velho `for`*:

```java
    for (String developer: developers) {
        System.out.println(developer);
    }
```
Por baixo do capô, essa forma utiliza a interface `Iterator` para iterar entre os itens, chamando o método `hasNext` para saber quando parar de iterar, e o método `next` para capturar o item na posição corrente.

Nesses dois casos, são utilizados *iteradores externos* (**external iterators** [^0]). No primeiro exemplo, precisamos explicitamente controlar a iteração, indicando onde começar e onde parar; Já no segundo, essas mesmas operações acontecem *por baixo dos panos* utilizando os métodos da interface `Iterator`. E mais, através desse controle explícito, podemos utilizar as declarações `break` e `continue` para gerenciar o fluxo de controle da execução da iteração. 

Com **external iterators**, instruimos o programa **como** fazer a iteração para que, só então atingir **o quê** queremos no final das contas.

A segundo exemplo, iteramos entre os elementos da coleção com menos *cerimônia* do que a primeira versão. A de convir que essa estrutura é melhor do que a primeira somente quando não temos a intenção de modificar a coleção relacionado a um índice em particular, porém, ambas utilizam um estilo imperativo e nós podemos dispensar essa abordagem uma vez que podemos utilizar o estilo funcional.

Há boas razões a favor de mudar do estilo imperativo para o estilo funcional:

- Loops utilizando `for` são ineretentente sequenciais e são dificeis de paralelizar;
- Tais loops são ***non-polymorphic***, isto é, temos que passar a coleção na instrução do `for` ao invés de executar algum método (que pode usufruir do polimorfismo por baixo dos panos) na coleção para executar a tarefa.
- No nível de design, o princípio **"Tell, don't ask"**[^1] cai por terra! Nós solicitamos a execução de uma específica iteração ao invés de deixar esses detalhes da iteração para a biblioteca de nível mais baixo.

Dito isso, vamos utilizar o estilo funcional no lugar do imperativo, e assim utilizar uma versão de *iteração interna*. 

Com uma *iteração interna* nós deixamos a maioria das instruções de **COMO** fazer tal iteração para a biblioteca de nível mais baixo e focamos n**O QUE** queremos realizar durante a iteração. 

A interface `Iterable` foi melhorada no Java 8 com um método especial chamado `forEach`, que aceita um parâmetro do tipo `Consumer`. Como o próprio nome indica, uma instância do tipo `Consumer` irá consumir o que for passado pra ele através do seu método `accept`.

```java
  developers.forEach(new Consumer<String>() {
      @Override
      public void accept(final String developer) {
          System.out.println(developer);
      }
  });
```
Ao trocar a utilização do velho `for` pelo novo *internal iterator* [^0] `forEach` ganhamos o benefício de não necessitar focar em **COMO** iterar na coleção em questão e sim em **O QUE** fazer a cada iteração. O código aplica o princípio **Tell, don't ask** de maneira satisfatória.

> Espere um pouco, essa interface `Consumer` não é uma interface funcional!

Exato! Com isso podemos utilizar Lambda Expressions ao invés de implementar uma classe anônima! 

O método `forEach` é um método que aplica o pilar *higher-order function*, onde nos permite oferecer uma Lambda Expression ou um bloco de código que irá executar dentro do contexto de cada elemento da lista. A variável `developer` será vinculada a cada elemento da coleção durante sua chamada. 

Assim, a implementação por baixo dos panos deste método terá o controle de como iterar e como executar o objeto de função recebido como argumento e, também porá decidir se a execução deve ser *preguiçosa* (**lazy**), ou em qual será a ordem da iteração, e explorar o paralelismo como achar melhor.

```java
  developers.forEach((final String developer) -> System.out.println(developer));
```
A sintaxe padrão de Lambda Expressions espera que os parâmetros estejam junto com seu tipo definido entre parênteses e separado por vírgulas, mas o compilador Java também oferece a *inferência de tipos* [^2] [^3] [^4].

Baseado na assinatura do método da interface que a Lambda Expression está implementando, o compilador é capaz de detectar qual é o tipo do parâmetro em questão e efetuar sua inferência.

Vamos usufruir da inferência de tipos em nosso exemplo tirando a declaração:

```java
  developers.forEach((developer) -> System.out.println(developer));
```
Assim, baseado no contexto do método, o compilador sabe determinar o tipo do parâmetro que está sendo fornecido.

Para casos onde há multiplos parâmetros, podemos seguir o mesmo princípio, não declarar o tipo de cada parâmetro, mas se precisarmos especificar o tipo de um parâmetro, precisaremos especificar o tipo de todos os parâmentros, isto é, ou declara nenhum ou declara todos os tipos de cada parâmetros.

Para casos onde só há um parâmetro, o compilador Java não exige que o parâmetro esteja dentro de parenteses. 

```java
  developers.forEach(developer -> System.out.println(developer));
```

**Mas uma resalva:** parâmetros inferidos são **non-final**. Em um exemplo anterior que escrevemos uma *Lambda Expression* que estamos definimos explicitamente o tipo do parâmetro, nós também definimos o parâmetro como `final`. Isso instrui o compilador a nos alertar caso o parâmetro for modificado dentro da *Lambda Expression*. De modo geral, modificar parâmetros é algo ruim que pode conduzir a erros, então defini-los com `final` é uma boa prática.

Infelizmente, quando favorecemos a inferência de tipos na declaração dos parâmetros em uma Lambda Expressions, temos que ter uma disciplina extra em não modificar os parâmetros, pois o compilador não poderá nos ajudar nesses casos. 

## Reduzindo código com Method References

Vimos até agora exemplos com Lambda Expressions, porém há mais um passo que podemos dar para deixar o codigo mais conciso:

```java
  developers.forEach(System.out::println);
```

No último código de exemplo nós usamos um ***Method Reference***. O Java nos deixa, de maneira simples, substituir o corpo de código com um método nomeado de nossa escolha. Vamos olhar com mais detalhes sobre *Method Reference* em artigos futuros, no worries! :wink:

Como não existe **bala de prata**, utilizar `forEach` também tem suas limitações. Uma vez que começa o método, diferentemente das versões que utilizam `for`, a iteração não podem ser interrompidas. Como consequencia, esse estilo é útil em casos comuns onde nós queremos processar cada elemento de uma coleção.  

No próximo artigo, vamos ver como `Lambda Expressions` podem nos ajudar a lidar com a mutabilidade e deixar nosso código mais conciso durante operações de transformação com coleções...spoiler: **Streams API :rocket: !!!**

Obrigado a todos e até o próximo artigo!!!

### Source dos exemplos [^5]:
 - [Iteration.java](https://github.com/dearrudam/learning-notes/blob/main/java/Iteration.java)


### Referências:

[^0]: Livro:["Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expression" by Venkat Subramaniam](https://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/)

[^1]: [**Tell-Don't-Ask Principle** by Martin Fowler](https://martinfowler.com/bliki/TellDontAsk.html#:~:text=Tell%2DDon't%2DAsk,an%20object%20what%20to%20do.)

[^2]: [JEP 323: Local-Variable Syntax for Lambda Parameters](https://openjdk.org/jeps/323)

[^3]: [JEP 286: Local-Variable Type Inference](https://openjdk.org/jeps/286)

[^4]: [Arquivo: Java 9 na prática: Inferência de tipos](https://www.alura.com.br/artigos/java-9-na-pratica-inferencia-de-tipos)

[^5]: [JBang](https://www.jbang.dev/)
