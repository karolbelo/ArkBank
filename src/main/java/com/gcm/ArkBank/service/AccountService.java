package com.gcm.ArkBank.service;

import com.gcm.ArkBank.model.Account;
import com.gcm.ArkBank.model.AccountBonus;

import com.gcm.ArkBank.model.AccountSaving;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class AccountService {
	private Map<Integer, Account> contas = new HashMap<>();
	Scanner scanner = new Scanner(System.in);

	public boolean checarConta(int numConta){
		if (!contas.containsKey(numConta)) {
			System.out.printf("Conta inválida.");
			return false;
		}
		return true;
	}
//Adicionado: Opção de conta do tipo bonus
	public void cadastrarConta(int numero, int tipoConta) {
		if (exist(numero)) {
			System.out.println("Conta já cadastrada. Tente outro número.");
			return;
		}
		switch (tipoConta) {
		case 1 -> {
			contas.put(numero, new Account(numero));
			System.out.println("Conta Comum cadastrada com sucesso.");
		}
		case 2 -> {
			contas.put(numero, new AccountBonus(numero));
			System.out.println("Conta Bônus cadastrada com sucesso.");
		}
		case 3 -> {
			System.out.println("Digite o saldo inicial da conta");
			double saldoInicial = scanner.nextDouble();
			contas.put(numero, new AccountSaving(numero, saldoInicial));
			System.out.println("Conta Poupança cadastrada com sucesso.");
			System.out.printf("saldo inicial %.2f :.%n", saldoInicial);
		}
		default -> {
			System.out.println("Tipo de conta inválido");
		}
		}
	}

	public void creditar(int numero, double valor) {
		// Validação para valores negativos (nova implementação para rc-1.3.0)
		if (valor <= 0) {
			System.out.println("Erro: Valor do crédito deve ser positivo.");
			return;
		}

		Account conta = contas.get(numero);
		if (!checarConta(numero)){
			return;
		}
			conta.setBalance(conta.getBalance() + valor);

			// Lógica de pontos para depósito
			if (conta instanceof AccountBonus) {
				int points = (int) (valor / 100);
				((AccountBonus) conta).adicionarPontos(points);
				System.out.printf("Você ganhou %d pontos por este depósito.%n", points);
			}

			System.out.printf("Crédito de R$ %.2f realizado com sucesso.%n", valor);
			System.out.printf("Novo saldo: R$ %.2f%n", conta.getBalance());
	}

	public void debitar(int numero, double valor) {
		Account conta = contas.get(numero);
		if (!checarConta(numero)){
			return;
		}

		// Adicionado: Verificação de valor positivo (nova implementação para rc-1.3.0)
		if (valor <= 0) {
			System.out.println("Valor do débito deve ser positivo.");
			return;
		}
		// Adicionado: Verificação de saldo suficiente
		if (conta.getBalance() < valor) {
			System.out.println("Saldo insuficiente para realizar o débito.");
			return;
		}

		conta.setBalance(conta.getBalance() - valor);
		System.out.printf("Débito de R$ %.2f realizado com sucesso.%n", valor);
		System.out.printf("Novo saldo: R$ %.2f%n", conta.getBalance());
	}

	public void transferir(int origem, int destino, double valor) {
		if (origem == destino) {
			System.out.println("Conta de origem e destino não podem ser iguais.");
			return;
		}

		Account contaOrigem = contas.get(origem);
		Account contaDestino = contas.get(destino);

		if ((!checarConta(origem) )|| (!checarConta(destino))) {
			System.out.println("Conta de origem ou destino não encontrada.");
			return;
		}

		// (nova implementação para rc-1.3.0)
		if (valor <= 0) {
			System.out.println("Valor da transferência deve ser positivo.");
			return;
		}
//Adicionado: Ajuste na mensagem de erro
		if (contaOrigem.getBalance() < valor) {
			System.out.println("Saldo insuficiente para realizar a transferência.");
			return;
		}

		contaOrigem.setBalance(contaOrigem.getBalance() - valor);
		contaDestino.setBalance(contaDestino.getBalance() + valor);

		// Adicionado: Lógica de pontos para transferência recebida
		if (contaDestino instanceof AccountBonus) {
			int points = (int) (valor / 200);
			((AccountBonus) contaDestino).adicionarPontos(points);
			System.out.printf("Conta destino ganhou %d pontos por esta transferência.%n", points);
		}

		System.out.printf("Transferencia de R$ %.2f realizado com sucesso.%n", valor);
	}

	public void consultarPontos(int numero) {
		Account conta = contas.get(numero);
		if (!checarConta(numero)){
			return;
		}
		if (conta instanceof AccountBonus) {
			System.out.printf("Pontos acumulados: %d%n", ((AccountBonus) conta).getPoints());
		} else {
			System.out.println("Esta não é uma conta bônus.");
		}
	}

	private boolean exist(int numero) {
		return contas.containsKey(numero);
	}

	public void checarSaldoConta(int numeroConta) {
		Account conta = contas.get(numeroConta);
		if (!checarConta(numeroConta)){
			return;
		}
		System.out.printf("Saldo em conta: R$ %.2f%n", conta.getBalance());
	}

	public void calcularJuros(int numeroConta, double taxaJuros) {
		Account conta = contas.get(numeroConta);
		if (conta instanceof AccountSaving) {
			double novoSaldo = conta.getBalance() * (taxaJuros / 100);
			conta.setBalance(conta.getBalance() + novoSaldo);
			System.out.printf("Saldo em conta: R$ %.2f%n", conta.getBalance());
		} else {
			System.out.println("Conta não encontrada");
		}
	}
}
