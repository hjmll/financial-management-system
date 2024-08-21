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
        return accountService.selectAccount(account);
    }

    //根据选择的基金账号和交易账号，返回客户信息，余额，和可以选择的产品号列表
    @GetMapping("/customer")
    public Customer selectCustomer(String fundAccount,String tradingAccount)
    {
        Customer customer = customerService.selectCustomerByFundAccount(fundAccount);
        customer.setBalance(subscriptionService.findBalance(fundAccount, tradingAccount));
        customer.setProductCodeList(productService.selectProduct());
        return customer;
    }

    //选择产品号，然后传递产品风险等级
    @GetMapping("/customer/product")
    public String findBalance(String productCode)
    {
        return productService.selectRiskLevelByProductCode(productCode);
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
        String tradingAccount = subscriptionDTO.getTradingAccount();
        String productCode = subscriptionDTO.getProductCode();
        String tradingNumber = generateTransactionNumber();
        Subscription subscription = new Subscription();
        subscription.setAmount(amount);
        subscription.setTradingAccount(tradingAccount);
        subscription.setProductCode(productCode);
        subscription.setCustomerName(customerService.selectCustomerByFundAccount(subscriptionDTO.getFundAccount()).getName());
        subscription.setTransactionNumber(tradingNumber);
        subscription.setFundAccount(fundAccount);
        subscriptionService.subscribe(subscription);
        return subscription;
    }
}
