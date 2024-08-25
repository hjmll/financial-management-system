package com.hs.subscription.service;

import com.hs.subscription.domain.Customer;
import org.springframework.stereotype.Service;

@Service("subCustomerService")
public interface CustomerService {
    Customer selectCustomerByFundAccount(String fundAccount);
}
