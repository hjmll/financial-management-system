package com.hs.redemption.controller;

import com.hs.redemption.domain.Customer;
import com.hs.redemption.domain.Holding;
import com.hs.redemption.domain.Redemption;
import com.hs.redemption.domain.RedemptionDTO;
import com.hs.redemption.service.CustomerService;
import com.hs.redemption.service.RedemptionService;
import com.hs.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<List<Customer>> selectCustomer(@RequestParam(required = false) String fundAccount,
            @RequestParam(required = false) String name) {
        List<Customer> customers = customerService.selectCustomerByFundAccountAndName(fundAccount, name);
        return ResponseEntity.ok(customers); // 返回200 OK和客户列表
    }

    //根据基金账号查询客户持有份额
    @GetMapping("/holding")
    public ResponseEntity<List<Holding>> selectHoldingByFundAccount(@RequestParam String fundAccount) {
        List<Holding> holdings = customerService.selectHoldingByFundAccount(fundAccount);
        return ResponseEntity.ok(holdings);
    }

    //根据当天和当天申请次数生成申请编号
    private String generateRequestId(String date) {
        int requestNumber = subscriptionService.findTodayRequestNumber(date);
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")) + String.format("%06d", requestNumber + 1);
    }

    @PostMapping("/success")
    public ResponseEntity<Redemption> handleRedemption(@RequestBody RedemptionDTO redemptionDTO) {
        try {
            String cardNumber = redemptionDTO.getCardNumber();
            String productCode = redemptionDTO.getProductCode();
            double shares = redemptionDTO.getShares();
            String fundAccount = customerService.selectFundAccountByCardNumber(cardNumber);
            String customerName = customerService.selectCustomerNameByFundAccount(fundAccount);
            String date = redemptionDTO.getDate();
            if (cardNumber == null || productCode == null || shares <= 0 || fundAccount == null
                    || customerName == null || date == null) {
                return ResponseEntity.badRequest().build();
            }
            Redemption redemption = new Redemption();
            redemption.setRequestNumber(generateRequestId(date));
            redemption.setCardNumber(cardNumber);
            redemption.setCustomerName(customerName);
            redemption.setProductCode(productCode);
            redemption.setShares(shares);
            redemption.setFundAccount(fundAccount);
            redemption.setDate(date);
            redemptionService.redeem(redemption);
            return ResponseEntity.ok(redemption);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(500).build();
        }
    }
}
