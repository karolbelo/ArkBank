package com.gcm.ArkBank.controller;

import com.gcm.ArkBank.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountController implements CommandLineRunner {
    private final AccountService accountService;
    private final ApplicationContext context;
    Scanner scanner = new Scanner(System.in);

    public AccountController(AccountService accountService, ApplicationContext context) {
        this.accountService = accountService;
        this.context = context;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Banco ArkBank!");

        while (true) {
            System.out.println("" +
                    "\n1 - Cadastrar Conta" +
                    "\n2 - Crédito" +
                    "\n3 - Transferência" +
                    "\n4 - Débito " +
                    "\n5 - Checar Saldo" +
                    "\n6 - Render Juros(Apenas contas do tipo poupança)" +
                    "\n0 - Sair");
          
            int opcao = scanner.nextInt();
            switch (opcao) {
                case 1 -> cadastrarConta();
                case 2 -> creditar();
                case 3 -> transferir();
                case 4 -> debitar();
                case 5 -> checarSaldo();
                case 6 -> renderJuros();
                case 0 -> encerrarAplicacao();
                default -> System.out.println("Opção inválida.");
            }
        }
    }
//Adicionado: Opção da conta do tipo bonus
    private void cadastrarConta() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();

        System.out.println("Escolha o tipo de conta:");
        System.out.println("1 - Conta Simples");
        System.out.println("2 - Conta Bônus");
        System.out.println("3 - Conta Poupança");

        System.out.print("Opção: ");
        int tipoConta = scanner.nextInt();

        accountService.cadastrarConta(numero, tipoConta);
    }


    private void creditar() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();

        if (valor <= 0) {
            System.out.println("Valor deve ser positivo.");
            return;
        }

        accountService.creditar(numero, valor);
    }

    private void debitar() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();

        if (valor <= 0) {
            System.out.println("Valor deve ser positivo.");
            return;
        }

        accountService.debitar(numero, valor);
    }
  
    private void transferir() {
        System.out.print("Conta origem: ");
        int origem = scanner.nextInt();
        System.out.print("Conta destino: ");
        int destino = scanner.nextInt();
        System.out.print("Valor: ");
        double valor = scanner.nextDouble();
        accountService.transferir(origem, destino, valor);
    }

    private void checarSaldo(){
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        accountService.checarSaldoConta(numero);
        
    }

    private void encerrarAplicacao() {
        System.out.println("Encerrando.");
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
    private void renderJuros(){
        System.out.println("Número da conta");
        int numConta = scanner.nextInt();
        System.out.println("Quantia de juros por mês em %");
        double porcentagem = scanner.nextDouble();
        accountService.calcularJuros(numConta,porcentagem);
    }
}
