package com.hs.subscription.controller;


import com.hs.subscription.domain.*;
import com.hs.subscription.service.AccountService;
import com.hs.subscription.service.CustomerService;
import com.hs.subscription.service.ProductService;
import com.hs.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/subscribe")
public class SubscriptionController {

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProductService productService;

    @Autowired
    SubscriptionService subscriptionService;

    //在搜索框中输入，进行模糊搜索账户
    @GetMapping
    public List<Account> selectAccount(String account)
    {
        List<Account> accounts = accountService.selectAccount(account);
        return accounts;
    }

    //根据选择的基金账号，返回客户信息，包括其可以选择的产品号列表
    @GetMapping("/customer")
    public Customer selectCustomer(String fundAccount)
    {
        Customer customer = customerService.selectCustomerByFundAccount(fundAccount);
        customer.setProductCodeList(productService.selectProductsByFundAccount(fundAccount));
        return customer;
    }

    //选择产品号，然后传递交易账户余额、客户风险等级、产品风险等级
    @GetMapping("/customer/product")
    public PreTrade findBalance(String fundAccount, String productCode)
    {
        return subscriptionService.preTrade(fundAccount, productCode);
    }

    //根据交易时间和基金账号生成申请编号
    public String generateTransactionNumber()
    {
        int tradingNum = subscriptionService.findTodayTransactionNumber();
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + String.format("%06d", tradingNum + 1);
    }

    //申购申请
    @PostMapping("/success")
    public Subscription subscribe(@RequestBody SubscriptionDTO subscriptionDTO)
    {
        double amount = subscriptionDTO.getAmount();
        String fundAccount = subscriptionDTO.getFundAccount();
        String productCode = subscriptionDTO.getProductCode();
        String tradingNumber = generateTransactionNumber();
        Subscription subscription = new Subscription();
        subscription.setAmount(amount);
        subscription.setTradingAccount(accountService.findTradingAccount(fundAccount,productCode));
        subscription.setProductCode(productCode);
        subscription.setCustomerName(customerService.selectCustomerByFundAccount(subscriptionDTO.getFundAccount()).getName());
        subscription.setTransactionNumber(tradingNumber);
        subscription.setFundAccount(fundAccount);
        subscriptionService.subscribe(subscription);
        return subscription;
    }
}
