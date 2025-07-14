package com.gcm.ArkBank.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gcm.ArkBank.exceptions.AccountNotFoundException;
import com.gcm.ArkBank.exceptions.InvalidOperationException;
import com.gcm.ArkBank.model.Account;
import com.gcm.ArkBank.model.AccountBonus;
import com.gcm.ArkBank.model.AccountSaving;

@Service
public class AccountService {
	private final Map<Integer, Account> contas = new HashMap<>();

	public Account getAccount(int numero) {
		Account account = contas.get(numero);
		if (account == null) {
			throw new AccountNotFoundException("Conta não encontrada.");
		}
		return account;
	}

	public Collection<Account> getAllAccounts() {
		return contas.values();
	}

	public Account cadastrarConta(int numero, int tipoConta, double saldoInicial) {
		if (contas.containsKey(numero)) {
			throw new InvalidOperationException("Conta já cadastrada. Tente outro número.");
		}

		Account novaConta;
		switch (tipoConta) {
			case 1:
				novaConta = new Account(numero, saldoInicial);
				break;
			case 2:
				novaConta = new AccountBonus(numero, saldoInicial);
				break;
			case 3:
				novaConta = new AccountSaving(numero, saldoInicial);
				break;
			default:
				throw new InvalidOperationException("Tipo de conta inválido");
		}
		contas.put(numero, novaConta);
		return novaConta;
	}

	public Account creditar(int numero, double valor) {
		if (valor <= 0) {
			throw new InvalidOperationException("Erro: Valor do crédito deve ser positivo.");
		}

		Account conta = getAccount(numero);

		conta.setBalance(conta.getBalance() + valor);

		if (conta instanceof AccountBonus) {
			int points = (int) (valor / 100);
			((AccountBonus) conta).adicionarPontos(points);
		}
		return conta;
	}

	public Account debitar(int numero, double valor) {
		Account conta = getAccount(numero);

		if (valor <= 0) {
			throw new InvalidOperationException("Valor do débito deve ser positivo.");
		}

		if ((conta instanceof Account || conta instanceof AccountBonus) && conta.getBalance() - valor <= -2000) {
			throw new InvalidOperationException("Operação cancelada, limite de R$ -2000,00 de saldo negativo atingido");
		}

		conta.setBalance(conta.getBalance() - valor);
		return conta;
	}

	public void transferir(int origem, int destino, double valor) {
		if (origem == destino) {
			throw new InvalidOperationException("Conta de origem e destino não podem ser iguais.");
		}

		Account contaOrigem = getAccount(origem);
		Account contaDestino = getAccount(destino);

		if (valor <= 0) {
			throw new InvalidOperationException("Valor da transferência deve ser positivo.");
		}
		if ((contaOrigem instanceof Account || contaOrigem instanceof AccountBonus) && contaOrigem.getBalance() - valor <= -1000) {
			throw new InvalidOperationException("Operação cancelada, limite de R$ -1000,00 de saldo negativo atingido");
		}

		contaOrigem.setBalance(contaOrigem.getBalance() - valor);
		contaDestino.setBalance(contaDestino.getBalance() + valor);

		if (contaDestino instanceof AccountBonus) {
			int points = (int) (valor / 150);
			((AccountBonus) contaDestino).adicionarPontos(points);
		}
	}

	public double getSaldo(int numeroConta) {
		Account conta = getAccount(numeroConta);
		return conta.getBalance();
	}

	public void renderJuros(double taxaJuros) {
		contas.values().stream()
				.filter(c -> c instanceof AccountSaving)
				.forEach(c -> {
					double juros = c.getBalance() * (taxaJuros / 100);
					c.setBalance(c.getBalance() + juros);
				});
	}
}
