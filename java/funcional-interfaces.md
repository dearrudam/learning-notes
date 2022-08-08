---
title: [PT-BR] Functional Interfaces, a fundação para a programação funcional com Java
description: 
language: PT-BR 
tags: [java,beginners,tutorial,braziliandevs]
cover_image:
series: Programação Funcional com Java
published: true
publish_date: Mon 08 Aug 2022 10:36:42 AM -03
---

No último post vimos como implementar o pattern EAM (*Execute Around Method*) no Java através das **Lambdas Expressions**, e vimos também que esse pattern utiliza um dos pilares da programação funcional chamado *Higher-order functions* para prover a sua solução. 

É através dos objetos de função podemos aplicar o pilar *Higher-order functions* no Java, pois podemos:

- Passar objetos de função como argumentos nos métodos;
- Criar objetos de função dentro dos métodos e;
- Retornar objetos de função de métodos;

Para escrever códigos que interajam com qualquer objeto, isso inclui os objetos de função, precisamos conhecer o seu tipo, o seu contrato. Comumente, utilizamos interfaces para definir esse contrato, que é onde definimos os métodos que podemos chamar das instâncias baseadas nas classes que implementam esse tal contrato. O mesmo acontece quando queremos interagir com uma váriavel, precisamos conhecer qual é o seu tipo.

Inevitavelmente, ao escrever nossos métodos precisamos definir, em suas assinaturas, os tipos dos argumentos que ele suporta e, se caso retornem algum objeto, precisamos também declarar qual é seu tipo. O mesmo se aplica a métodos que recebem ou devolvam objetos de função, isto é, será necessário declarar seu tipo, a sua interface. 

Objetos de função que são criados através de *lambda expressions* ou *method references* (sim, vamos ver isso no futuro!:wink:), devem implementar interfaces especiais, as interfaces funcionais (**Functional Interfaces**).

As interfaces funcionais são interfaces que tem um único método abstrato para ser implementado e que podem ou não ter vários métodos estáticos ou *default* methods que estão implementados na própria interface. 

Interfaces bem conhecidas `Runnable`, `Callable`, `Comparable`, entre outras que combinam com essa essa definição, são também tratadas como interfaces funcionais.

Você pode ficar atentado a criar suas interfaces funcionais, mas uma boa prática é utilizar as interfaces funcionais padrão fornecidas pelo pacote `java.util.function`[^1] uma vez que alguma atenda sua necessidade. 

As interfaces funcionais são a fundação para a utilização das *Lambdas Expressions* e *Method References*.

## Conhecendo as interfaces funcionais mais utilizadas!

A biblioteca padrão do Java tem aproximadamente **43** interfaces funcionais no pacote `java.util.function`[^1] para facilitar a vida do desenvolvedor.

