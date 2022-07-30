---
title: [PT-BR] EAM - Execute Around Method pattern utilizando Lambda Expressions
description: 
language: PT-BR 
tags: []
cover_image:
series: Programação Funcional com Java
published: false
publish_date: 
---

### Por que codar no estilo funciona?

Nos últimos artigos vimos os benefícios de maneira geral em utilizar o estilo de programação funcional, mas será realmente vantajoso começar a utilizar esse novo estilo? Deveríamos mesmo esperar grandes melhorias, ou só estamos trocando seis por meia dúzia? Pode não parecer, mas essas são questões genuínas que nós precisamos responder antes de nos comprometermos nosso tempo e esforço em considerar essa abordagem.

Olhando a sintaxe do Java, podemos ver que, apesar de muitos acharem ela verbosa até, ela é simples! E uma vez que a sintaxe se torna familiar, sua utilização se torna confortável. Isso é verdade para praticamente qualquer linguagem. Quanto mais familiar estivermos com a linguagem, será confortável trabalhar com suas APIs e frameworks.

Mas o que mais nos atrapalha é que, além do esforço de *codificar* soluções em nossas aplicações para os problemas propostos, teremos que ter um grande esforço manter essas mesmas aplicações que, provavelmente, tem ou tiveram muitas *mãos* envolvidas em seu desenvolvimento.

Temos que garantir que nós e nossos amigos programadores estejamos sempre atentos a vários pontos sensíveis da aplicação que podem ser facilmente se tornar em um pesadelo de manutenção de evolução de nossos códigos. Não estou falando só de regras de negócios, mas também de tem código que tem preocupações ortogonais que suportam essas tais regras.

Só pra relembrar, abaixo temos alguns itens que precisamos dar atenção quando estamos desenvolvendo nossos códigos:

- Será que estamos lidando de maneira correta os recursos como banco de dados ou fluxos I/O? Estamos lidando corretamente com conexões de banco de dados? Será que estamos ferando as conexões no tempo certo? Será que estamos mantendo transações ativas além do tempo necessário?
- E quanto a tratamento de exceções? Será que estamos lidando com elas nos níveis adequados? Estamos registrando nos logs as exceções de maneira correta e no nível correto?
- E quanto o processamento concorrente? Estamos adquirindo e liberando os locks de maneira adequada?
- E a lista se estende... etc...etc... 

Cada um desses itens, olhando isoladamente, podem até parecer não serem grandes problemas para se lidar. Mas as coisas mudam de figura quando eles estão combinados a complexidade inerente ao domínio que a aplicação está se propondo a resolver. Com certeza, as coisas ficarão complicadas de maneira bem rápida, exigindo um trabalho árduo de manutenção dos códigos e dificultando também a sua evolução.

E se nós encapsularmos cada uma dessas decisões em pequenos pedaços de código, onde cada pedaço gerenciasse suas restrições de maneira mais concisa? Assim, não precisaríamos gastar energia além do necessário para garantir a execução adequada desses tais códigos.

Novamente essas palavras: *código conciso*. Talvez elas pairam na cabeça de vocês assim como pairam na minha, e então, perguntas como a seguinte podem surgir:

> Será que código conciso significa apenas código enxuto?

Lendo o livro que **Venkat** [^1], encontrei uma explição bem interessante que eu gostaria de compartilhar com vocês:

> Writing code is like throwing ingredients together; making it concise is like turning that into a sauce.

Dando uma traduzida para o português:

> Escrever código é como juntar ingredientes; fazer isso de forma concisa é como transformar isso em um molho."

Mas assim como criar um molho depende de um esforço em conhecer bem como juntar todos os ingredientes, escrever códigos concisos também requerem um esforço maior para serem desenvolvidos. Assim conforme ele completa suas comparações:

> It often takes more effort to write concise code. It's less code to read, but effective code is transparent. A short code listing that's hard to understand or hides details is terse rather than concise.

Minha humilde tradução:

> Muitas vezes é preciso mais esforço para escrever um código conciso. É menos código para ler, mas código efetivo é transparente. Uma pequena lista de códigos que são difíceis de entender ou que escondem detalhes são mais enxutos do que concisos;    

