package com.gcm.ArkBank.service;

import com.gcm.ArkBank.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    private Map<Integer, Account> contas = new HashMap<>();

    public void cadastrarConta(int numero) {
        if (exist(numero)) {
            System.out.println("Conta já cadastrada. Tente outro número.");
            return;
        }
        System.out.println("Conta cadastrada com sucesso.");
        contas.put(numero, new Account(numero));
    }

    public void creditar(int numero, double valor) {
        Account conta = contas.get(numero);
        if (conta != null) {
            conta.setBalance(conta.getBalance() + valor);
            System.out.printf("Crédito de R$ %.2f realizado com sucesso.%n", valor);
            System.out.printf("Novo saldo: R$ %.2f%n", conta.getBalance());
        } else {
            System.out.printf("Conta inválida.");
        }
    }

    public void debitar(int numero, double valor) {
        Account conta = contas.get(numero);
        if (conta == null) {
            System.out.printf("Conta inválida.");
            return;
        }

        conta.setBalance(conta.getBalance() - valor);
        System.out.printf("Debito de R$ %.2f realizado com sucesso.%n", valor);
        System.out.printf("Novo saldo: R$ %.2f%n", conta.getBalance());
    }
  
    public void transferir(int origem, int destino, double valor) {
        if (origem == destino) {
            System.out.println("Conta de origem e destino não podem ser iguais.");
            return;
        }

        Account contaOrigem = contas.get(origem);
        Account contaDestino = contas.get(destino);

        if (contaOrigem == null || contaDestino == null) {
            System.out.println("Conta de origem ou destino não encontrada.");
            return;
        }

        if (valor <= 0) {
            System.out.println("Valor da transferência deve ser positivo.");
            return;
        }

        if (contaOrigem.getBalance() < valor) {
            System.out.println("Saldo insuficiente.");
            return;
        }

        contaOrigem.setBalance(contaOrigem.getBalance() - valor);
        contaDestino.setBalance(contaDestino.getBalance() + valor);

        System.out.printf("Transferencia de R$ %.2f realizado com sucesso.%n", valor);
    }

    private boolean exist(int numero) {
        return contas.containsKey(numero);
    }
}
