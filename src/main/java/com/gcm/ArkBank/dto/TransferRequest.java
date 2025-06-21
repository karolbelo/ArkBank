package com.gcm.ArkBank.dto;

import lombok.Data;

@Data
public class TransferRequest {
    private int from;
    private int to;
    private double amount;
} 