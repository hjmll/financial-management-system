package com.hs.subscription.domain;

import lombok.Data;

@Data
public class PreTrade {
    /**
     * 余额
     */
    private double balance;

    /**
     * 客户风险等级
     */
    private String customerRiskLevel;

    /**
     * 产品风险等级
     */
    private String productRiskLevel;
}

