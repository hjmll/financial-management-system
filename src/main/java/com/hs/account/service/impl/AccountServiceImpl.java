package com.hs.account.service.impl;

import com.hs.account.domain.Account;
import com.hs.account.mapper.AccountMapper;
import com.hs.account.service.AccountService;
import com.hs.settlement.mapper.HaltMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int createAccount(Account account){
        try {
            int success = accountMapper.createAccount(account);
            if (success != 1) {
                return -1;
            } else return 1;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public Boolean updateAccount(Account account){
        try {
            int success = accountMapper.updateAccount(account);
            if (success == 1) {
                return Boolean.TRUE;
            } else{
                System.out.println("success = " + success);
                return Boolean.FALSE;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return Boolean.FALSE;
        }
    }

    @Override
    public List<Account> getAllAccounts(){
        return accountMapper.getAllAccounts();
    }

    @Override
    public int findAccountNumber(){
        return accountMapper.getAccountNumber();
    }
}
