package com.gcm.ArkBank.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private int number;
    private double balance;

    public Account(int number) {
        this.number = number;
        this.balance = 0.0;
    }
    //isso aqui é pra Account Saving
    public Account(int number, double balance) {
        this.number = number;
        this.balance = balance;
    }
}
