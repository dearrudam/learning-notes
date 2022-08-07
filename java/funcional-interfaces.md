---
title: [PT-BR] Functional Interface - as interfaces das Lambdas Expressions!
description: 
language: PT-BR 
tags: []
cover_image:
series: Programação Funcional com Java
published: true
publish_date: Sat 30 Jul 2022 01:21:46 AM -03
---

No último post vimos como implementar o pattern EAM (*Execute Around Method*) no Java através das **Lambdas Expressions**, e vimos também que esse pattern utiliza um dos pilares da programação funcional chamado *Higher-order functions* para prover a sua solução.

Vimos também que no Java, o as funções de primeira ordem (**Higher-order functions**) são os objetos de função que podemos:

- Passar como argumentos nos métodos;
- Criar dentro dos métodos e;
- Retornar de métodos;

Sabemos que, métodos precisam definir, em suas assinaturas, os tipos dos argumentos que ele suporta e, se caso retornem algum objeto, precisamos também declarar qual é seu tipo. 

O mesmo acontece quando vamos atribuir para uma variável um objeto. Para interagir com essa variável, precisaremos conhecer qual é o seu tipo.

Para ser possível criar objetos de função através de *lambdas* ou *method reference* (sim, vamos ver isso no futuro!:wink:), devemos utilizar interfaces especiais, as interfaces funcionais (**Functional Interfaces**).

As interfaces funcionais são interfaces que podem ou não ter vários métodos estáticos ou *default* methods que estão implementados na própria interface. 

Interfaces bem conhecidas `Runnable`, `Callable`, `Comparable`, entre outras que combinam com essa essa definição, são também tratadas como interfaces funcionais.

Você pode ficar atentado a criar suas interfaces funcionais, mas uma boa prática é utilizar as interfaces funcionais padrão fornecidas pelo pacote `java.util.function`[^1] uma vez que alguma atenda sua necessidade. 

Essas interfaces são a fundação essencial para a utilização das *Lambdas Expressions* e *Method References*.

Vamos explorar o conjunto de interfaces funcionais que podemos utilizar em nossas aplicações Java.


## Conhecendo as interfaces funcionais mais utilizadas!

Há aproximadamente **43** interfaces funcionais no pacote `java.util.function`[^1]. As mais utilizadas são:
  - [Function\<T,R>](#functiontr)
  - [Consumer\<T>](#consumert)
  - [Supplier\<T>](#suppliert)


### Function\<T,R>

Essa interface representa uma operação que vai aceitar um input do tipo `T` e retornará o uma instância do tipo `R`.

|**método abstrato**|`accept`|
|-|-|
|**default method(s)**| ????|
|**Uso comum**| ????|
|**Interfaces especializadas**|????|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)

### Consumer\<T>

Essa interface representa uma operação que vai aceitar um input do tipo `T` e nçao retornará nada (`void`)

|**método abstrato**|`accept`|
|-|-|
|**default method(s)**| `andThen`|
|**Uso comum**| Como parâmetro em métodos `forEach`|
|**Interfaces especializadas**| `IntConsumer`, `LongConsumer`, `DoubleConsumer`,...|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)

### Supplier\<T>

Um objeto fábrica que espera retornar um objeto do tipo `T`.

|**método abstrato**|`get`|
|-|-|
|**default method(s)**| - |
|**Uso comum**| Utilizado para criar Streams infinitos preguiçosos (lazy) do tipo `T` e também utilizado como parâmetro para o método `orElseGet` da classe `Optional`|
|**Interfaces especializadas**| `IntSupplier`, `LongSupplier`, `DoubleSupplier`,...|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)



### Referências:

[^1]: [Package java.util.function](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)