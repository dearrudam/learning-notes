---
title: Lambda Expressions não são classes anônimas?
description:
language: PT-BR 
tags: []
cover_image:
series: Programação Funcional com Java
published: false
publish_date: 
---
Seguindo essa série falando um pouco sofre **"Programação Funcional com Java"**, gostaria de trazer algo interessante:
# Lambda Expressions não são classes anônimas?

Com Java 8, muitas features, como Lambdas Expression, Streams API e Method References nos permitiram a desenvolver com Java de uma maneira diferente, uma maneira **funcional**. Estamos no Java 18, com o Java 19 batendo em nossa porta, com previsão para setembro/2022, e algumas questões sobre essas features, que apesar de não serem tão novas, pairam em nossas cabeças. 

Talvez o óbvio pra mim, não seja óbvio para vocês e vice-versa!

Nesse artigo, vamos conversar um pouco sobre Lambda Expression!

## Objetos de Função

Antes dessas novas features introduzidas pelo Java 8, as interfaces (ou, raramente, as classes abstratas) com um único método abstrato eram utilizadas para representar *tipos de função*. As instâncias dessas classes são conhecidas como *objetos de função*, e através dessas implementações, tinhamos uma forma para representar funções ou ações em nossas aplicações.[^0]

Desde o JDK 1.1, o principal meio para criar objetos de função era por *classes anônimas*. 
```java
    JButton button = new JButton("Click!");
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            // that's a nostalgic thing, do ya!?
        }
    });
```
Quem nunca implementou algo assim utilizando a API Swing do Java? Tudo bem, este código é nostálgico, talvez ele traga lembranças não tão agradáveis, então, brincadeiras de lado, vamos olhar outro fragmento de código onde precisamos ordenar palavras pelo comprimento:

```java
    Collections.sort(words, new Comparator<String>() {
        public int compare(String word1, String word2) {
            return Integer.compare(word1.length(), word2.length());
        }
    });
```
Aqui, instanciamos uma classe anônima e implementamos a lógica exigida pela interface comparator onde, se retornar -1, significa que o comprimento do primeiro argumento nomeado como *word1*, é menor que o comprimento do segundo argumento nomeado como *word2*, se retornar 0 significa que ambos argumentos tem o mesmo comprimento e, finalmente, se retornar 1 significa que o comprimento do argumento *word1* é maior que o argumento *word2*. (Desculpem-me, mas sempre tive que consultar o javadoc para implementar essa lógica, ela sempre me confundia... :P !)

Classes anônimas eram adequadas aos padrões clássicos quando eram exigidos a criação de *objetos de função*, lembram do design pattern Strategy [^1]?

Mas toda essa verbosidade exigida na utilização das classes anônimas fazia com a programação funcional se tornasse uma possibilidade bem desagradável.

Pois bem, com o Lambda expressions podemos trazer melhorias significativas a esse código:

```java
    Collections.sort(words, 
            (word1, word2) -> Integer.compare(word1.length(), word2.length()));
```
Todo aquela verbosidade - instanciação e declaração da classe anônima com parenteses e assinatura do método -  desapareceu e o comportamento ficou um pouco mais evidente. Agradeçam as expressões lambdas!

## Mas o que são expressões lambdas?

No Java 8, interfaces com um único método abstrato agora recebem um tratamento especial. A linguagem agora permite que expressões lambdas, ou apenas lambdas (para abreviar) implemente objetos de função que representam essas interfaces, que agora essas podem ser chamadas de *Interfaces Funcionais* - sim \o/... podemos falar mais sobre interfaces funcionais em um futuro próximo.

Então podemos dizer que expressão lambdas são funções anônimas que podemos adicionar em nossos códigos afim de representar um objeto de função.

Você pode estar agora se convencendo que, debaixo do capô, as expressões lambdas são convertidas para classes anônimas, não é?

É agora que a coisa fica interessante!

Olhem esse código:

```java
public class UsingAnonymousClasses {

    public static void main(String... args) {

        Thread th;

        th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread [%s] is doing A...".formatted(Thread.currentThread().getName()));
            }
        });

        th.start();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread [%s] is doing B...".formatted(Thread.currentThread().getName()));
            }
        });

        th.start();
        th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread [%s] is doing C...".formatted(Thread.currentThread().getName()));
            }
        });

        th.start();
        
    }
}
```
Por ora, ignore a orquestração da execução das threads, ok ?

Neste código, podemos ver que implementamos 3 (três) classes anônimas com comportamentos distintos.

Para desencargo de consciência, vamos listar as classes compiladas a partir deste código:

