package com.hs.subscription.domain;

import lombok.Data;

@Data
public class Account {
    /**
     * 基金账户号，用于标识用户的基金账户。
     */
    private String fundAccount;

    /**
     * 交易账户号，用于标识用户的交易账户。
     */
    private String tradingAccount;
}