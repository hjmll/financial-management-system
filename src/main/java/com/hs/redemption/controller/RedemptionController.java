package com.hs.redemption.controller;

import com.hs.redemption.domain.Customer;
import com.hs.redemption.domain.Holding;
import com.hs.redemption.domain.Redemption;
import com.hs.redemption.domain.RedemptionDTO;
import com.hs.redemption.service.CustomerService;
import com.hs.redemption.service.RedemptionService;
import com.hs.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/redeem")
public class RedemptionController {

    @Autowired
    CustomerService customerService;

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    RedemptionService redemptionService;

    //根据基金账号和客户姓名模糊查询客户的信息，包括客户类型、证件号码、证件类型、客户姓名、客户风险等级和基金账号
    @GetMapping
    public List<Customer> selectCustomer(String fundAccount, String name)
    {
        return customerService.selectCustomerByFundAccountAndName(fundAccount,name);
    }

    //根据基金账号查询客户持有份额
    @GetMapping("/holding")
    public List<Holding> selectHoldingByFundAccount(String fundAccount)
    {
        return customerService.selectHoldingByFundAccount(fundAccount);
    }

    //根据当天和当天申请次数生成申请编号
    public String generateRequestId(String date)
    {
        int requestNumber = subscriptionService.findTodayRequestNumber(date);
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")) + String.format("%06d", requestNumber + 1);
    }

    @PostMapping("/success")
    public Redemption selectCustomer(@RequestBody RedemptionDTO redemptionDTO)
    {
        Redemption redemption = new Redemption();
        String cardNumber = redemptionDTO.getCardNumber();
        String productCode = redemptionDTO.getProductCode();
        double shares = redemptionDTO.getShares();
        String fundAccount = customerService.selectFundAccountByCardNumber(cardNumber);
        String customerName = customerService.selectCustomerNameByFundAccount(fundAccount);
        String date = redemptionDTO.getDate();
        redemption.setRequestNumber(generateRequestId(date));
        redemption.setCardNumber(cardNumber);
        redemption.setCustomerName(customerName);
        redemption.setProductCode(productCode);
        redemption.setShares(shares);
        redemption.setFundAccount(fundAccount);
        redemption.setDate(date);
        redemptionService.redeem(redemption);
        return redemption;
    }
}