**PS**: o termo *terse*, pelo que consegui encontrar na internet [^2] , significa que muitas palavras foram descartadas da frase em questão, dificultando sua compreensão, então usei o termo enxuto no contexto. **Fica aqui o apelo pela ajuda por alguém que domina inglês :pray: **

Continuando a explicação:

> Concise code equals design agility. Concise code has less ceremony. This means we can quickly try out our design ideas and move forward if they're good, or move on if they turn sour.

Traduzindo:

> Código conciso é como design ágil. Código conciso tem menos cerimônia. Isso significa que nós podemos experimentar rápidamente nossas ideias de design e seguir em frente se elas forem boas, ou mudá-las se elas "azedarem".   

Então podemos concluir que um código conciso é aquele que além de fazer o que é proposto a fazer, ele deve ser claro, sucinto, sem ambiguidades, curto porém com todas as informações necessária para sua compreensão.

Bom, com certeza não é algo fácil de se atingir... mas não desanimem! Com prática e estudo chegaremos lá! :thumbsup:!


Que tal agora codar um pouco?

### EAM - Execute Around Method pattern utilizando Lambda Expressions 

Nos textos anteriores, trouxe a seguinte questão:
> Será que estamos lidando de maneira correta os recursos como banco de dados ou fluxos I/O? 

E nos parágrafos subsequentes também trouxe o seguinte texto:

> E se nós encapsularmos cada uma dessas decisões em pequenos pedaços de código, onde cada pedaço gerenciasse suas restrições de maneira mais concisa? 

Pois bem, trabalhar com recursos externos traz alguns detalhes que são importantes, pois se não tratarmos da maneira correta, problemas como Memory Leaks podem surgir. 

O Java provê algumas opções para fechar e liberar recursos, mas acredito que podemos utilizar Lambda Expressions de uma maneira interessante para lidar com esse detalhe.

Vamos iniciar a partir de uma simples classe que lida com um recurso externo, uma classe que utiliza um FileWriter para escrever algumas mensagens:

```java
public class MessageFileWriter {

    private FileWriter writer;

    public MessageFileWriter(String filename) throws IOException {
        this.writer = new FileWriter(filename);
    }

    public void writeMessage(final String message) throws IOException {
        this.writer.write(message);
    }

    public void closeFile() throws IOException {
        this.writer.close();
    }

}
```
**TL,DR**

No construtor da classe `MessageFileWriter` inicializamos a instância `FileWriter` passando o nome do arquivo destino em que queremos escrever. Com o método `writeMessage` podemos escrever mensagens utilizando a instância do `FileWriter`. Já o método `closeFile` nós poderemos fechar o recurso chamando o método `close` da instância `FileWriter` e com isso, esperamos que as mensagens sejam descarregadas para o arquivo para só então fechá-lo.  

**END TL,DR**

Agora vamos escrever o método `main` para que possamos utilizar essa classe:

```java
    public static void main(String... args) throws IOException {
        var messageWriter = new MessageFileWriter("messages.txt");
        messageWriter.writeMessage("Hello folks!");
    }
```

Aqui, criamos uma instância da classe `MessageFileWriter` e então invocamos o método `writeMessage` passando uma `String` com o texto "Hello folks!" como a mensagem que queremos escrever, mas se executarmos esse código, nós vamos ver que o arquivo `message.txt` está em branco :scream:. O método `closeFile` nunca é chamado, assim o arquivo nunca será fechado e o conteúdo que queremos escrever nunca será descarregado da memória para o arquivo. 

Se criarmos várias instâncias em processos de longa duração (*long-running process*) nos iríamos acabar com vários arquivos abertos que não seriam fechados e todos estariam em branco!

Você pode estar pensando:

> Ah, vamos adicionar a invocação do método `closeFile` então!

Provavelmente iremos terminar um código similar como demostrado abaixo:

```java
    public static void main(String... args) throws IOException {
        var messageWriter = new MessageFileWriter("messages.txt");
        messageWriter.writeMessage("Hello folks!");
        messageWriter.closeFile();
    }
```

