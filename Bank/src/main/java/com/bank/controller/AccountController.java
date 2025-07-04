package com.bank.controller;

import com.bank.entity.AccountEntity;
import com.bank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/v1")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping
    public ResponseEntity<AccountEntity> create(@RequestBody AccountEntity account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountEntity> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @GetMapping
    public ResponseEntity<List<AccountEntity>> getAll() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountEntity> update(@PathVariable Long id, @RequestBody AccountEntity account) {
        return ResponseEntity.ok(accountService.updateAccount(id, account));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
