package com.hs.account.domain;

import lombok.Data;

@Data
public class Bankcard {
    private String cardNumber; // 银行卡号
    private String bankName; // 银行名称
    private double balance;
    private String lastUpdated; // 金额变动最新日期
    private String fundAccount; // 资金账号，一客户一个
    private String tradingAccount = ""; // 交易账号，一卡一个
}
