package com.demo.service;
import com.demo.model.Account;
import com.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    public List<Account> findAll() {
        return repository.findAll();
    }
    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }
    public Account create(Account account) {
        return repository.save(account);
    }

    public Account update(Long id, Account account) {
        Optional<Account> optional = repository.findById(id);
        if (optional.isPresent()) {
            Account existing = optional.get();
            existing.setName(account.getName());
            existing.setBalance(account.getBalance());
            existing.setType(account.getType());
            return repository.save(existing);
        }
        return null;
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
