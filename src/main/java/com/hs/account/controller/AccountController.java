package com.hs.account.controller;
// 通过搜索工具输入客户姓名、证件号码等关键词来快速查找客户信息(前端做的？）
// 分页浏览需要吗

import com.hs.account.domain.Account;
import com.hs.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // 创建新账户
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody Account account) {
        Map<String, Object> response = new HashMap<>();
        String fund_account = generateFundAccount();
        account.setFundAccount(fund_account);
        int createdAccount = accountService.createAccount(account);
        if(createdAccount == 1){
            response.put("message", "succeed.");
            response.put("FundAccount", fund_account);
            return ResponseEntity.ok(response);
        }
        else{
            response.put("message", "failed.");
        } return ResponseEntity.badRequest().body(response);
    }

    // 更新账户信息
    @PostMapping("/update")
    public ResponseEntity<String> updateAccount(@RequestBody Account account) {
        Boolean updatedAccount = accountService.updateAccount(account);
        if (updatedAccount) {
            return ResponseEntity.ok("Account updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update account.");
        }
    }

    // 获取所有账户
    @GetMapping("/get_all_accounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    //生成基金账号
    private String generateFundAccount()
    {
        int number = accountService.findAccountNumber();
        return "110" + String.format("%09d", number + 1);
    }
}