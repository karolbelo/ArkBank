# ArkBank

Projeto da disciplina **DIM0517 - Gerência de Configuração e Mudanças**.

## Equipe

- Alvaro Prudencio Araujo – [im-a-Cloud](https://github.com/im-a-Cloud)
- Karolayne Belo Silva Galvao de Melo – [karolbelo](https://github.com/karolbelo)
- Raquel Alves de Brito – [raquelbrto](https://github.com/raquelbrto)

## Linguagem e Stack de Desenvolvimento

- Linguagem: Java
- Framework: Spring

## Variáveis de Ambiente

Para rodar esta aplicação, você precisa de:

- Java: JDK 21.
- Git: Para clonar o repositório.

##  Configuração do Projeto

1. Clone o Repositório

        git clone git@github.com:karolbelo/ArkBank.git

2. Acesse o diretorio

        cd ArkBank

3. Execução

   Execute via terminal: 

   ```bash
   mvn spring-boot:run
   ```
   
  Se preferir execute usando uma idea(intellij, eclipse).

---

## Explicação do Menu de Opções

Ao iniciar a aplicação pelo console, o usuário será saudado com a mensagem:

```
Bem-vindo ao Banco ArkBank!
```

Em seguida, um menu interativo será exibido continuamente com as seguintes opções:

```
1 - Cadastrar Conta
2 - Crédito
3 - Transferência
4 - Débito
5 - Consultar Saldo
0 - Sair
```

### Detalhes das opções:

* **Opção 1 – Cadastrar Conta**
  Permite ao usuário criar uma nova conta no sistema.
  O programa solicitará o número da conta, e a conta será criada com saldo inicial igual a zero.

* **Opção 2 – Crédito**
  Realiza uma operação de crédito em uma conta existente.
  O sistema pedirá o número da conta e o valor a ser creditado, e então adicionará esse valor ao saldo atual da conta.

* **Opção 3 – Transferência**
  Realiza uma transferência de valor entre duas contas.
  O usuário informará o número da conta de origem, da conta de destino e o valor a ser transferido.
  O valor será debitado da conta de origem e creditado na conta de destino.

* **Opção 4 – Débito**
  Subtrai um valor do saldo de uma conta existente.
  O sistema solicitará o número da conta e o valor a ser debitado.
  *Importante:* contas podem ficar com saldo negativo.

* **Opção 5 – Consultar Saldo**
  Permite consultar o saldo atual de uma conta existente.
  Basta informar o número da conta.


* **Opção 0 – Sair**
  Encerra a execução da aplicação de forma segura, exibindo uma mensagem de despedida.

* **Qualquer outro número digitado** será interpretado como **opção inválida**, e o menu será exibido novamente.

---
