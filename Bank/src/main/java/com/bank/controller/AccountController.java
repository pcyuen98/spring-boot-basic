package com.bank.controller;

import com.bank.entity.AccountEntity;
import com.bank.mapper.AccountMapper;
import com.bank.model.AccountDTO;
import com.bank.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/v1")
public class AccountController {

	// TODO: SpringBoot:Practical 7.1 - Test CRUD services below and ensure it's working on swagger
	
	// ensure requestBody is DTO instead of Entity
	// ensure return item map to DTO instead of entity
	// use CustomerController.java as an example
	
    @Autowired
    private IAccountService accountService;
    
	@Autowired
	private AccountMapper accountMapper;

    @PostMapping
    public ResponseEntity<AccountEntity> create(@RequestBody AccountEntity account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountMapper.toDto(accountService.getAccountById(id)));
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