```bash
bin/ $ tree .
.
├── UsingAnonymousClasses$1.class
├── UsingAnonymousClasses$2.class
├── UsingAnonymousClasses$3.class
└── UsingAnonymousClasses.class
```

Agora vamos criar uma versão e utilizar lambdas ao invés de classes anônimas:

```java
public class UsingLambdaExpressions {

    public static void main(String... args) {
        Thread th;
        
        th = new Thread(() -> System.out.println("thread [%s] is doing A...".formatted(Thread.currentThread().getName())));
        th.start();

        th = new Thread(() -> System.out.println("thread [%s] is doing B...".formatted(Thread.currentThread().getName())));
        th.start();
        
        th = new Thread(() -> System.out.println("thread [%s] is doing C...".formatted(Thread.currentThread().getName())));
        th.start();        
    }
}
```
Muito bem, vamos então conferir as classes compiladas a partir desta nova versão de código:

```bash
bin/ $ tree .
.
└── UsingLambdaExpressions.class
```

Como podemos ver, na verdade, as expressões lambdas não são convertidas para classes anônimas, logo, elas não são classes anônimas!!!

## Mas como elas são implementadas afinal!?

Utilizando o utilitário *javap* podemos dar uma espiada no bytecode gerado:

```bash
bin/ $ javap -c UsingLambdaExpressions.class 
Compiled from "UsingLambdaExpressions.java"
public class UsingLambdaExpressions {
  public UsingLambdaExpressions();
    Code:
       0: aload_0
       1: invokespecial #8                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String...);
    Code:
       0: new           #16                 // class java/lang/Thread
       3: dup
       4: invokedynamic #18,  0             // InvokeDynamic #0:run:()Ljava/lang/Runnable;
       9: invokespecial #22                 // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
      12: astore_1
      13: aload_1
      14: invokevirtual #25                 // Method java/lang/Thread.start:()V
      17: new           #16                 // class java/lang/Thread
      20: dup
      21: invokedynamic #28,  0             // InvokeDynamic #1:run:()Ljava/lang/Runnable;
      26: invokespecial #22                 // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
      29: astore_1
      30: aload_1
      31: invokevirtual #25                 // Method java/lang/Thread.start:()V
      34: new           #16                 // class java/lang/Thread
      37: dup
      38: invokedynamic #29,  0             // InvokeDynamic #2:run:()Ljava/lang/Runnable;
      43: invokespecial #22                 // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
      46: astore_1
      47: aload_1
      48: invokevirtual #25                 // Method java/lang/Thread.start:()V
      51: return
}
```
Curiosamente, não precisamos ter tanto conhecimento para interpretar bytecodes em java para conseguir ver que as expressões lambdas se tornaram instruções chamadas **InvokeDynamic**. 

Mas e o corpo das lambdas?! 

Dependendo do contexto, o compilador irá converter o corpo das lambdas em um desses possiveis casos:
  - Ou em métodos estáticos (static methods) da própria classe 
  - Ou métodos vinculado a instâncias definidas (instance methods) 
  - Ou simplesmente rotear a chamada para métodos existente em uma outras classes.

Interessante não é!? 

Bom, agradeço e muito ao **"Venkat Subramaniam"**, pois em uma palestra que ele apresentou no **Devoxx** em 2015 me ensinou muito! Super recomendo! Segue o link da palestra: [Get a Taste of Lambdas and Get Addicted to Streams by Venkat Subramaniam](https://www.youtube.com/watch?v=1OpAgZvYXLQ).

Até o próximo artigo!!!

### Source dos exemplos [^3]:
 - [UsingAnonymousClasses.java](https://github.com/dearrudam/learning-notes/blob/main/java/UsingAnonymousClasses.java)
 - [UsingLambdaExpressions.java](https://github.com/dearrudam/learning-notes/blob/main/java/UsingLambdaExpressions.java)

### Referências:

[^0]: Livro: [Effective Java - Joshua Bloch](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997/ref=sr_1_1?keywords=effective+java&qid=1657086875&s=books&sprefix=effective%2Cstripbooks%2C272&sr=1-1);
[^1]: Desing Patterns - Strategy [[PT-BR]](https://refactoring.guru/pt-br/design-patterns/strategy)/[[EN-US]](https://refactoring.guru/design-patterns/strategy) | Livro: [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612/ref=sr_1_1?crid=WNFJ58ETWC0D&keywords=design+patterns&qid=1657081268&sprefix=design+patterns%2Caps%2C266&sr=8-1)
[^3]: [JBang](https://www.jbang.dev/);