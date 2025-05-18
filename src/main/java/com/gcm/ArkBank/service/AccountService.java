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

    private boolean exist(int numero) {
        return contas.containsKey(numero);
    }
}
