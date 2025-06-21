package com.gcm.ArkBank.controller;

import com.gcm.ArkBank.dto.CreateAccountRequest;
import com.gcm.ArkBank.dto.CreditDebitRequest;
import com.gcm.ArkBank.dto.RenderJurosRequest;
import com.gcm.ArkBank.dto.TransferRequest;
import com.gcm.ArkBank.model.Account;
import com.gcm.ArkBank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/banco/conta")
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<Account> cadastrarConta(@RequestBody CreateAccountRequest request) {
        Account account = accountService.cadastrarConta(request.getNumero(), request.getTipoConta(), request.getSaldoInicial());
        return ResponseEntity.ok(account);
    }

    @GetMapping("/")
    public ResponseEntity<Collection<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable int id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Map<String, Double>> getSaldo(@PathVariable int id) {
        double saldo = accountService.getSaldo(id);
        return ResponseEntity.ok(Collections.singletonMap("saldo", saldo));
    }

    @PutMapping("/{id}/credito")
    public ResponseEntity<Account> creditar(@PathVariable int id, @RequestBody CreditDebitRequest request) {
        Account account = accountService.creditar(id, request.getValor());
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}/debito")
    public ResponseEntity<Account> debitar(@PathVariable int id, @RequestBody CreditDebitRequest request) {
        Account account = accountService.debitar(id, request.getValor());
        return ResponseEntity.ok(account);
    }

    @PutMapping("/transferencia")
    public ResponseEntity<Void> transferir(@RequestBody TransferRequest request) {
        accountService.transferir(request.getFrom(), request.getTo(), request.getAmount());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/rendimento")
    public ResponseEntity<Void> renderJuros(@RequestBody RenderJurosRequest request) {
        accountService.renderJuros(request.getTaxaJuros());
        return ResponseEntity.ok().build();
    }
} 