Apesar de aparentemente termos resolvido o problema, essa abordagem ainda demonstra alguns problemas (*code smells*) :confused::

Esse código tende a introduzir duplicação de código e assim, aumento de custo de manutenção. Isso quer dizer, em qualquer lugar que fossemos utilizar instâncias da nossa classe `MessageFileWriter`, teríamos que sempre chamar o método `closeFile` para ter certeza que o conteúdo vai ser descarregado no arquivo e o recurso fechado. E devemos concordar que o design da interface de nossa classe não ajuda a evitar esquecer esse detalhe. 

Estamos trabalhando com recursos externos, então, provavelmente exceções podem ser lançadas e se não tratadas, o recurso não será fechado e talvez a aplicação possa entrar em um estado inconsistente. 

Podemos implementar blocos `try` e `finally` em torno da chamada desses métodos e assim garantir que o método `closeFile` será chamado:

```java
    public static void main(String... args) throws IOException {
        try{
            var messageWriter = new MessageFileWriter("messages.txt");
            messageWriter.writeMessage("Hello folks!");
        }finally{   
            messageWriter.closeFile();
        }
    }
```

Poderíamos também utilizar uma feature que foi introduzida no Java 7, **ARM - Automatic Resource Management**, na qual podemos reduzir a verbosidade da versão no exemplo anterior usando o bloco `try-resources`, que é uma forma especial de utilizar o bloco `try` informando uma instâncias que implementam a interface `AutoCloseable`, logo teremos refatorar nossa classe `MessageFileWriter` para que ela implemente essa interface:

```java
public class MessageFileWriter implements AutoCloseable {

    private FileWriter writer;

    public MessageFileWriter(String filename) throws IOException {
        this.writer = new FileWriter(filename);
    }

    public void writeMessage(final String message) throws IOException {
        this.writer.write(message);
    }

    public void close() throws IOException {
        this.writer.close();
    }

}
```
E aqui aplicamos o `try-resources`:

```java
    public static void main(String... args) throws IOException {
        try (var messageWriter = new MessageFileWriter("messages.txt")) {
            messageWriter.writeMessage("Hello folks!");
        }
    }
```

Porém, ainda o problema de duplicação de código e aumento de esforço de manutenção ainda vão continuar e, toda vez que alguém mexer em alguma nessa parte que contenha esse trecho de código, será necessário verificar se nada foi quebrado com a mudança. (Testes, please :pray:!)

*ARM* foi um passo para o caminho certo, mas mesmo assim, há a necessidade de declarações adicionais para utilizar nossa classe. IMHO, ninguém deveria ter a necessidade que saber que nossa classe implementa `AutoCloseable` e que pode utilizar `try-resources` para utilizá-la. 

Como desenvolvedores, amamos quando o compilador e a IDE nos auxiliam na utilização de uma dada API, não é mesmo? 

Que tal desenhar nossa classe de tal forma a ser mais concisa e fácil de utilizar!?

## Conhecendo o pattern *Execute Around Method - EAM* 

Para nossa solução, vamos utilizar Lambda Expressions para implementar um pattern chamado **Execute Around Method - EAM** [^3], na qual pode nos fornecer um melhor controle para coordenar a execução de sequencial de operações. 

Esse padrão nos permite encapsular as operações na sequência desejada para que, a partir de uma possível **função de primeira ordem** passada como argumento, dispare a execução das operações e da função de maneira adequada.

Primeiro, vamos preparar uma classe chamada `MessageFileWriterEAM` para essa a nova solução:

```java

public class MessageFileWriterEAM  {

    private FileWriter writer;

    private MessageFileWriterEAM(String filename) throws IOException {
        this.writer = new FileWriter(filename);
    }

    public void writeMessage(final String message) throws IOException {
        this.writer.write(message);
    }

    private void closeFile() throws IOException {
        this.writer.close();
    }

}

```
Como você pode ter notado, deixamos essa classe com o construtor e o método `closeFile` privados, além de fazer com que essa classe não implemente `AutoClosable`.

