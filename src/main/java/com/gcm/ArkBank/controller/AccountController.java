package com.gcm.ArkBank.controller;

import com.gcm.ArkBank.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    private Scanner scanner = new Scanner(System.in);
}
