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

    private boolean exist(int numero) {
        return contas.containsKey(numero);
    }
}
