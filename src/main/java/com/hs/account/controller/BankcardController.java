package com.hs.account.controller;


import com.hs.account.domain.Bankcard;
import com.hs.account.mapper.BankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/bank")
public class BankcardController {

    @Autowired
    private BankMapper bankMapperC;

    // 添加银行卡，添加后初始金额为0
    @PostMapping("/add_card")
    public ResponseEntity<Map<String, Object>> addCard(@RequestBody Bankcard bankcard) {
        Map<String, Object> response = new HashMap<>();
        String trading_account = generateTradingAccount(bankcard.getFundAccount());
        bankcard.setTradingAccount(trading_account);
        bankcard.setBalance(0);
        int addCard = bankMapperC.addCard(bankcard);
        if(addCard == 1){
            response.put("message", "succeed.");
            response.put("trading_account", trading_account);
            return ResponseEntity.ok(response);
        }
        else{
            response.put("message", "failed.");
        } return ResponseEntity.badRequest().body(response);
    }

    // 虚拟充值，传入卡号和充值金额，返回是否充值成功
    @GetMapping("/recharge")
    public ResponseEntity<Map<String, Object>> recharge(String card_number, double amount) {
        Map<String, Object> response = new HashMap<>();
        // 查询是否有此卡
        if (bankMapperC.findCard(card_number) == 0) {
            response.put("message", "Wrong card number.");// 错误银行卡号
            return ResponseEntity.badRequest().body(response);
        }
        try{
            bankMapperC.charge(amount, card_number);
            response.put("message", "Succeed, recharge_amount: " + String.format("%2f", amount));
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    //生成交易账号
    private String generateTradingAccount(String account)
    {
        int number = bankMapperC.getCardNumber(account);
        return account + String.format("%04d", number + 1);
    }
}
