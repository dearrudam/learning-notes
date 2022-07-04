---
title: Dicas para dominar exceções em seus códigos Java
description: 
tags: [Java, Exceptions ]
language: PT-BR
cover_image:
series: 
published: true
publish_date: 
---
# Dicas para dominar exceções em seus códigos Java


Esse artigo foi a base da palestra que apresentei no 1o. **[SouJava Lightning Talks](https://www.youtube.com/watch?v=R-3v90oM0TI&t=1808s)** então fique a vontade de conferir, okay?! 

Você já se perguntou: 

- Como será que devo criar as exceções para minha as minhas regras de negócio?
- Será que não estou criando exceções demais?
- Em relação às exceções das regras de negócios que estou criando: devo extender de RuntimeException ou de Exception?
- E quanto a lidar com o tratamento de exceções:
- E as mensagem de erro, estou sendo claro nelas?
- será que estou logando de maneira adequada a captura de exceções em meus códigos?
- Será que estou "silenciando" uma exceção de maneira equivocada?
- Será que estou desenvolvendo meus códigos "orientados à exceções"?

É comum desenvolvedores Java enfrentarem esses dilemas quando se há a necessidade de desenvolver ou lidar com exceções em seus códigos. 

Uns lidam com exceções como **"erros"** da  aplicação, outros como **"caminhos alternativos"** para as regras de negócios em questão e assim por diante. É algo interessante e muitas vezes interpretativa.

Tem desenvolvedores que se utilizam da forma que a linguagem **"força"** o tratamento das exceções a fim que atingir o seu objetivo, e acho que todos eles estão corretos de acordo com o cada contexto alvo. 

Há uns meses atrás tive uma discussões interessantes com outros desenvolvedores e em particular com a comunidade **SouJava** sobre o assunto. Quero parabenizar a todos pelas ótimas discussões **(You rock guys!!!)**. 

Mas uma coisa que temos que admitir: pode não parecer, mas exceções são peças importantes para entender os cenários **"excepcionais"** que **"brotam"** em tempo de desenvolvimento e de execução em nossos códigos.

Vamos explorar algumas dessas dicas que possam nos ajudar a modelar e lidar com exceções em nossos códigos Java. 

##Não conhecer a hierarquia de classes de exceções do Java

Essa é uma dúvida que pode atrapalhar e muito a modelagem do código.

![image](https://dev-to-uploads.s3.amazonaws.com/uploads/articles/em6woonxnfn1x7nfu2t2.png)

Olhando a hierarquia das classes de exceções do Java podemos reconhecer que as classes em vermelho são as consideradas **Checked Exceptions** e as amarelas são as **Unchecked Exception**.

##Não saber a diferença entre Checked Exceptions e Unchecked Exceptions e quando usá-las

**Checked Exceptions** são exceções que devem ser usadas para **erros recuperáveis ou que sejam requisitos de um regra de negócio importante**. Normalmente exceções de seu domínio classificam-se nessa categoria. **O compilador da linguagem vai obrigar o tratamento dela sempre que um método que declarativamente a lançar for chamado**.

Já as **Unchecked Exceptions** devem ser utilizadas **quando algum cenário "excepcional" é irreversível ou irrecuperável**. A princípio não deveríamos capturar esses tipos de exceções. Normalmente utilizam-se **"Tratadores de exceções"** para esse fim, que geralmente é o de notificar o utilizador da regra que algo aconteceu e não há nada o que fazer para aquela situação.

##Capturar toda exceção como Exception

*Pra quê criar minhas exceções, vamos utilizar a que tem!*

Bom, capturar qualquer exceção (ou todas... hehehe) como Exception pode ser um problema. O mesmo ocorre do lado de quem está escrevendo um método que lança somente exceções do tipo Exception.
```java
try{
	debitAccount.transferTo(creditAccount,amount);
}catch(Exception ex){
	// Ok, mas o que aconteceu?
	// Teremos que checar a mensagem, e torcer para que ela seja útil
	// E talvez analisar a stacktrace
}
```
No código acima, por utilizar somente a classe Exception, a identificação está prejudicada então **se a mensagem de erro não ajudar, a análise do stacktrace será um dos únicos pontos para sua identificação**.

Como a classe Exception é uma **checked exception** então crie suas exceções a partir dela para os cenários queira que o compilador force o tratamento da mesma. **Lembre-se de criar suas exceções checadas para casos que devem e que realmente fazem sentido de acordo com seu domínio**.

##Criar muitas exceções de maneira desordenada

Cuidado pra não criar exceções para tudo! Caso o fizer, provavelmente terá muitas classes para gerenciar e manter então crie somente as exceções necessárias e seja claro nas mensagens.

Imagine em um sistema de transferência de valores entre contas, onde criássemos uma exceção para cada cenário:

```java
try{
	debitAccount.transferTo(creditAccount,amount);
}catch(InvalidAmountException ex){
	// quantia inválida, e agora?
}catch(ExceededAmountException ex){
	// quantia excedida, e agora?
}catch(BalanceNotAvailableException ex){
	// saldo não disponível, e agora?
}catch(DisabledCreditAccountException ex){
	// conta à creditar está desativada, e agora?
}catch(DisabledDebitAccountException ex){
	// conta à debitar está desativada, e agora?
}catch(BlockedCreditAccountException ex){
	// conta à creditar está bloqueada, e agora?
}catch(BlockedDebitAccountException ex){
	// conta à debitar está bloqueada e agora?
}catch(BusinessException ex){
	// alguma outra coisa aconteceu, e agora?
}
```
E se utilizarmos essa outra sintaxe:
```java
try{
	debitAccount.transferTo(creditAccount,amount);
}catch(InvalidAmountException 
			| ExceededAmountException 
			| BalanceNotAvailableException 
			| DisabledCreditAccountException 
			| DisabledDebitAccountException
			| BlockedCreditAccountException 
			| BlockedDebitAccountException 
			| BusinessException ex){
	// quantia inválida ou
	// quantia excedida ou
	// saldo não disponível ou
	// conta à creditar está desativada ou
	// conta à debitar está desativada ou
	// conta à creditar está bloqueada ou
	// conta à debitar está bloqueada ou
	// alguma outra coisa aconteceu, e agora?
}
```
Nada agradável, não é?

Que tal reduzir nossa lista de exceções:

![image](https://www.plantuml.com/plantuml/png/TSun3i8m38NXFQU8klS636q5AtTmaZT4k0x2JjM1mrCXGKCbnZ_vfET1ZHvMPtQHITLtbpNYRhb8vqazri_xL3KBL7__GmE8IIAknSQ5CbDW7AmGsXKaf74krGwJkpg3ekM5R8CnbYe7cdOwSOctFonip3ci_lJi-_V9BdnB27kXZTH6s6A0f3hp2m00)

[comment]: # (source: assets/exception-hierarchical-solution.puml)

Assim ficará mais sucinta o seu tratamento:
```java
try{
	debitAccount.transferTo(creditAccount,amount);
}catch(BalanceNotAvailableException ex){
	// saldo não disponível, e agora?
}catch(DeactivatedAccountException ex){
	// uma ou ambas estão desativadas, e agora?
}catch(BlockedAccountException ex){
	// uma ou ambas estão bloqueadas, e agora?
}
```
Claro, não existe **"bala de prata"**. Será preciso utilizar mensagens bem claras para fornecer um bom contexto para identificar o ocorrido. Em nosso exemplo, mensagens claras serão necessárias, especialmente as exceções DeactivatedAccountException e BlockedAccountException. Ambas não deixa claro se a debitAccount ou creditAccount ou ambas causaram o cenário em questão.

##Não utilizar mensagens claras nas exceções

Acredito que é unânime essa dica: sempre use mensagens claras em suas exceções. Isso ajudará bastante a encontrar e entender o erro. 
Normalmente é utilizado um arquivos de propriedades para armazenar essas mensagens de erros, fornecendo assim talvez uma maneira de exibir a mensagem na camada de apresentação de maneira internacionalizada. 

##Silenciar exceções

Outra abordagem muito equivocada e muito comum que ocorre é no tratamento de exceções que o chamador "acham desnecessário" realizar o tratamento, simplesmente ignorando o fato da ocorrência do lançamento de exceção.

Por exemplo, você pode talvez ter visto algo assim:

```java
Connection  connection = null;
try{
		connection = datasource.getConnection();
		// executando uma regra incrível aqui
}catch(SQLException exception){
		handleSQLException(exception);
}finally{
	if( connection != null){
		try{
			connection.close();
		}catch(SQLException ex){}
	}
}
```
## Não finalizar/fechar recursos abertos no contexto que ocorreu a exceção

Um dos fatores de leaks em aplicações java é o não fechamento correto de recursos como conexão de banco de dados, streams ou qualquer outro componente que tem por necessidade serem fechados ao finalizar o bloco de código que eles foram abertos ou gerados. 
Utilize o bloco **finally** para fechar os recursos ou componentes necessários.

Então, não faça isso:
```java
Connection  connection = null;
try{
		connection = datasource.getConnection();
		// executando uma regra incrível aqui
}catch(SQLException exception){
		handleSQLException(exception);
}
```

Meu conselho é fazer algo assim:
```java
Connection  connection = null;
try{
		connection = datasource.getConnection();
		// executando uma regra incrível aqui
}catch(SQLException exception){
		handleSQLException(exception);
}finally{
	if( connection != null){
		try{
			connection.close();
		}catch(SQLException ex){
			throw new IllegalStateException("algo grave aconteceu ao fechar a conexão",ex);
		}
	}
}
```

A fim de ajudar a lidar com esse problema, a partir do Java 7 foi adicionado o **try-with-resources** statement, onde é possível utilizar quando precisarmos trabalhar com qualquer objeto que implemente a interface **java.lang.AutoCloseable**, que inclui todos os objetos que implementam **java.io.Closeable**:
```java
try(Connection  connection = datasource.getConnection()){
		// executando uma regra incrível aqui
}catch(SQLException exception){
		handleSQLException(exception);
}
```
Assim os componentes e recursos abertos dentro do **try()** serão fechados ao finalizar o bloco.

**Nota:** *Uma instrução try-with-resources pode ter blocos catch e finally, exatamente como uma instrução try comum. Em uma instrução try-with-resources, qualquer catch ou finally block é executado depois que os recursos declarados são fechados.*

##Silenciar exceções
Outra abordagem muito equivocada e muito comum que ocorre é no tratamento de exceções que o chamador "acham desnecessário" realizar o tratamento, simplesmente ignorando o fato da ocorrência do lançamento de exceção.

Mas não se ilude, talvez a aplicação entrou em um estado inconsistente e isso ninguém quer, não é? 

Um outro exemplo:
```java
try{
		CurrencyUnit usd = Monetary.getCurrency("USD");
		Money coffee = Money.of(5, usd);
		
		Account maxAccount = bank.getAccount(123);
		Account otavioAccount = bank.getAccount(333);

		maxAccount
			.transferTo(otavioAccount)
			.amount(coffee)
			.commit();

}catch(BalanceNotAvailableException exception){
		// É impossível saber o que aconteceu aqui sem realizar um debug! 
}
```
É impossível saber o que aconteceu aqui sem realizar um debug! (Bom, o Otávio infelizmente vai saber... ☹️).

Então, sempre trate ou log essas exceções (talvez em um nível WARN ou ERROR).

##Perder o rastreamento da pilha

Quem sou eu pra ditar regras, não é? Mas vamos olhar o exemplo abaixo. 

```java
try{
		CurrencyUnit usd = Monetary.getCurrency("USD");
		Money coffee = Money.of(5, usd);
		
		Account maxAccount = bank.getAccount(123);
		Account otavioAccount = bank.getAccount(333);

		maxAccount
			.transferTo(otavioAccount)
			.amount(coffee)
			.commit();

}catch(BalanceNotAvailableException exception){
		// Assim perderemos a causa raiz da exceção que é: 
    // o Max não vai poder para o café pro Otávio dessa vez!
		throw new ExceededAmountException();
}
```
Para quem desenvolveu pode parecer nada significante, mas ignorar a causa raiz irá causar um desconforto para quem estiver dando o suporte caso a exceção ocorra. 

Se a mensagem não for clara, será necessário navegar até o ponto do código que  ocorreu a exceção. 

Assim perderemos a causa raiz da exceção que é: o Max não conseguiu pagar o café pro Otávio dessa vez! 

##Conclusão

Trabalhar com exceções em Java é um assunto um tanto quanto polêmico e interessante. Tem uns que abominam, outros que se favorecem da funcionalidade e até pode ser divisor de águas quando se tenta conhecer outras linguagens (como por exemplo o Golang). 

Na verdade o que interessa é resolver o problema da melhor maneira possível, digo, sempre olhando os prós e os contras de cada abordagem, **pensando não só do ponto de vista de quem desenvolve mas também do ponto de vista de manutenção**.

Deixo abaixo alguns artigos que utilizei que são referências ótimas para todos sobre o tema!

Espero ter **"lançado"** em sua mente a vontade de explorar mais sobre esse tema e assim torço para que você continue a **"capturar "** e aprender! 

Esse artigo foi a base da palestra que apresentei no 1o. **[SouJava Lightning Talks](https://www.youtube.com/watch?v=R-3v90oM0TI&t=1808s)** então fique a vontade de conferir!!!

Deixe suas dicas, dúvidas e **"exceções"**, quero dizer, sugestões aqui!

Abraços e até a próxima!

###Referências:

- [11 Erros que desenvolvedores Java ocometem quando usam Exceptions - por Rafael Del Nero](https://www.oracle.com/br/technical-resources/article/java/erros-java-exceptions.html)
- [Exception handling - (https://learning-notes.mistermicheels.com/)](https://learning-notes.mistermicheels.com/architecture-design/exception-handling/)
- [The try-with-resources Statement] (https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
