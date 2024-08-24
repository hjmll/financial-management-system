package com.hs.subscription.domain;

import lombok.Data;

@Data
public class Subscription {
    /**
     * 投资人名称
     */
    private String customerName;

    /**
     * 申请编号
     */
    private String transactionNumber;

    /**
     * 交易账号
     */
    private String tradingAccount;

    /**
     * 基金账号
     */
    private String fundAccount;

    /**
     * 产品代码
     */
    private String productCode;

    /**
     * 投资金额
     */
    private double amount;

    /**
     * 交易时间
     */
    private String date;
}
