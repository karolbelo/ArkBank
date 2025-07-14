package com.gcm.ArkBank.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountBonus extends Acount {
    private int points;

     public AccountBonus(int number, double initialBalance) {
        super(number, initialBalance);      
        this.points = 10;    // Conta bônus começa com 10 pontos
    }

    public void adicionarPontos(int pontos) {
        this.points += pontos;
    }
    
}
