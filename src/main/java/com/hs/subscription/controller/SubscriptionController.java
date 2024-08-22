package com.hs.subscription.controller;


import com.hs.subscription.domain.*;
import com.hs.subscription.service.AccountService;
import com.hs.subscription.service.CustomerService;
import com.hs.subscription.service.ProductService;
import com.hs.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<Account>> selectAccount(@RequestParam(required = false) String account) {
        List<Account> accounts = accountService.selectAccount(account);
        return ResponseEntity.ok().body(accounts);
    }

    //根据选择的基金账号和交易账号，返回客户信息，余额，和可以选择的产品号列表
    @GetMapping("/customer")
    public ResponseEntity<Customer> selectCustomer(@RequestParam String fundAccount,
            @RequestParam String tradingAccount) {
        try {
            Customer customer = customerService.selectCustomerByFundAccount(fundAccount);
            customer.setBalance(subscriptionService.findBalance(fundAccount, tradingAccount));
            customer.setProductCodeList(productService.selectProduct());
            return ResponseEntity.ok().body(customer);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    //选择产品号，然后传递产品风险等级
    @GetMapping("/customer/product")
    public ResponseEntity<String> findBalance(String productCode) {
        String riskLevel = productService.selectRiskLevelByProductCode(productCode);
        return ResponseEntity.ok().body(riskLevel);
    }

    //根据当天和当天申请次数生成申请编号
    private String generateRequestId(String date)
    {
        int requestNumber = subscriptionService.findTodayRequestNumber(date);
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")) + String.format("%06d", requestNumber + 1);
    }

    //申购申请
    @PostMapping("/success")
    @Transactional
    public ResponseEntity<Subscription> subscribe(@RequestBody SubscriptionDTO subscriptionDTO)
    {
        System.out.println(subscriptionDTO);
        try {
            double amount = subscriptionDTO.getAmount();
            String fundAccount = subscriptionDTO.getFundAccount();
            String tradingAccount = subscriptionDTO.getTradingAccount();
            String productCode = subscriptionDTO.getProductCode();
            String date = subscriptionDTO.getDate();
            String tradingNumber = generateRequestId(date);
            Subscription subscription = new Subscription();
            subscription.setAmount(amount);
            subscription.setTradingAccount(tradingAccount);
            subscription.setProductCode(productCode);
            subscription.setCustomerName(customerService.selectCustomerByFundAccount(fundAccount).getName());
            subscription.setTransactionNumber(tradingNumber);
            subscription.setFundAccount(fundAccount);
            subscription.setDate(date);
            subscriptionService.subscribe(subscription);
            return ResponseEntity.ok().body(subscription);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(500).build();
        }
    }
}
