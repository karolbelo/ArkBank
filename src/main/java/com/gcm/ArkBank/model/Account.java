package com.gcm.ArkBank.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private int number;
    private double balance;

    public Account(int number) {
        this(number, 0.0);
    }

    public Account(int number, double balance) {
        this.number = number;
        this.balance = balance;
    }

    public Account(int number, double balance) {
        this.number = number;
        this.balance = balance;
    }
}
