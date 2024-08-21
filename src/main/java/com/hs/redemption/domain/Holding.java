package com.hs.redemption.domain;

import lombok.Data;

@Data
public class Holding {

    /**
     * 基金账号
     */
    private String fundAccount;

    /**
     * 产品代码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 银行卡号
     */
    private String cardNumber;

    /**
     * 持仓份额
     */
    private double shares;

}
