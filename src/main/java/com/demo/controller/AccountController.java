package com.demo.controller;

import com.demo.model.Account;
import com.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping("/list")
    public List<Account> getAll() {
        return service.findAll();
    }

    @PostMapping("/{id}/get")
    public ResponseEntity<Account> getById(@PathVariable Long id) {
        Account account = service.findById(id);
        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return service.create(account);
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<Account> update(@PathVariable Long id,
                                          @RequestBody Account account) {
        Account updated = service.update(id, account);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = service.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
