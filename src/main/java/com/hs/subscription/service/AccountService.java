package com.hs.subscription.service;

import com.hs.subscription.domain.Account;

import java.util.List;

public interface AccountService {
    List<Account> selectAccount(String account);

//    String findTradingAccount(String fundAccount, String productCode);
}
