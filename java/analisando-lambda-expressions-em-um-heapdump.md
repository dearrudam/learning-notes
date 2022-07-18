---
title: [PT-BR] Analisando Lambda Expressions em um Heap-dump 
description:
language: PT-BR 
tags: []
cover_image:
series: Programação Funcional com Java
published: true
publish_date: Mon 18 Jul 2022 08:00:27 AM -03
---

É muito interessante ver o quanto podemos aprender ao compartilhar conhecimento!

Um exemplo disso são os comentários que recebi no último artigo que publiquei - ["Lambdas Expressions não são classes anônimas"](https://dev.to/dearrudam/lambda-expressions-nao-sao-classes-anonimas-5eho)! 

Agradeço a todos! 

Um desses comentários foi muito interessante, praticamente um [**mini artigo**](https://dev.to/wldomiciano/comment/2022e), onde [Wellington Domiciano](https://dev.to/wldomiciano), motivado pelo tema, pesquisou e compartilhou suas conclusões ao explorar Lambda Expressions e como a especificação transforma essas expressões lambdas em implementações de interfaces funcionais.

[João Victor Martins](https://dev.to/j_a_o_v_c_t_r) e eu até concordamos e recomendamos ao Wellington para reescrever esse *mini artigo* em um artigo propriamente dito. Essa interação foi muito massa e espero que continue sempre, pois todos nós, a comunidade, sairemos ganhando com isso!

Pois bem, uma coisa interessante sobre Lambdas Expressions é compreender como elas são implementadas.

No artigo anterior, utilizamos o utilitário *javap* para visualizar a classe (*.class) gerada a partir de um dado source com expressões lambdas e assim conferir que as lambdas são chamadas pelas das instruções **InvokeDynamic** .

Lembram do *mini artigo* do Wellington? 

Pois bem, o Wellington, com a contribuição muito massa dele através do seu comentário, demonstrou um uso muito interessante para visualizar as inner classes derivadas das lambdas e isso me despertou mais curiosidades e gostaria de trazer algo bem interessante! Valeu Wellington!

## Descarregando as inner classes derivadas das lambdas 

Baseado no *mini artigo* do Wellington, vamos compilar a seguinte classe abaixo:

 

```java
import java.util.List;
import java.util.function.Consumer;

public class CreatingLambdaExpression {

    public static void main(String... args) {

        Consumer<String> printer = (text) -> System.out.println(text);

        List.of("Wellington", "João Vitor", "Maximillian")
                .forEach(printer);

    }
}
```

Esse source se tornará no *.class abaixo:

```bash
bin/ $ tree.
.
└── CreatingLambdaExpression.class
```

E se executarmos o comando abaixo, podemos explorar um pouco mais como o cenário com a lambda foi compilada:

```bash
bin/ $ javap -c -p CreatingLambdaExpression 
Compiled from "CreatingLambdaExpression.java"
public class CreatingLambdaExpression {
  public CreatingLambdaExpression();
    Code:
       0: aload_0
       1: invokespecial #8                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String...);
    Code:
       0: invokedynamic #16,  0             // InvokeDynamic #0:accept:()Ljava/util/function/Consumer;
       5: astore_1
       6: ldc           #20                 // String Wellington
       8: ldc           #22                 // String João Vitor
      10: ldc           #24                 // String Maximillian
      12: invokestatic  #26                 // InterfaceMethod java/util/List.of:(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/List;
      15: aload_1
      16: invokeinterface #32,  2           // InterfaceMethod java/util/List.forEach:(Ljava/util/function/Consumer;)V
      21: return

  private static void lambda$0(java.lang.String);
    Code:
       0: getstatic     #44                 // Field java/lang/System.out:Ljava/io/PrintStream;
       3: aload_0
       4: invokevirtual #50                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       7: return
}
```

Mas podemos ir além, graças a essa [resposta](https://stackoverflow.com/a/47469864/3334365) compartilhada pelo Wellington, basta adicionarmos a seguinte propriedade na execução do programa:

```bash
-Djdk.internal.lambda.dumpProxyClasses=<dump directory>
```

> **P.S**: Quando utilizar essa propriedade, recomendo indicar um diretório vázio, pois a JVM irá **descarregar** além das inner classes geradas pelo seu projeto, ele também descarregará inner classes das bibliotecas de terceiros que seu projeto esteja utilizando. Isso poluirá seu diretorio e talvez, não é o que você deseja, certo?

Vamos utilizar na execução do nosso código:

```bash
bin/ $ java -Djdk.internal.lambda.dumpProxyClasses=. CreatingLambdaExpression
Wellington
João Vitor
Maximillian
```

Muito bem, o código executou como esperado, mas agora podemos ver a inner classe derivada da lambda em nosso fonte **descarregadas** no diretório bin:

```
bin/ $ tree .
.
├── CreatingLambdaExpression$$Lambda$1.class
└── CreatingLambdaExpression.class
```

E assim podemos visualizar sua classe utilizando o utilitário **javap**

```bash
bin/ $ javap -c -p CreatingLambdaExpression\$\$Lambda\$1.class 
final class CreatingLambdaExpression$$Lambda$1 implements java.util.function.Consumer {
  private CreatingLambdaExpression$$Lambda$1();
    Code:
       0: aload_0
       1: invokespecial #10                 // Method java/lang/Object."<init>":()V
       4: return

  public void accept(java.lang.Object);
    Code:
       0: aload_1
       1: checkcast     #14                 // class java/lang/String
       4: invokestatic  #20                 // Method CreatingLambdaExpression.lambda$main$0:(Ljava/lang/String;)V
       7: return
}
```

Com isso podemos concluir que: 

Quando a JVM encontra uma lambda, ele utiliza instruções ASM contidas na própria JVM para construir as *inner classes* (classes internas) necessárias. 

As classes derivadas de lambdas são geradas "on the fly" pelo componente [LambdaMetafactory](https://docs.oracle.com/javase/8/docs/api/?java/lang/invoke/LambdaMetafactory.html)[^1], isso quer dizer, em tempo de execução pela JRE. 

É legal saber que essas coisas foram desenvolvidas, mas é legal também saber o **por quê** elas foram desenvolvidas!

## Descobrindo "O POR QUÊ" 

Pesquisando um pouco mais sobre essa propriedade, cheguei a esse issue [JDK-8023524](https://bugs.openjdk.org/browse/JDK-8023524)[^2]:

![JDK-8023524](https://github.com/dearrudam/learning-notes/raw/main/java/screenshot2022-07-18_04-51-20.png)

Bom, temos o motivo bem claro aqui:

> "The lambda metafactory generates classes on the fly. For supportability and serviceability reasons, it is desirable to be able to inspect these classes..."

Dando uma traduzida para o português:

> "A lambda metafactory gera classes em tempo de execução. Por motivos de suporte e manutenção, é desejável poder inspecionar essas classes..."

Sim, suporte e manutenção!

Para explorar e ajudar o entendimento, vamos explorar um cenário: Encontrar a lambda que está causando um possível memory leak através de um heap-dump! 

## Encontrar lambdas a partir de um heap-dump

Inspirado por um issue no [StackOverflow: Finding a Java lambda from its mangled name in a heap dump](https://stackoverflow.com/questions/41570839/finding-a-java-lambda-from-its-mangled-name-in-a-heap-dump)[^3], vamos implementar nosso programa que criará um cenário pŕoximo de um memory leak, porém vamos tentar utilizar ferramentas e entender o porque foi importante a criação dessa propriedade *jdk.internal.lambda.dumpProxyClasses*:

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;

public class CollectingIntegerListFromLambdaExpressions {

    static List<IntSupplier> list;

    static IntSupplier suppliersA(Object o) {
        return () -> 0;
    }

    static IntSupplier suppliersB(Object o) {
        int h = o.hashCode();
        return () -> h;
    }

    static IntSupplier suppliersC(Object o) {
        return () -> o.hashCode();
    }

    static IntSupplier suppliersD(Object o) {
        int len = o.toString().length();
        return () -> len;
    }

    static void run() {
        Object big = new byte[10_000_000];
        list = Arrays.asList(
                suppliersA(big),
                suppliersB(big),
                suppliersC(big),
                suppliersD(big));
        System.out.println("It's done! A list of integers has been created!");
        System.out.println("the generated list of integers is : %s"
                .formatted(list.stream().map(IntSupplier::getAsInt).collect(Collectors.toList())));
    }

    public static void main(String... args) throws InterruptedException {
        run();
        System.out.println("""
            Keeping the application alive in order to let aheap dump be taken.
            Press CTRL+C to close the application.
            """);
        Thread.sleep(Long.MAX_VALUE);
    }
}
```
**TL;DR**

Basicamente, em nosso programa irá gerar uma lista de objetos de função do tipo *java.util.function.IntSupplier*, e esses serão fornecidos por métodos  através de expressões lambdas, e, a partir dessa lista, iremos gerar uma lista de inteiros e escrever seu conteúdo na saída do programa, porém, para ser possível realizar o dump da heap, a thread main ficará *dormindo* através do método *Thread.sleep*;

O problema está no método *suppliersC*, e esse método retornará uma lambda que irá manter a referência do argumento *o*, que é do tipo *Object*, que na verdade receberá a instância de um array de *bytes* com *10.000.000* posições durante a execução. 

Essas instâncias estão vinculadas a thread *main*, que pelo fato dela estar dormindo, o Garbage Collector não conseguirá remover essas instâncias da memória, causando assim um cenário de vazamento de memória.

**end TL;DR**

Bom, a compilação desse source resultou nesse *.class:

```bash
bin/ $ tree .
.
└── CollectingIntegerListFromLambdaExpressions.class
```

Então, iniciação a aplicação:

```txt
bin/ $ java CollectingIntegerListFromLambdaExpressions
It's done! A list of integers has been created!
the generated list of integers is : [0, 2124308362, 2124308362, 11]
Keeping the application alive in order to let aheap dump be taken.
Press CTRL+C to close the application.

```
E, para capturar o heap-dump, utilizei o Eclipse Memory Analyzer(MAT)[^4]:

![memory-leak-lambda.gif](https://github.com/dearrudam/learning-notes/raw/main/java/memory-leak-lambda.gif)

De acordo com a analyzer, uma instância do tipo da classe **CollectingIntegerListFromLambdaExpressions$$Lambda$3** está consumindo aproximadamente **10.000.016** bytes (**90.83%**) na memória heap.

Mas essa classe foi gerada "on the fly" a partir de alguma lambda que não temos como analizar, pois não temos acesso a ela!

Tudo bem, sabemos que o problema está na lambda que retorna do método *suppliersC(Object o)*, porém na vida real, teremos muito mais classes e métodos envolvidos, possivelmente isso causará, no mínimo, um esforço e um tempo maior para localizar esse ponto lendo os fontes. É aqui que essa propriedade pode nos ajudar!

Então vamos ativa-la e assim ver como podemos ser mais assertivos para localizar esse lambda danadinha!

```txt
bin/ $ java -Djdk.internal.lambda.dumpProxyClasses=. CollectingIntegerListFromLambdaExpressions
It's done! A list of integers has been created!
the generated list of integers is : [0, 1627674070, 1627674070, 11]
Keeping the application alive in order to let aheap dump be taken.
Press CTRL+C to close the application.
```

Muito bem, com isso já podemos ver as inner classes derivadas de nosso fonte:

```bash
bin/ $ tree .
.
├── CollectingIntegerListFromLambdaExpressions$$Lambda$1.class
├── CollectingIntegerListFromLambdaExpressions$$Lambda$2.class
├── CollectingIntegerListFromLambdaExpressions$$Lambda$3.class
├── CollectingIntegerListFromLambdaExpressions$$Lambda$4.class
├── CollectingIntegerListFromLambdaExpressions$$Lambda$5.class
├── CollectingIntegerListFromLambdaExpressions.class
└── java
    └── util
        └── stream
            ├── Collectors$$Lambda$6.class
            ├── Collectors$$Lambda$7.class
            └── Collectors$$Lambda$8.class

3 directories, 9 files
```
Como podemos ver, todas as inner classes derivadas do nosso fonte foram **descarregadas** no diretório *bin*. 

> **P.S**: Quando utilizar essa propriedade, recomendo indicar um diretório vázio, pois a JVM irá **descarregar** além das inner classes geradas pelo seu projeto, ele também descarregará inner classes das bibliotecas de terceiros que seu projeto esteja utilizando. Isso poluirá seu diretorio e talvez, não é o que você deseja, certo?

Agora, vamos visualizar a classe **CollectingIntegerListFromLambdaExpressions$$Lambda$3.class**: 

```bash
bin/ $ javap -c -p CollectingIntegerListFromLambdaExpressions\$\$Lambda\$3
final class CollectingIntegerListFromLambdaExpressions$$Lambda$3 implements java.util.function.IntSupplier {
  private final java.lang.Object arg$1;

  private CollectingIntegerListFromLambdaExpressions$$Lambda$3(java.lang.Object);
    Code:
       0: aload_0
       1: invokespecial #13                 // Method java/lang/Object."<init>":()V
       4: aload_0
       5: aload_1
       6: putfield      #15                 // Field arg$1:Ljava/lang/Object;
       9: return

  public int getAsInt();
    Code:
       0: aload_0
       1: getfield      #15                 // Field arg$1:Ljava/lang/Object;
       4: invokestatic  #23                 // Method CollectingIntegerListFromLambdaExpressions.lambda$suppliersC$2:(Ljava/lang/Object;)I
       7: ireturn
}
```

Podemos ver que essa classe recebe no construtor um argumento do tipo *java.lang.Object*, e dentro do método *getAsInt()* é possível ver uma instrução **invokestatic** que está com um comentário apontando uma chamada para o método estático **CollectingIntegerListFromLambdaExpressions.lambda$suppliersC$2**. 

Aqui já podemos ver o nome do método que instanciou o objeto que está causando o memory leak.

Mas vamos visualizar a classe **CollectingIntegerListFromLambdaExpressions.class** e ver um pouco mais de detalhes:

```bash
bin/ $ javap -c -p CollectingIntegerListFromLambdaExpressions
Compiled from "CollectingIntegerListFromLambdaExpressions.java"
public class CollectingIntegerListFromLambdaExpressions {
  static java.util.List<java.util.function.IntSupplier> list;

  public CollectingIntegerListFromLambdaExpressions();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  static java.util.function.IntSupplier suppliersA(java.lang.Object);
    Code:
       0: invokedynamic #7,  0              // InvokeDynamic #0:getAsInt:()Ljava/util/function/IntSupplier;
       5: areturn

  static java.util.function.IntSupplier suppliersB(java.lang.Object);
    Code:
       0: aload_0
       1: invokevirtual #11                 // Method java/lang/Object.hashCode:()I
       4: istore_1
       5: iload_1
       6: invokedynamic #15,  0             // InvokeDynamic #1:getAsInt:(I)Ljava/util/function/IntSupplier;
      11: areturn

  static java.util.function.IntSupplier suppliersC(java.lang.Object);
    Code:
       0: aload_0
       1: invokedynamic #18,  0             // InvokeDynamic #2:getAsInt:(Ljava/lang/Object;)Ljava/util/function/IntSupplier;
       6: areturn

  static java.util.function.IntSupplier suppliersD(java.lang.Object);
    Code:
       0: aload_0
       1: invokevirtual #21                 // Method java/lang/Object.toString:()Ljava/lang/String;
       4: invokevirtual #25                 // Method java/lang/String.length:()I
       7: istore_1
       8: iload_1
       9: invokedynamic #15,  0             // InvokeDynamic #1:getAsInt:(I)Ljava/util/function/IntSupplier;
      14: areturn

  static void run();
    Code:
       0: ldc           #30                 // int 10000000
       2: newarray       byte
       4: astore_0
       5: iconst_4
       6: anewarray     #31                 // class java/util/function/IntSupplier
       9: dup
      10: iconst_0
      11: aload_0
      12: invokestatic  #33                 // Method suppliersA:(Ljava/lang/Object;)Ljava/util/function/IntSupplier;
      15: aastore
      16: dup
      17: iconst_1
      18: aload_0
      19: invokestatic  #38                 // Method suppliersB:(Ljava/lang/Object;)Ljava/util/function/IntSupplier;
      22: aastore
      23: dup
      24: iconst_2
      25: aload_0
      26: invokestatic  #41                 // Method suppliersC:(Ljava/lang/Object;)Ljava/util/function/IntSupplier;
      29: aastore
      30: dup
      31: iconst_3
      32: aload_0
      33: invokestatic  #44                 // Method suppliersD:(Ljava/lang/Object;)Ljava/util/function/IntSupplier;
      36: aastore
      37: invokestatic  #47                 // Method java/util/Arrays.asList:([Ljava/lang/Object;)Ljava/util/List;
      40: putstatic     #53                 // Field list:Ljava/util/List;
      43: getstatic     #57                 // Field java/lang/System.out:Ljava/io/PrintStream;
      46: ldc           #63                 // String It\'s done! A list of integers has been created!
      48: invokevirtual #65                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      51: getstatic     #57                 // Field java/lang/System.out:Ljava/io/PrintStream;
      54: ldc           #71                 // String the generated list of integers is : %s
      56: iconst_1
      57: anewarray     #2                  // class java/lang/Object
      60: dup
      61: iconst_0
      62: getstatic     #53                 // Field list:Ljava/util/List;
      65: invokeinterface #73,  1           // InterfaceMethod java/util/List.stream:()Ljava/util/stream/Stream;
      70: invokedynamic #79,  0             // InvokeDynamic #3:apply:()Ljava/util/function/Function;
      75: invokeinterface #83,  2           // InterfaceMethod java/util/stream/Stream.map:(Ljava/util/function/Function;)Ljava/util/stream/Stream;
      80: invokestatic  #89                 // Method java/util/stream/Collectors.toList:()Ljava/util/stream/Collector;
      83: invokeinterface #95,  2           // InterfaceMethod java/util/stream/Stream.collect:(Ljava/util/stream/Collector;)Ljava/lang/Object;
      88: aastore
      89: invokevirtual #99                 // Method java/lang/String.formatted:([Ljava/lang/Object;)Ljava/lang/String;
      92: invokevirtual #65                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      95: return

  public static void main(java.lang.String...) throws java.lang.InterruptedException;
    Code:
       0: invokestatic  #103                // Method run:()V
       3: getstatic     #57                 // Field java/lang/System.out:Ljava/io/PrintStream;
       6: ldc           #106                // String Keeping the application alive in order to let aheap dump be taken.\nPress CTRL+C to close the application.\n
       8: invokevirtual #65                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      11: ldc2_w        #110                // long 9223372036854775807l
      14: invokestatic  #112                // Method java/lang/Thread.sleep:(J)V
      17: return

  private static int lambda$suppliersC$2(java.lang.Object);
    Code:
       0: aload_0
       1: invokevirtual #11                 // Method java/lang/Object.hashCode:()I
       4: ireturn

  private static int lambda$suppliersB$1(int);
    Code:
       0: iload_0
       1: ireturn

  private static int lambda$suppliersA$0();
    Code:
       0: iconst_0
       1: ireturn
}
```
Bom, com isso, podemos ver o poder que essa propriedade pode nos ajudar caso precisarmos enfrentar problemas parecidos com esse, na qual precisaremos analizar as inner classes geradas a partir de lambdas expressions.

Novamente, gostaria de agradecer ao Wellington por compartilhar sua experiência e espero que eu possa ter agregado a todos vocês um pouco mais com essa minha experiência e que acendam a vontade de todos para compartilhar e ajudar cada vez mais uns aos outros, como uma comunidade tem que ser!

Obrigado a tosos e até o próximo artigo!!!

### Source dos exemplos: [^5]
 - [CreatingLambdaExpression.java](https://github.com/dearrudam/learning-notes/blob/main/java/CreatingLambdaExpression.java)
 - [CollectingIntegerListFromLambdaExpressions.java](https://github.com/dearrudam/learning-notes/blob/main/java/CollectingIntegerListFromLambdaExpressions.java)


### Referências

- [**"Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expression" by Venkat Subramaniam**](https://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/)
- : Livro: [Effective Java - Joshua Bloch](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997/ref=sr_1_1?keywords=effective+java&qid=1657086875&s=books&sprefix=effective%2Cstripbooks%2C272&sr=1-1);

[^1]: [Javadoc: java.lang.invoke.LambdaMetafactory](https://docs.oracle.com/javase/8/docs/api/?java/lang/invoke/LambdaMetafactory.html)

[^2]: [Issue: JDK-8023524 - Mechanism to dump generated lambda classes / log lambda code generation](https://bugs.openjdk.org/browse/JDK-8023524)

[^3]: [StackOverflow: Finding a Java lambda from its mangled name in a heap dump](https://stackoverflow.com/questions/41570839/finding-a-java-lambda-from-its-mangled-name-in-a-heap-dump)

[^4]: [Eclipse Memory Analyzer(MAT)](https://www.eclipse.org/mat/)

[^5]: [JBang](https://www.jbang.dev/);

