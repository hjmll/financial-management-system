package com.hs.redemption.domain;

import lombok.Data;

@Data
public class Redemption {
    /**
     * 投资人名称
     */
    private String customerName;

    /**
     * 申请编号
     */
    private String RequestNumber;

    /**
     * 银行卡号
     */
    private String cardNumber;

    /**
     * 产品代码
     */
    private String productCode;

    /**
     * 赎回份额
     */
    private double shares;

    /**
     * 基金账号
     */
    private String fundAccount;

    /**
     *交易时间
     */
    private String date;

}
