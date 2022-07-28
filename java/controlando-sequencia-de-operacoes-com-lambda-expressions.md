---
title: [PT-BR] Controlando sequência de operações com Lambda Expressions!
description: 
language: PT-BR 
tags: []
cover_image:
series: Programação Funcional com Java
published: false
publish_date: 
---

## Por que codar no estilo funciona?

Nos últimos artigos vimos os benefícios de maneira geral em utilizar o estilo de programação funcional, mas será realmente vantajoso começar a utilizar esse novo estilo? Deveríamos mesmo esperar grandes melhorias, ou só estamos trocando seis por meia dúzia? Pode não parecer, mas essas são questões genuínas que nós precisamos responder antes de nos comprometermos nosso tempo e esforço em considerar essa abordagem.

Olhando a sintaxe do Java, podemos ver que ela pode até ser verbosa, mas ela é simples! Para nós "Javeiros", a sintaxe se tornou bem familiar e até confortável quando utilizamos os seus frameworks e suas APIs. 

O que realmente nos atrapalha é o esforço requerido para codificar e manter aplicações corporativas típicas na qual desenvolvemos com Java. Para explicar melhor, olhe só alguns detalhes que normalmente precisamos dar atenção:

- Será que os nossos amigos Javeiros estão lidando corretamente com conexões de banco de dados? Será que eles fecham as conexões no tempo certo? Será que eles não estão mantendo transações ativas além do tempo necessário?
- E quanto a tratamento de exceções? Eles estão lidando com elas nos níveis adequados? Eles estão registrando nos logs as exceções de maneira correta e no nível correto?
- E quanto o processamento concorrente? Será que eles estão adquirindo e liberando os locks de maneira adequada?
- E a lista se estende... etc...etc... 

Cada um desses itens, olhando isoladamente, podem até parecer não serem grandes problemas para se lidar. Mas as coisas mudam de figura rapidamente quando eles estão combinados a complexidade inerente ao domínio que a aplicação está procurando resolver. As coisas provavelmente ficarão complicadas de maneira bem rápida, exigindo um trabalho árduo de manutenção dos códigos e dificultando também a sua evolução.

Para facilitar o desenvolvimento, melhorando o gerenciamento desses detalhes e também proporcionando uma diminuição da necessidade de ficar colocando esforços para garantir que essas decisões sejam aplicadas de maneira correta, podemos então encapsular essas decisões em partes pequenas de código afim de facilitarmos seu gerenciamento e manutenção é uma abordagem satisfatória que trará um grande benefício, e é aqui que o estilo funcional pode nos ajudar, trazer meios para implementarmos essas técnicas de maneira concisa.

Código conciso... essas palavras talvez pairam na cabeça de vocês assim como pairávam na minha. Então me perguntava: será que um código conciso significa apenas um código enxuto?

**Venkat** traz uma explição bem interessante que eu gostaria de compartilhar com vocês:

> Writing code is like throwing ingredients together; making it concise is like turning that into a sauce.

Dando uma traduzida para o português:

> Escrever código é como juntar ingredientes; fazer isso de forma concisa é como transformar isso em um molho."

Mas assim como criar um molho depende de um esforço em conhecer bem como juntar todos os ingredientes, escrever códigos concisos também requerem um esforço maior para serem desenvolvidos. Assim conforme ele completa suas comparações:

> It often takes more effort to write concise code. It's less code to read, but effective code is transparent. A short code listing that's hard to understand or hides details is terse rather than concise.

Minha humilde tradução:

> Muitas vezes é preciso mais esforço para escrever um código conciso. É menos código para ler, mas código efetivo é transparente. Uma pequena lista de códigos que são difíceis de entender ou que escondem detalhes são mais enxutos do que concisos;    

**PS**: o termo *terse*, pelo que consegui encontrar na internet [^1] , significa que muitas palavras foram descartadas da frase em questão, dificultando sua compreensão. **Fica aqui o apelo pela ajuda por alguém que domina inglês :pray: **

Continuando a explicação:

> Concise code equals design agility. Concise code has less ceremony. This means we can quickly try out our design ideas and move forward if they're good, or move on if they turn sour.

Traduzindo:

> Código conciso é como design ágil. Código conciso tem menos cerimônia. Isso significa que nós podemos experimentar rápidamente nossas ideias de design e seguir em frente se elas forem boas, ou mudá-las se elas "azedarem".   

Então podemos concluir que um código conciso é aquele que além de fazer o que é proposto a fazer, ele deve ser claro, sucinto, sem ambiguidades, curto porém com todas as informações necessária para sua compreensão.

Bom, com certeza não é algo fácil de se atingir... mas não desanimem hein!?


Bom... dado alguns detalhes, bora codar um pouco:

## Controlando sequência de operações com Lambda Expressions!

 





[^1]: [The Difference Between “Concise” and “Terse”](https://writing-rag.com/4082/the-difference-between-concise-and-terse/#:~:text=Concise%20means%20you%20take%20out,Terseness%20introduces%20ambiguity.)