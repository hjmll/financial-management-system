package com.hs.account.service;

import com.hs.account.domain.Account;

import java.util.List;

public interface AccountService {
    int createAccount(Account account);
    Boolean updateAccount(Account account);
    List<Account> getAllAccounts();
    int findAccountNumber();
}
