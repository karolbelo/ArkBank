package com.gcm.ArkBank.controller;

import com.gcm.ArkBank.model.Account;
import com.gcm.ArkBank.model.AccountBonus;
import com.gcm.ArkBank.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Scanner;

@Component
public class AccountCliController implements CommandLineRunner {
	private final AccountService accountService;
	private final ApplicationContext context;
	Scanner scanner = new Scanner(System.in);

	public AccountCliController(AccountService accountService, ApplicationContext context) {
		this.accountService = accountService;
		this.context = context;
	}

	@Value("${cli.enabled:true}")
    private boolean cliEnabled;

    @Override
    public void run(String... args) throws Exception {
        if (!cliEnabled) return;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Bem-vindo ao Banco ArkBank!");

		while (true) {
			System.out.println("" + "\n1 - Cadastrar Conta" + "\n2 - Crédito" + "\n3 - Transferência" + "\n4 - Débito "
					+ "\n5 - Checar Saldo" + "\n6 - Render Juros(Apenas contas do tipo poupança)" + "\n7 - Consultar Conta" + "\n0 - Sair");

			int opcao = scanner.nextInt();
			switch (opcao) {
				case 1 -> cadastrarConta();
				case 2 -> creditar();
				case 3 -> transferir();
				case 4 -> debitar();
				case 5 -> checarSaldo();
				case 6 -> renderJuros();
				case 7 -> consultarConta();
				case 0 -> encerrarAplicacao();
				default -> System.out.println("Opção inválida.");
			}
		}
	}

	private void consultarConta() {
		System.out.print("Número da conta: ");
		int numero = scanner.nextInt();
		try {
			Account account = accountService.getAccount(numero);
			System.out.println("Dados da conta:");
			System.out.println("Número: " + account.getNumber());
			System.out.println("Tipo: " + account.getClass().getSimpleName());
			System.out.println("Saldo: " + account.getBalance());
			if (account instanceof AccountBonus) {
				System.out.println("Bônus: " + ((AccountBonus) account).getPoints());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void cadastrarConta() {
		System.out.print("Número da conta: ");
		int numero = scanner.nextInt();

		System.out.println("Escolha o tipo de conta:");
		System.out.println("1 - Conta Simples");
		System.out.println("2 - Conta Bônus");
		System.out.println("3 - Conta Poupança");

		System.out.print("Opção: ");
		int tipoConta = scanner.nextInt();

		double saldoInicial = 0.0;
		if (tipoConta == 1 || tipoConta == 2 || tipoConta == 3) {
			System.out.print("Insira o saldo inicial: R$ ");
			saldoInicial = scanner.nextDouble();
		}

		try {
			accountService.cadastrarConta(numero, tipoConta, saldoInicial);
			System.out.println("Conta cadastrada com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void creditar() {
		System.out.print("Número da conta: ");
		int numero = scanner.nextInt();
		System.out.print("Valor: ");
		double valor = scanner.nextDouble();

		try {
			accountService.creditar(numero, valor);
			System.out.println("Crédito realizado com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void debitar() {
		System.out.print("Número da conta: ");
		int numero = scanner.nextInt();
		System.out.print("Valor: ");
		double valor = scanner.nextDouble();

		try {
			accountService.debitar(numero, valor);
			System.out.println("Débito realizado com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void transferir() {
		System.out.print("Conta origem: ");
		int origem = scanner.nextInt();
		System.out.print("Conta destino: ");
		int destino = scanner.nextInt();
		System.out.print("Valor: ");
		double valor = scanner.nextDouble();
		try {
			accountService.transferir(origem, destino, valor);
			System.out.println("Transferência realizada com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void checarSaldo() {
		System.out.print("Número da conta: ");
		int numero = scanner.nextInt();
		try {
			double saldo = accountService.getSaldo(numero);
			System.out.printf("Saldo em conta: R$ %.2f%n", saldo);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void encerrarAplicacao() {
		System.out.println("Encerrando.");
		int exitCode = SpringApplication.exit(context, () -> 0);
		System.exit(exitCode);
	}

	private void renderJuros() {
		System.out.println("Quantia de juros por mês em %");
		double porcentagem = scanner.nextDouble();
		try {
			accountService.renderJuros(porcentagem);
			System.out.println("Juros rendidos com sucesso!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
