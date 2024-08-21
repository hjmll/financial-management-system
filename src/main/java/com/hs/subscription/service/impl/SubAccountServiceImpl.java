package com.hs.subscription.service.impl;

import com.hs.subscription.domain.Account;
import com.hs.subscription.mapper.SubAccountMapper;
import com.hs.subscription.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubAccountServiceImpl implements AccountService {

    @Autowired
    private SubAccountMapper accountMapper;
    @Override
    public List<Account> selectAccount(String account) {
        return accountMapper.selectAccount(account);
    }

//    @Override
//    public String findTradingAccount(String fundAccount, String productCode) {
//        return accountMapper.findTradingAccount(fundAccount, productCode);
//    }
}
