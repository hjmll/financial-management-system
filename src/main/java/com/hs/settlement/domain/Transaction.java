package com.hs.settlement.domain;

import lombok.Data;

@Data
public class Transaction {
    private String id; //  资金交易订单号
    private String cardNumber; // 银行卡号
    private String date; // 交易日期
    private String description; // 交易原因
    private double amount; // 金额
    private String type; // IN-OUT
}
