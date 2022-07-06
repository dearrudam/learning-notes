---
title: Lamba Expression não são classes anônimas?
description:
language: PT-BR 
tags: []
cover_image:
series: Programação Funcional com Java
published: false
publish_date: 
---
# Lamba Expression não são classes anônimas?

Com Java 8, muitas features, como Lambdas Expression, Streams API e Method References nos permitiram a desenvolver com Java de uma maneira diferente, uma maneira **funcional**. Estamos no Java 18, com o Java 19 batendo em nossa porta, com previsão para setembro/2022, e algumas questões sobre essas features, que apesar de não serem tão novas, pairam em nossas cabeças. 

Nesse artigo, vamos conversar um pouco sobre Lambda Expression!

## Objetos de Função

Antes dessas novas features introduzidas pelo Java 8, as interfaces (ou, raramente, as classes abstratas) com um único método abstrato eram utilizadas para representar *tipos de função*. As instâncias dessas classes são conhecidas como *objetos de função*, e através dessas implementações, tinhamos uma forma para representar funções ou ações em nossas aplicações. 

Desde o JDK 1.1, o principal meio para criar objetos de função era por *classes anônimas*. Quem nunca implementou algo assim utilizando a API Swing do Java:

```java
    JButton button = new JButton("Click!");
    button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent event) {
            // that's a nostalgic thing, do ya!?
        }
    });
```
Tudo bem, este código é nostálgico, talvez ele traga lembranças não tão agradáveis, então, brincadeiras de lado, vamos olhar outro fragmento de código onde precisamos ordenar palavras pelo comprimento:

```java
    Collections.sort(words, new Comparator<String>() {
        public int compare(String word1, String word2) {
            return Integer.compare(word1.length(), word2.length());
        }
    });
```
Aqui, instanciamos uma classe anônima e implementamos a lógica exigida pela interface comparator onde, se retornar -1, significa que o comprimento do primeiro argumento nomeado como *word1*, é menor que o comprimento do segundo argumento nomeado como *word2*, se retornar 0 significa que ambos argumentos tem o mesmo comprimento e, finalmente, se retornar 1 significa que o comprimento do argumento *word1* é maior que o argumento *word2*. (Ok, desculpe explicar a especificação da interface, mas sempre tive que consultar o javadoc, pois sempre me confundia na implementação... :) quem nunca não é?)

Bom, classes anônimas eram adequadas aos padrões clássicos do design orientados a objetos quando eram exigidos a criação de *objetos de função*, lembram do design pattern Strategy [^1]. 



[^1]: Desing Patterns - Strategy [[PT-BR]](https://refactoring.guru/pt-br/design-patterns/strategy)/[[EN-US]](https://refactoring.guru/design-patterns/strategy) | Livro: [Design Patterns: Elements of Reusable Object-Oriented Software](https://www.amazon.com/Design-Patterns-Elements-Reusable-Object-Oriented/dp/0201633612/ref=sr_1_1?crid=WNFJ58ETWC0D&keywords=design+patterns&qid=1657081268&sprefix=design+patterns%2Caps%2C266&sr=8-1)
