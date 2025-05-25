package com.gcm.ArkBank.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//saldo inicial é apenas temporário, no bugfix vai voltar a ser um atributo herdado
public class AccountSaving extends Account{
    public AccountSaving(int number, double saldoInicial) {
        super(number, saldoInicial);
    }
}
