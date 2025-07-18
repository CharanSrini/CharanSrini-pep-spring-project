package com.example.service;

import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    // @Autowired
    // public AccountService(AccountRepository accountRepository){
    //     this.accountRepository = accountRepository;
    // }

    public Account findByID(int account_id){
        Optional<Account> account = accountRepository.findById(account_id);
        // if(account.isEmpty()){
        //     return null;
        // }
        return account.orElse(null);
    }

    public Account userRegistration(Account account){
        if(account.getUsername() != "" && account.getPassword().length() >= 4){
            return accountRepository.save(account);
        }
        return null;
    }

    public Account login(Account account){
        List<Account> accounts = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        if(accounts.size() == 0){
            return null;
        }
        else{
            return accounts.get(0);
        }
    }
}
