package com.demo.service;

import com.demo.mapper.AccountMapper;
import com.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountMapper mapper;
    public List<Account> findAll() {
        return mapper.selectList(null);
    }
    public Account findById(Long id) {
        return mapper.selectById(id);
    }
    public Account create(Account account) {
        mapper.insert(account);
        return account;
    }
    public Account update(Long id, Account account) {
        Account existing = mapper.selectById(id);
        if (existing != null) {
            account.setId(id);
            mapper.updateById(account);
            return mapper.selectById(id);
        }
        return null;
    }
    public boolean delete(Long id) {
        return mapper.deleteById(id) > 0;
    }
}