Abaixo podemos conferir uma lista com **seis** interfaces básicas que frequentemente iremos encontrar e, por esse motivo, é importante ficarmos familiarizados com elas:

  - [Consumer\<T>](#consumert)
  - [Supplier\<T>](#suppliert)
  - [Predicate\<T>](#predicatet)
  - [Function\<T,R>](#functiontr)  
  - [UnaryOperator\<T>](#unaryoperatort)  
  - [BinaryOperator\<T>](#binaryoperatort)

### [TL;DR](#resumo-das-seis-interfaces-funcionais-básicas)

Há também *três* variantes para cada uma dessas *seis* interfaces funcionais básicas que operam com os seguintes tipos primitivos `int`, `long` e `double`. Essas são as *Primitive Specializations*[^2].

As *Primitive Specializations*[^2] foram adicionadas para remover a necessidade de se lidar com `autoboxing`[^3] e `unboxing`[^3] de tipos primitivos, e assim melhorar a performance da aplicação. Esses tipos não são parametrizados, exceto as variantes `Function<T,R>` que devem ser parametrizadas. Por exemplo: `LongFunction<int[]>` que recebe um `long` e retorna um `int[]`.

A interface `Function<T,R>` tem nove variantes complementares que são utilizadas quando o tipo de retorno é primitivo. Já para funções que recebem o mesmo tipo como argumento e retorno, a interface `UnaryOperator<T>` entra na cena. 

Para encontrar interfaces variantes complementares de `Function<T,R>` para retornos que resultem em primitivos, basta procurar interfaces onde o nome segue o padrão \<*Src*>To\<*Result*>Function, por exemplo, *LongToIntFunction*. Há **seis** variantes para essa condição.

Agora, para encontrar as interfaces variantes complementares de `Function<T,R>` para parâmetros do tipo primitivo porém o retorno uma referência para um objeto, basta procurar as interfaces onde o nome segue o padrão \<*Src*>ToObjFunction, por exemplo, *LongToObjFunction*. Há **três** variantes para esse tipo de interface.

Há também variações especiais para `Predicate<T>`, `Function<T,R>` e `Consumer<T>` onde ambas aceitam dois argumentos: `BiPredicate<T,U>`, `BiFunction<T,U>` e `BiConsumer<T,U>`.

`BiFunction<T,U>` também tem variantes que retornam os *três* tipos primitivos relevantes: `ToIntBiFunction<T,U>`, `ToLongBiFunction<T,U>` e `ToDoubleBiFunction<T,U>`.

Já a interface `BiConsumer<T,U>` tem variações que recebem dois argumentos, uma referência de objeto e um tipo primitivo: `ObjDoubleConsumer<T>`, `ObjLongConsumer<T>` e `ObjIntConsumer<T>`, somando no total ***nove*** versões de dois argumentos das interfaces básicas. 

Temos também a interface `BooleanSupplier`, uma variante de `Supplier<T>` que retorna um `boolean`. 

### Resumo das seis interfaces funcionais básicas

A maioria das interfaces funcionais padrões existe somente para fornecer suporte para os tipos primitivos, então:

> **Não ceda à tentação de usar as interfaces funcionais básicas com os tipos primitivos ao invés de utilizar as interfaces funcionais primitivas já existentes na biblioteca padrão. As consequências advindas da utilização do empacotamento dos tipos primitivos podem ser perigosíssimas para operações em massa.** [^4]

Segue um resumo de cada uma das *seis* interfaces funcionais básicas:
#### Consumer\<T>

Essa interface representa uma operação que vai aceitar um input do tipo `T` e não retornará nada (`void`).

|**método abstrato**|`accept`|
|-|-|
|**default method(s)**| `andThen`|
|**Uso comum**| Como parâmetro em métodos `forEach`|
|**Primitive Specializations[^2]**| `IntConsumer`, `LongConsumer`, `DoubleConsumer`,...|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)

#### Supplier\<T>

Essa interface representa um objeto fábrica que espera retornar dado objeto do tipo `T`.

|**método abstrato**|`get`|
|-|-|
|**default method(s)**| - |
|**Uso comum**| Utilizado para criar Streams infinitos preguiçosos (lazy) do tipo `T` e também utilizado como parâmetro para o método `orElseGet` da classe `Optional`|
|**Primitive Specializations[^2]**| `IntSupplier`, `LongSupplier`, `DoubleSupplier`,...|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)

#### Predicate\<T>

Essa interface define um objeto de função muito utilizado para checar se o argumento do tipo `T` satisfaz ou não alguma condição, returnando `true` ou `false`.

|**método abstrato**|`test`|
|-|-|
|**default method(s)**| `and`,`negate`, e `or` |
|**Uso comum**| Como parâmetro para métodos da classe `Stream`, como `filter` e `anyMatch`  |
|**Primitive Specializations[^2]**| `IntSupplier`, `LongSupplier`, `DoubleSupplier`,...|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)

#### Function\<T,R>

Essa interface representa uma operação de transformação, que seu intuito é aceitar um input do tipo `T` e retornar o uma instância do tipo `R`. 

|**método abstrato**|`apply`|
|-|-|
|**default method(s)**| `andThen`, `compose`|
|**Uso comum**| Como parâmetro ao método `map` da classe `Stream`|
|**Primitive Specializations[^2]**|`IntFunction`, `LongFunction`, `DoubleFunction`,`IntToDoubleFunction`,`DoubleToIntFunction`,...|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)

#### UnaryOperator\<T>

Essa interface representa uma operação que aceita um input do tipo `T` e retornar o uma instância do mesmo tipo `T`. Essa interface é uma especialização da interface `Function<T>`, isto é, ela estende a interface `Function<T>`.

|**método abstrato**|`apply`|
|-|-|
|**default method(s)**| `andThen`, `compose`|
|**Uso comum**| Quando há necessidade de transformação, enriquecimento de uma data instância do tipo `T` ou a substituição  por outra instância do mesmo tipo `T`|
|**Primitive Specializations[^2]**|`IntUnaryOperator`, `LongUnaryOperator`, `DoubleUnaryOperator`,...|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)


#### BinaryOperator\<T>

Essa interface representa uma operação de transformação, que seu intuito é aceitar dois inputs do tipo `T` e retornar o uma instância do tipo `R`. Essa interface estende a interface `BiFunction<T, U, R>`.

|**método abstrato**|`apply`|
|-|-|
|**default method(s)**| `andThen`, `compose`|
|**Uso comum**| Como parâmetro aos métodos com o nome `reduce` da classe `Stream`|
|**Primitive Specializations[^2]**|`IntBinaryOperator`, `LongBinaryOperator`, `DoubleBinaryOperator`,...|

[:arrow_left: Voltar para a lista das interfaces funcionais mais utilizadas.](#conhecendo-as-interfaces-funcionais-mais-utilizadas)


É muita informação para assimilar, porém a maior parte das interfaces funcionais já foram escritas e estão disponíveis na biblioteca padrão do Java e seus nomes foram estruturados para que não seja difícil encontrá-las quando precisarmos delas.

## Criando a sua interface funcional

Caso nenhuma dessas interfaces padrões mencionadas anteriomente satisfaça o que deseja fazer - como por exemplo, a necessidade de criar um predicado que suporte *três* argumentos ou a necessidade de ter uma interface funcional que lance exceções checadas ([veja aqui um artigo meu que fala um pouco desses tipos de exceções :wink:](https://dev.to/dearrudam/be-the-exception-dicas-para-dominar-excecoes-em-seus-codigos-java-4nlo)) -, então a criação de uma interface funcional customizada será necessária.

Outro fato também a se considerar é que terá momentos que devemos escrever nossa própria interface funcional mesmo quando alguma outra interface padrão é estruturamente idêntico. 

Quando ela, a sua possível interface funcional, apresentar requisitos fortes na qual seu nome seja relevante para o domínio do negócio que ela se propõe a resolver, talvez seja uma boa sua escrita.

Uma forma de ter certeza da necessidade de criar uma interface funcional sob medida seria quando ela assume uma ou mais das seguintes características:

- Ela está associada a um contrato forte;
- Ela será usada com frequência e poderia se beneficiar de um nome descritivo;
- Ela se beneficiaria de métodos padrões customizados;

Uma vez que decidimos em criar nossas próprias interfaces funcionais, elas precisarão seguir alguns critérios para serem consideradas interfaces funcionais válidas. 

Os critérios são:

1. Tem que ter apenas um método abstrato;
2. Pode ou não conter vários métodos estáticos;
3. Pode ou não conter vários métodos *default*;  

Outro detalhe opcional, porém muito útil é anotar essas interfaces com a anotação `@FuncionalInterface`. Além de deixar explícito o propósito para qual a interface foi criada, também instrui o compilador a reforçar a checagem para verificar se a interface realmente se qualifica como uma interface funcional.

## Interfaces funcionais - a fundação para permitir a programação funcional com Java

As interfaces funcionais são, sem dúvida, uma peça fundamental para que possamos utilizar o estilo funcional em nossos códigos Java.

Elas promovem o reuso de código e abrem a oportunidade de aplicarmos **Higher-order functions**, passando de lá para cá objetos de função definidos por *Lambda Expressions* ou por *Method References*. Esses detalhes tendem a auxiliar a criação de códigos concisos, menos propenso à erros, facilitando o entendimento, manutenção e evolução.[^5]

E é isso galera! :thumbsup:

Espero que tenha gostado do texto! :smile:

Se gostou e achou relevante esse conteúdo, compartilhe com seus amigos.:wink:

Críticas e sugestões serão sempre bem-vindos!!!

Até a próxima.

### Referências:

[^1]: [Package java.util.function](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)

[^2]: Livro:["Core Java Interview Questions You'll Most Likely Be Asked" by Vibrant Publishers](https://books.google.com.br/books?id=JGxCEAAAQBAJ&pg=PT143&lpg=PT143&dq=o+que+significa+primative+specialization+java&source=bl&ots=lwy3wb_R-j&sig=ACfU3U2rAmNfXsux1QyL53GQk1JsPBnY0A&hl=pt-BR&sa=X&ved=2ahUKEwjTp9aCvLX5AhWUALkGHb91A5gQ6AF6BAgREAM#v=onepage&q=o%20que%20significa%20primative%20specialization%20java&f=false)

[^3]: [The Java Tutorials: Autoboxing and unboxing](https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html)

[^4]: Livro:["Effective Java" - Joshua Bloch](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997/ref=sr_1_1?keywords=effective+java&qid=1657086875&s=books&sprefix=effective%2Cstripbooks%2C272&sr=1-1);

[^5]: Livro:["Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expression" by Venkat Subramaniam](https://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/)

