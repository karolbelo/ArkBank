package com.gcm.ArkBank.dto;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private int numero;
    private int tipoConta;
    private double saldoInicial;
} 