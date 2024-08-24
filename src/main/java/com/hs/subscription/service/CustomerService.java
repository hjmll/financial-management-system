package com.hs.subscription.service;

import com.hs.subscription.domain.Customer;

public interface CustomerService {
    Customer selectCustomerByFundAccount(String fundAccount);
}
