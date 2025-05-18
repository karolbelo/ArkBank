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
            System.out.println("\n1 - Cadastrar Conta\n0 - Sair");
            int opcao = scanner.nextInt();
            switch (opcao) {
                case 1 -> cadastrarConta();
                case 0 -> encerrarAplicacao();
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrarConta() {
        System.out.print("Número da conta: ");
        int numero = scanner.nextInt();
        accountService.cadastrarConta(numero);
    }

    private void encerrarAplicacao() {
        System.out.println("Encerrando.");
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
    }
}