Uma vez que não podemos criar diretamente uma instanciar do tipo `MessageFileWriterEAM` pelo seu construtor, nós precisaremos de um método fábrica (*Factory Method*)[^4] para poder usa-la.

Diferentemente dos métodos fábricas que criam uma instância e as devolvem para uso, nosso método vai receber um *objeto de função* do usuário, realizar a operação e só retornar ao fluxo da aplicação após o termino de seu trabalho.

Primeiramente teremos que implementar uma *interface funcional* que será o tipo de função que nosso método irá receber.

As interfaces funcionais (*Functional Interfaces*) são interfaces que contém um único método, e são cadidatos ideais para que o compilador sintetize *objetos de função* atravez de *Lambda Expressions* ou *Method References* - Sim... no futuro falaremos mais sobre Method References! :wink:

```java
    @FunctionalInterface
    public static interface UseMessageFileWriter<T, E extends Throwable> {
        void accept(T instance) throws E;
    }
```
`UseMessageFileWriter` será nossa interface funcional. Repare que anotamos a interface com `@FunctionalInterface`. Isso é puramente opcional, mas sempre é útil comunicar a intensão explicitamente para quem for utilizar nossos códigos. Assim, qualquer desenvolvedor irá compreender que é uma interface funcional e que não pode adicionar outros métodos à interface. 

Agora vamos implementar esse método:

```java
    public static void use(final String filename,
            final UseMessageFileWriter<MessageFileWriterEAM, IOException> block) throws IOException {
        final MessageFileWriterEAM messageFileWriterEAM = new MessageFileWriterEAM(filename);
        try {
            block.accept(messageFileWriterEAM);
        } finally {
            messageFileWriterEAM.closeFile();
        }
    }
```
Só um detalhe: poderíamos fazer com que a classe `MessageFilterWriteEAM` implementasse `AutoCloseable` e assim utilizar `try-resources` ao invés de usar o bloco `try` e `finally`, mas, por decisão de design, não desejamos permitir que desenvolvedores tenham acesso ao método `close` e assim abrir a possibilidade para esse método ser chamado antes da hora.

E assim podemos ver o ganho na utilização desta abordagem:

```java
    public static void main(String[] args) throws IOException {
        MessageFileWriterEAM.use(
                "messages.txt",
                writerEAM -> writerEAM.writeMessage("Hello folks!"));
    }
```

Agora, qualquer componente poderá fazer uso de instâncias de nossa classe de maneira mais concisa, sem se preocupar se há necessidade de fechar recursos ou se precisa orquestrar invocações de métodos pois todos esses detalhes estão encapsulados atrás da chamada do método. Isso abre oportunidade para evoluir o código do por trás do método `use`, como adicionar features, como por exemplo: logging.

E é isso galera!:clap::clap::clap:

Espero que tenham gostado do texto! :thumbsup:

Caso gostou e achou relevante esse conteúdo, compartilhe com seus amigos.:wink:

Se não gostou, compartilhe com seus inimigos!:smiling_imp: (okay, não foi tão boa assim a piada...)

Críticas e sugestões serão sempre bem-vindos!!!

Até a próxima.

### Source dos exemplos: [^5]
- [MessageFilterWriter.java]()
- [MessageFileWriterEAM.java]()


### Referências:

[^1]: ["Functional Programming in Java: Harnessing the Power of Java 8 Lambda Expression" by Venkat Subramaniam](https://www.amazon.com/Functional-Programming-Java-Harnessing-Expressions/dp/1937785467/)

[^2]: [The Difference Between “Concise” and “Terse”](https://writing-rag.com/4082/the-difference-between-concise-and-terse/#:~:text=Concise%20means%20you%20take%20out,Terseness%20introduces%20ambiguity.)

[^3]: [Execute Around Method](http://wiki.c2.com/?ExecuteAroundMethod)

[^4]: [Effective Java - Joshua Bloch](https://www.amazon.com/Effective-Java-Joshua-Bloch/dp/0134685997/ref=sr_1_1?keywords=effective+java&qid=1657086875&s=books&sprefix=effective%2Cstripbooks%2C272&sr=1-1);

[^5]: [JBang](https://www.jbang.dev/);