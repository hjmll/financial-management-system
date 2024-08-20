package com.hs.subscription.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    /**
     * 交易流水号
     */
    private String id;

    /**
     * 银行卡号
     */
    private String cardNumber;

    /**
     * 创建日期
     */
    private Date date;

    /**
     * 业务类型
     */
    private String description;

    /**
     * 金额
     */
    private double amount;

    /**
     * 支入支出
     */
    private String type;
}
