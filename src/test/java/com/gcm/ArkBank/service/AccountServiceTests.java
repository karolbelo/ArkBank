package com.gcm.ArkBank;

import com.gcm.ArkBank.exceptions.AccountNotFoundException;
import com.gcm.ArkBank.exceptions.InvalidOperationException;
import com.gcm.ArkBank.model.Account;
import com.gcm.ArkBank.model.AccountBonus;
import com.gcm.ArkBank.model.AccountSaving;
import com.gcm.ArkBank.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTests {

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService();
    }

    @Test
    void testCadastrarContaSimples() {
        Account account = accountService.cadastrarConta(123, 1, 100.0);
        assertNotNull(account);
        assertEquals(123, account.getNumber());
        assertEquals(100.0, account.getBalance());
        assertInstanceOf(Account.class, account);
    }

    @Test
    void testCadastrarContaBonus() {
        AccountBonus account = (AccountBonus) accountService.cadastrarConta(456, 2, 200.0);
        assertNotNull(account);
        assertEquals(456, account.getNumber());
        assertEquals(200.0, account.getBalance());
        assertEquals(10, account.getPoints());
        assertInstanceOf(AccountBonus.class, account);
    }

    @Test
    void testCadastrarContaPoupanca() {
        AccountSaving account = (AccountSaving) accountService.cadastrarConta(789, 3, 300.0);
        assertNotNull(account);
        assertEquals(789, account.getNumber());
        assertEquals(300.0, account.getBalance());
        assertInstanceOf(AccountSaving.class, account);
    }

    @Test
    void testConsultarContaExistente() {
        accountService.cadastrarConta(123, 1, 100.0);
        Account account = accountService.getAccount(123);
        assertNotNull(account);
        assertEquals(123, account.getNumber());
    }

    @Test
    void testConsultarContaInexistente() {
        assertThrows(AccountNotFoundException.class, () -> {
            accountService.getAccount(999);
        });
    }

    @Test
    void testConsultarSaldo() {
        accountService.cadastrarConta(123, 1, 150.0);
        double saldo = accountService.getSaldo(123);
        assertEquals(150.0, saldo);
    }

    @Test
    void testCreditarValorPositivo() {
        accountService.cadastrarConta(123, 1, 100.0);
        accountService.creditar(123, 50.0);
        assertEquals(150.0, accountService.getSaldo(123));
    }

    @Test
    void testCreditarValorNegativo() {
        accountService.cadastrarConta(123, 1, 100.0);
        assertThrows(InvalidOperationException.class, () -> {
            accountService.creditar(123, -50.0);
        });
    }

    @Test
    void testCreditarBonificacao() {
        AccountBonus account = (AccountBonus) accountService.cadastrarConta(456, 2, 100.0);
        accountService.creditar(456, 250.0);
        assertEquals(350.0, account.getBalance());
        assertEquals(12, account.getPoints());
    }

    @Test
    void testDebitarValorPositivo() {
        accountService.cadastrarConta(123, 1, 100.0);
        accountService.debitar(123, 50.0);
        assertEquals(50.0, accountService.getSaldo(123));
    }

    @Test
    void testDebitarValorNegativo() {
        accountService.cadastrarConta(123, 1, 100.0);
        assertThrows(InvalidOperationException.class, () -> {
            accountService.debitar(123, -50.0);
        });
    }

    @Test
    void testDebitarDeixarSaldoNegativo() {
        accountService.cadastrarConta(123, 1, 500.0);
        assertThrows(InvalidOperationException.class, () -> {
            accountService.debitar(123, 1501.0);
        });
    }

    @Test
    void testTransferenciaValorNegativo() {
        accountService.cadastrarConta(123, 1, 100.0);
        accountService.cadastrarConta(456, 1, 100.0);
        assertThrows(InvalidOperationException.class, () -> {
            accountService.transferir(123, 456, -50.0);
        });
    }

    @Test
    void testTransferenciaDeixarSaldoNegativo() {
        accountService.cadastrarConta(123, 1, 500.0);
        accountService.cadastrarConta(456, 1, 100.0);
        assertThrows(InvalidOperationException.class, () -> {
            accountService.transferir(123, 456, 1501.0);
        });
    }

    @Test
    void testTransferenciaBonificacao() {
        accountService.cadastrarConta(123, 1, 500.0);
        AccountBonus accountDestino = (AccountBonus) accountService.cadastrarConta(456, 2, 100.0);
        accountService.transferir(123, 456, 300.0);

        assertEquals(200.0, accountService.getSaldo(123));
        assertEquals(400.0, accountService.getSaldo(456));
        assertEquals(12, accountDestino.getPoints()); 
    }

    @Test
    void testRenderJuros() {
        accountService.cadastrarConta(111, 3, 1000.0);
        accountService.cadastrarConta(222, 1, 2000.0);
        accountService.cadastrarConta(333, 3, 3000.0);

        accountService.renderJuros(1.0);

        assertEquals(1010.0, accountService.getSaldo(111));
        assertEquals(2000.0, accountService.getSaldo(222)); // Should not change
        assertEquals(3030.0, accountService.getSaldo(333));
    }
} 