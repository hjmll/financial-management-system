package com.hs.redemption.service;

import com.hs.redemption.domain.Customer;
import com.hs.redemption.domain.Holding;

import java.util.List;

public interface CustomerService {
    List<Customer> selectCustomerByFundAccountAndName(String fundAccount, String name);

    List<Holding> selectHoldingByFundAccount(String fundAccount);

    String selectCustomerNameByFundAccount(String fundAccount);

    String selectFundAccountByCardNumber(String cardNumber);
}
