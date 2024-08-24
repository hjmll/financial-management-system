package com.hs.subscription.service.impl;

import com.hs.subscription.domain.Customer;
import com.hs.subscription.mapper.SubCustomerMapper;
import com.hs.subscription.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCustomerServiceImpl implements CustomerService {

    @Autowired
    private SubCustomerMapper customerMapper;

    @Override
    public Customer selectCustomerByFundAccount(String fundAccount) {
        return customerMapper.selectCustomerByFundAccount(fundAccount);
    }
}
