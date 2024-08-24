package com.hs.subscription.domain;

import lombok.Data;

@Data
public class SubscriptionDTO {
    /**
     * 基金账户号，用于标识客户的基金账户。
     */
    private String fundAccount;

    /**
     * 交易账户号，用于标识客户的交易账户。
     */
    private String tradingAccount;

    /**
     * 产品号，用于标识产品。
     */
    private String productCode;

    /**
     * 申购金额。
     */
    private double amount;

    /**
     * 申购时间
     */
    private String date;
}
