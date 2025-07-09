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

##Link para o DockherHub
https://hub.docker.com/repository/docker/alvaroprudencio/ark-bank/tags/rel-3.0/sha256-b4b7c802a50e96d4ac507266f24b6a013c52f45e664ad6baeca1bce84dabb6a6

Para rodar esta aplicação, você precisa de:

- Java: JDK 21.
- Git: Para clonar o repositório.
- Maven

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
6 - Render Juros
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
  
* **Opção 6 – Render Juros**
  Permite ao usuário informar uma taxa de juros e aplica o rendimento em **todas as contas poupança** cadastradas.  

* **Opção 0 – Sair**
  Encerra a execução da aplicação de forma segura, exibindo uma mensagem de despedida.

* **Qualquer outro número digitado** será interpretado como **opção inválida**, e o menu será exibido novamente.

##Como usar os end-points

Execute o comando abaixo para iniciar o container:

```bash
docker run -p 8080:8080 alvaroprudencio/ark-bank:latest
```
Acesse a porta 8080
```bash
http://localhost:8080/
```
Caso dê algum erro, tente usar o curl, mas a lógica de uso é a mesma

Listar todas as contas
```bash
curl http://localhost:8080/banco/conta/
```
Obter uma conta pelo ID, caso deseje constultar o saldo basta adicionar "/saldo" no final
```bash
curl http://localhost:8080/banco/conta/1
```
Creditar conta, debito funciona da mesma forma, apenas mudando credito pra debito
```bash
curl -X PUT http://localhost:8080/banco/conta/1/credito \
  -H "Content-Type: application/json" \
  -d '{ "valor": 500.0 }'
```
Transferência entre duas contas
```bash
curl -X PUT http://localhost:8080/banco/conta/transferencia \
  -H "Content-Type: application/json" \
  -d '{
    "from": 1,
    "to": 2,
    "amount": 150.0
  }'
```
Rendimento de uma conta
```bash
curl -X PUT http://localhost:8080/banco/conta/rendimento \
  -H "Content-Type: application/json" \
  -d '{ "taxaJuros": 0.05 }'
```
---
